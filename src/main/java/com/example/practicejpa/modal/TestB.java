package com.example.practicejpa.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "testB")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestB {
	@Id
	@Column(name = "b_id")
	long bId;
	
	@Column
	String data;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "a_Id")
	TestA testA;
	
}
