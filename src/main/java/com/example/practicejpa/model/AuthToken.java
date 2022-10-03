package com.example.practicejpa.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Getter
@Setter
@Entity(name = "AUTH_TOKEN")
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AuthToken extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AUTH_TOKEN_GENERATOR")
	@TableGenerator(
	name = "AUTH_TOKEN_GENERATOR",
	table = "SEQ_GENERATOR",
	pkColumnName = "SEQNO",
	pkColumnValue = "AUTH_ID",
	valueColumnName = "NEXT_VAL",
	schema = "testdb3",
	allocationSize = 1)
	@Column(name = "AUTH_ID", nullable = false)
	long authId;
	
	@Column(name = "UNIQUE_ID", unique = true, nullable = false)
	String uniquId;
	
	@Column(name = "TOKEN", nullable = false)
	String token;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	User user;
	
	
	
}