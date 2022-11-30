package com.example.practicejpa;

import com.example.practicejpa.utils.codeMessage.SystemMessage;
import javassist.tools.reflect.Reflection;
import org.reflections.Reflections;
import org.reflections.scanners.TypeElementsScanner;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainTest {
	
	// TODO: 2022-11-30 컴포넌트 스캔 구현해보기 
	public static void main(String[] args) {
		
		String defaultPath = "";
		
		try {
			Visitor visitor = new Visitor();
			Path path = visitor.getClassPath();
			Path path1 = Files.walkFileTree(path, visitor);
			System.out.println("dlat");

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
		// SystemMessage sa0001 = SystemMessage.toEnum("SA0001");
		// System.out.println(sa0001);
		//
		// PathMatcher pathMatcher = new AntPathMatcher();
		//
		// boolean b = pathMatcher.matchStart("/start/**", "/start/eee/qqq");
		// System.out.println("b = " + b);
		
		//
		// JwtProvider jwtProvider = new JwtProvider();
		//
		// MemberDto memberDto = new MemberDto();
		// memberDto.setUserId("1");
		// memberDto.setName("홍길동");
		//
		// Set<GrantedAuthority> auths = new HashSet<>();
		//
		// auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		// auths.add(new SimpleGrantedAuthority("ROLE_USER"));
		// memberDto.setAuths(auths);
		//
		// String uuid = UUID.randomUUID().toString();
		//
		// String accessToken = jwtProvider.createAccessToken(uuid, memberDto);
		// String refreshToken = jwtProvider.createRefreshToken(uuid);
		//
		// System.out.println("accessToken = " + accessToken);
		// System.out.println("refreshToken = " + refreshToken);
		//
		// JwtState state = jwtProvider.validRefreshToken(refreshToken, uuid);
		//
		// System.out.println(state);
		//
		// System.out.println();
		//
	}
}

class Visitor implements FileVisitor<Path> {
	
	List<Path> paths = new ArrayList<>();
	
	public Path getClassPath(){
		try {
			return Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (file.toFile().getName().endsWith(".class")) {
			paths.add(file);
		}
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.TERMINATE;
	}
	
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}
}