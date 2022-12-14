package com.example.practicejpa.jwtSecurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.practicejpa.jwtSecurity.jwtEnum.JwtState;
import com.example.practicejpa.utils.other.CopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtPublishProvider {
	
	private final String issuer;
	private final long expireTime;
	private final long refreshTime;
	private final ZoneId zoneId = ZoneId.of("Asia/Seoul");
	private final Clock clock = Clock.system(zoneId);
	
	private final Algorithm algorithm;
	private final JWTVerifier jwtVerifier;
	
	private static final String ID = "Id";
	private static final String Auth = "Auth";
	private static final String User = "User";
	
	@Autowired
	public JwtPublishProvider(@Value("${jwtProvider.scretKey:MYSYSJWT}") String scretKey,
	                          @Value("${jwtProvider.issuer:MYSYSTEM}") String issuer,
	                          @Value("${jwtProvider.expireTime:600}") long expireTime,
	                          @Value("${jwtProvider.refreshTime:86400}") long refreshTime) {
		
		this.issuer   = issuer;
		this.expireTime = expireTime;
		this.refreshTime = refreshTime;
		this.algorithm = Algorithm.HMAC256(scretKey);
		this.jwtVerifier = JWT.require(algorithm).withIssuer(issuer).build();
	}
	
	public JWTResult createToken(Member member) {
		String uuid = UUID.randomUUID().toString();
		return this.createToken(member, uuid);
	}
	
	public JWTResult createToken(Member member, String id) {
		String accessToken = this.createAccessToken(id, member);
		String refreshToken = this.createRefreshToken(id);
		return new JWTResult(accessToken, refreshToken);
	}
	
	public JWTResult createToken(com.example.practicejpa.model.User user, String id) {
		return this.createToken(this.convetMemberDto(user), id);
	}
	
	public JWTResult createToken(com.example.practicejpa.model.User user) {
		// DTO??? ??????
		return this.createToken(convetMemberDto(user));
	}
	
	private Member convetMemberDto(com.example.practicejpa.model.User user) {
		// DTO??? ??????
		Member member = CopyUtils.CopyObject(Member.class, user);
		Optional.ofNullable(user.getAuths())
		        .ifPresent(auths -> member.setAuths(auths.stream()
		                                                 .map(com.example.practicejpa.model.Auth::getGrantedAuthority)
		                                                 .collect(Collectors.toSet())));
		return member;
	}
	
	/**
	 * @param id
	 * @param user
	 * @return
	 */
	public String createAccessToken(String id, com.example.practicejpa.model.User user) {
		return this.createAccessToken(id, this.convetMemberDto(user));
	}
	
	/**
	 * id??? ???????????? ?????? ?????? ????????? ?????? ?????? ????????? ??????
	 *
	 * @param
	 * @return
	 */
	public String createAccessToken(String id, Member memberInfo) {
		
		List<String> auths = new ArrayList<>(Optional.ofNullable(memberInfo.getAuths())
		                                             .orElse(new HashSet<>()));
		
		memberInfo.setUserPw("");
		
		Map<String, Object> member = CopyUtils.convertMap(memberInfo);
		
		return JWT.create()
		          .withClaim(ID, id)
		          .withClaim(Auth, auths)
		          .withClaim(User, member)
		          .withIssuedAt(Instant.now(clock))
		          .withExpiresAt(Instant.now(clock).plusSeconds(expireTime))
		          .withIssuer(issuer)  // ????????????..
		          .sign(algorithm);
	}
	
	/**
	 * refresh Token ??????
	 *
	 * @param
	 * @return
	 */
	public String createRefreshToken(String id) {
		return JWT.create()
		          .withClaim(ID, id)
		          .withIssuedAt(Instant.now(clock)) // -> ??????????????? ?????????
		          .withExpiresAt(Instant.now(clock).plusSeconds(refreshTime)) // ??????????????? ?????????
		          .withIssuer(issuer)
		          .sign(algorithm);
	}
	
	// public Authentication getAuthentication(String accessToken) {
	// 	Member user = this.getUser(accessToken);
	// 	return new UsernamePasswordAuthenticationToken(user, null, user.getAuths());
	// }
	
	public Member getUser(String token) {
		
		try {
			DecodedJWT verify = jwtVerifier.verify(token);
			
			Map<String, Object> memMap = verify.getClaim(User).asMap();
			List<String> auths = verify.getClaim(Auth).asList(String.class);
			memMap.put("auths", auths);
			
			return CopyUtils.convertObject(memMap, Member.class);
			
		} catch (JWTVerificationException ignored) {
			return null;
		}
	}
	
	public Member getUser(String token, boolean valid) {
		
		try {
			DecodedJWT verify = valid ? jwtVerifier.verify(token) : JWT.decode(token);
			
			Map<String, Object> memMap = verify.getClaim(User).asMap();
			List<String> auths = verify.getClaim(Auth).asList(String.class);
			memMap.put("auths", auths);
			
			return CopyUtils.convertObject(memMap, Member.class);
			
		} catch (JWTVerificationException ignored) {
			return null;
		}
	}
	
	public String getID(String token) {
		try {
			return jwtVerifier.verify(token).getClaim(ID).asString();
		} catch (JWTVerificationException e) {
			return null;
		}
	}
	
	public String getID(String token, boolean valid) {
		try {
			DecodedJWT decodedJWT = valid ? jwtVerifier.verify(token) : JWT.decode(token);
			return decodedJWT.getClaim(ID).asString();
		} catch (JWTVerificationException e) {
			return null;
		}
	}
	
	/**
	 * AccessToken??? refreshToken??? ???????????? ????????? ????????? ???????????? ??????
	 *
	 * @param accessToken
	 * @param refreshToken
	 * @return
	 */
	public JwtState validToken(String accessToken, String refreshToken) {
		if (this.validAccessToken(accessToken) != JwtState.ERROR) {
			String id = this.getID(accessToken, false);
			if (this.validRefreshToken(id, refreshToken) != JwtState.ERROR) {
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
	 * accessToken??? ????????? Id??? IpAddress??? ????????? refreshToken??? ????????? ??????..??
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
