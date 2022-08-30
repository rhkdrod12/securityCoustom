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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Getter
@Setter
@Entity(name = "FILE_MGM")
@Builder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FileMgm extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "FILE_GENERATOR")
	@TableGenerator(
		name = "FILE_GENERATOR",
		table = "SEQ_GENERATOR",
		pkColumnName = "SEQNO",
		pkColumnValue = "FILE_ID",
		valueColumnName = "NEXT_VAL",
		schema = "testdb3",
		allocationSize = 1)
	@Column(name = "FILE_ID", nullable = false)
	long fileId;
	// 파일 명
	@Column(name = "FILE_NAME")
	String fileName;
	// 파일 저장명
	@Column(name = "FILE_SAVE_NAME")
	String fileSaveName;
	// 파일 확장자
	@Column(name = "FILE_EXT")
	String fileExt;
	// 파일 용량
	@Column(name = "FILE_BYTE")
	long fileByte;
	// 저장 위치
	@Column(name = "FILE_PATH")
	String filePath;
}
