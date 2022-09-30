package com.example.practicejpa.auth.authorityCRUD;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorityAspect {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @Authority 해당 어노테이션이 걸려있는 메소드의 권한 체크를 하기위한 용도
	 * before에 의해 메소드가 실행되기전에 먼저 실행됨
	 */
	@Pointcut("@annotation(com.example.practicejpa.auth.authorityCRUD.Authority)")
	public void authority(){}
	
	//authority()가 실행되기 전에 발동할 거고
	//Pointcut에 의해 해당 경로가 적인 어노테이션이 적힌 메소드가 타겟이 될거고
	//그래서 바람은 해당 메소드가 실행되기전에 이 여기 메소드가 실행되어야한다!/
	
	/*
		1. URL에 대한 계정 권한체크
		2. CRUD별 계정 권한체크
		
		근데 이렇게 하는것보다 DAO단에서 조회 권한을 확인하는게 나으려나...
		아니면 service단에서..?
		매번 확인은 하긴 할텐데.. 음.. 어떻게 확인해야하려나..
		어차피 URL 로 들어오긴할텐데..
	 */
	@Before("authority()")
	public void authorityTest(JoinPoint joinPoint){
		
		// MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		//
		// Authority authority = methodSignature.getMethod().getDeclaredAnnotation(Authority.class);
		//
		// Class<?>[] parameterTypes =  methodSignature.getParameterTypes();
		// Object[] parameterValues = joinPoint.getArgs();
		//
		// String table =  authority.table();
		// String column = authority.column().equals("")? authority.key(): authority.column();
		// String key = authority.key();
		// Class<?> clazz = authority.targetClazz();
		//
		// Object targetVal = null;
		//
		// if(parameterValues.length == 1){
		// 	targetVal = parameterValues[0];
		// }else{
		// 	for (int i = 0; i < parameterTypes.length; i++) {
		// 		if(parameterTypes[i] == clazz) targetVal = parameterValues[i];
		// 	}
		// }
		//
		// // ExpressionParser parser = new SpelExpressionParser();
		// // Expression exp = parser.parseExpression(key);
		// //
		// // Object val = exp.getValue(targetVal, authority.keyClazz());
		// //
		// // 권한체크 로직
		//
		// /**
		//  * CREATOR가 모두 들어가야한다는 말인데..
		//  */
		// String query = "SELECT CREATOR FROM " + table +" WHERE " +column +"= ?";
		// String owner = jdbcTemplate.queryForObject(query, String.class, val);
		// String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		//
		// if(!userName.equals(owner)){
		// 	throw new CustomException(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다.");
		// }
		
	}
}
