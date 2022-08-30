package com.example.practicejpa.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@ToString
public class FileMgmDto{
	private long fileId;
	private String fileFullName;
	private String fileName;
	private String fileExt;
	private long fileByte;
	private String fileSaveName;
	private String filePath;
	
	@ToString.Exclude
	private MultipartFile file;
	
	public String getFileFullName() {
		return fileFullName == null ? fileName.concat(".").concat(fileExt) : fileFullName;
	}
}
