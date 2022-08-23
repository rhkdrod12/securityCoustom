package com.example.practicejpa.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "testA")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestA {
	@Id
	@Column(name = "a_id")
	long aId;
	
	@Column
	String data;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "testA", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	Set<TestB> testBS;
	
	
}
