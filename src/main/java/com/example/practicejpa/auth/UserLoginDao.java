package com.example.practicejpa.auth;

import com.example.practicejpa.auth.Model.QAuth;
import com.example.practicejpa.auth.Model.QUser;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class UserLoginDao {
	
	@Autowired
	EntityManager em;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void updateRefreshToken(BigDecimal id, String refreshToken) {
	
	}
	
	public MemberDto findUserByName(String name) {
		
		QUser user = QUser.user;
		QAuth auth = QAuth.auth;
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		
		// User result = queryFactory.selectFrom(user)
		//                           .leftJoin(user.auths).fetchJoin()
		//                           .where(user.userId.eq(name))
		//                           .fetchOne();
		
		// List result = queryFactory.from(user)
		//                           .leftJoin(auth).on(user.id.eq(auth.user.id)).fetchJoin()
		//                           .where(user.userId.eq(name))
		//                           .transform(GroupBy.groupBy(user.id).list(Projections.constructor(MemberDto.class, user.userId, user.userPw, GroupBy.set(auth.grantedAuthority))));
		
		List<MemberDto> result = queryFactory.from(user)
		                                     .leftJoin(auth).on(user.id.eq(auth.user.id)).fetchJoin()
		                                     .where(user.userId.eq(name))
		                                     .transform(GroupBy.groupBy(user.id)
		                                                       .list(Projections.bean(MemberDto.class, user.userId, user.userPw,
		                                                       GroupBy.set(auth.grantedAuthority).as("auths"))));
		
		// MemberDto result = queryFactory.select(Projections.constructor(MemberDto.class, user.userId, user.userPw, GroupBy.set(auth.grantedAuthority)))
		//                                                                           .from(user)
		//                                                                           .leftJoin(auth).on(user.id.eq(auth.user.id)).fetchJoin()
		//                                                                           .where(user.userId.eq(name))
		//                                                                           .fetchOne();
		//.transform(GroupBy.groupBy(user.id).as(Projections.constructor(MemberDto.class, user.userId, user.userPw, GroupBy.set(auth.grantedAuthority))));
		
		System.out.println(result);
		
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	
}
