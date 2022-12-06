package com.example.practicejpa;

import com.example.practicejpa.utils.JwtAuth;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MainTest {
	
	// TODO: 2022-11-30 컴포넌트 스캔 구현해보기 
	public static void main(String[] args) {
		
		ClassScanner classScanner = new ClassScanner();
		List<Path> paths = classScanner.getPaths();
		List<Class<?>> classList = classScanner.getClassList();
		System.out.println(paths);
		
		List<Class<?>> includeAnnotationClassList = classScanner.getIncludeAnnotationClassList(JwtAuth.class);
		System.out.println(includeAnnotationClassList);
		
	}
}

/**
 * 해당 프로젝트 내 클래스 파일의 경로를 읽어오기 위한 클래스
 */
class ClassScanner{
	
	public Path getClassPath(){
		try {
			return Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Path> getPaths() {
		return getPaths(getClassPath());
	}
	
	public List<Path> getPaths(Path path) {
		ClassVisitor visitor = new ClassVisitor();
		try {
			Files.walkFileTree(path, visitor);
			return visitor.getPaths();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Class<?>> getClassList() {
		return this.getClassList(getClassPath());
	}
	
	public List<Class<?>> getClassList(Path path) {
		List<Path> paths = getPaths(path);
		List<Class<?>> clz = new ArrayList<>();
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		
		for (Path childPath : paths) {
			try {
				clz.add(Class.forName(getClassName(childPath), false, contextClassLoader));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return clz;
	}
	
	public List<Class<?>> getIncludeAnnotationClassList(Class<? extends Annotation> annotation) {
		return getIncludeAnnotationClassList(getClassPath(), annotation);
	}
	
	public List<Class<?>> getIncludeAnnotationClassList(Path path, Class<? extends Annotation> annotation) {
		
		List<Class<?>> classList = getClassList(path);
		List<Class<?>> result = new ArrayList<>();
		
		for (Class<?> aClass : classList) {
			if (aClass.getAnnotation(annotation) != null) {
				result.add(aClass);
			}
		}
		return result;
	}
	
	private String getClassName(Path path){
		int defaultPathLength = this.getClassPath().toString().length() + 1;
		int classExtLength = 6;
		
		String pathName = path.toString();
		return pathName.substring(defaultPathLength, pathName.length() - classExtLength)
		                   .replaceAll("\\\\", ".");
	}
	
}


class ClassVisitor implements FileVisitor<Path> {
	
	List<Path> paths = new ArrayList<>();
	
	public Path getClassPath(){
		try {
			return Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Path> getPaths(){
		return paths;
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