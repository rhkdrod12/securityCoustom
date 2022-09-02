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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity(name = "Test_A")
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TestA extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	String data;
	
	@OneToOne(mappedBy = "testA", cascade = CascadeType.ALL)
	TestB testB;
	
}
