package com.example.practicejpa.utils.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.practicejpa.auth.MemberDto;
import com.example.practicejpa.utils.other.CopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {
	
	@Value("${jwtProvider.scretKey:MYSYSJWT}")
	private String scretKey = "MYTESTJWT";
	@Value("${jwtProvider.issuer:MYSYSTEM}")
	private String issuer = "MYSYSTEM";
	@Value("${jwtProvider.expireTime: 600}")
	private long expireTime;
	@Value("${jwtProvider.refreshTime: 86400}")
	private long refreshTime;
	
	private ZoneId zoneId = ZoneId.of("Asia/Seoul");
	private Clock clock = Clock.system(zoneId);
	
	private Algorithm algorithm = Algorithm.HMAC256(scretKey);
	private JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(issuer).build();
	
	private static String ID = "Id";
	private static String IP_ADDRESS = "IpAddress";
	private static String Auth = "Auth";
	private static String User = "User";
	
	
	public JWTResult createToken(MemberDto memberDto) {
		String uuid = UUID.randomUUID().toString();
		String accessToken = this.createAccessToken(uuid, memberDto);
		String refreshToken = this.createRefreshToken(uuid);
		return new JWTResult(accessToken, refreshToken);
	}
	
	/**
	 * id를 서명자로 하는 권한 정보를 담고 있는 토큰을 생성
	 *
	 * @param
	 * @return
	 */
	public String createAccessToken(String id, MemberDto memberDto) {
		
		List<String> auths = Optional.ofNullable(memberDto.getAuths())
		                             .orElse(new HashSet<>())
		                             .stream()
		                             .map(GrantedAuthority::getAuthority)
		                             .collect(Collectors.toList());
		
		memberDto.setUserPw("");
		
		Map<String, Object> member = CopyUtils.convertMap(memberDto);
		
		return JWT.create()
		          .withClaim(ID, id)
		          .withClaim(Auth, auths)
		          .withClaim(User, member)
		          .withIssuedAt(Instant.now(clock))
		          .withExpiresAt(Instant.now(clock).plusSeconds(expireTime))
		          .withIssuer(issuer)  // 서명자네..
		          .sign(algorithm);
	}
	
	/**
	 * refresh Token 생성
	 *
	 * @param
	 * @return
	 */
	public String createRefreshToken(String id) {
		return JWT.create()
		          .withClaim(ID, id)
		          .withIssuedAt(Instant.now(clock)) // -> 발급시간을 지정함
		          .withExpiresAt(Instant.now(clock).plusSeconds(refreshTime)) // 만료시간을 지정함
		          .withIssuer(issuer)
		          .sign(algorithm);
	}
	
	public MemberDto getUser(String token) {
		
		try {
			DecodedJWT verify = jwtVerifier.verify(token);
			
			Map<String, Object> memMap = verify.getClaim(User).asMap();
			List<String> auths = verify.getClaim(Auth).asList(String.class);
			memMap.put("auths", auths);
			
			return CopyUtils.convertObject(memMap, MemberDto.class);
			
		} catch (JWTVerificationException ignored) {
			return null;
		}
	}
	
	public String getID(String token) {
		try {
			DecodedJWT verify = jwtVerifier.verify(token);
			return verify.getClaim(ID).asString();
		} catch (JWTVerificationException e) {
			return null;
		}
	}
	
	public JwtState validToken(String accessToken, String refreshToken) {
		if(this.validAccessToken(accessToken) != JwtState.ERROR){
			String id = this.getID(accessToken);
			if (this.validRefreshToken(id, refreshToken) != JwtState.ERROR){
				return JwtState.SUCCESS;
			}
		}
		return JwtState.ERROR;
	}
	
	public JwtState validAccessToken(String token) {
		try {
			DecodedJWT verify = jwtVerifier.verify(token);
			
			if (verify.getClaim(ID).isMissing()) {
				return JwtState.ERROR;
			} else if (verify.getClaim(Auth).isMissing()) {
				return JwtState.ERROR;
			} else {
				return JwtState.SUCCESS;
			}
			
		} catch (TokenExpiredException e) {
			return JwtState.EXPIRE;
		} catch (Exception e) {
			return JwtState.ERROR;
		}
	}
	
	/**
	 * accessToken에 저장된 Id와 IpAddress를 가지고 refreshToken의 유효성 검사..??
	 */
	public JwtState validRefreshToken(String id, String refreshToken) {
		try {
			DecodedJWT verify = jwtVerifier.verify(refreshToken);
			
			if (verify.getClaim(ID).isMissing() || verify.getClaim(ID).isNull()) {
				return JwtState.ERROR;
			} else if (!verify.getClaim(ID).asString().equals(id)) {
				return JwtState.ERROR;
			} else {
				return JwtState.SUCCESS;
			}
		} catch (TokenExpiredException e) {
			return JwtState.EXPIRE;
		} catch (Exception e) {
			return JwtState.ERROR;
		}
	}
	
}
