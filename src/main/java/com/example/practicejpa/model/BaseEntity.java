package com.example.practicejpa.model;

import com.example.practicejpa.utils.code.CrudStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {
	
	@Transient
	final public static String IDENTIFIER = "CREATOR";
	
	@Transient
	CrudStatus status = CrudStatus.READ;
	
	@Column(name = "create_time", updatable = false)
	Timestamp createTime = new Timestamp(System.currentTimeMillis());
	
	@Column(name = "creator", updatable = false)
	String creator = "SYSTEM";
	
	@Column(name = "last_update")
	Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
	
	@PrePersist
	public void prePersist(){
		lastUpdate = new Timestamp(System.currentTimeMillis());
		if(creator == null) creator = "SYSTEM";
	}
	
	@PostPersist
	public void postPersist(){
		status = CrudStatus.READ;
		// System.out.println("여기는 영속 후에 실행되는 곳입니다.");
	}
	
	@PreUpdate
	public void preUpdate(){
		lastUpdate = new Timestamp(System.currentTimeMillis());
		// System.out.println("여기는 업데이트 전에 실행되는 곳입니다.");
	}
	
	@PostUpdate
	public void postUpdate(){
		status = CrudStatus.READ;
		// System.out.println("여기는 업데이트 후에 실행되는 곳입니다.");
	}
	
	
}
