package com.example.practicejpa.controller;

import com.example.practicejpa.modal.FileMgm;
import com.example.practicejpa.service.FileService;
import com.example.practicejpa.utils.ParamUtils;
import com.querydsl.core.types.dsl.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/api")
public class FileContorller {
	
	@Autowired
	FileService fileService;
	
	@GetMapping("/download")
	public ResponseEntity<Object> download(@RequestParam(name = "fileName") String fileName) throws IOException {
		log.info("파일 다운로드 요청");
		if(ParamUtils.isNotEmpty(fileName)){
			FileMgm fileInfo = fileService.getFileInfo(fileName);
			if(fileInfo != null){
				String path = fileInfo.getFilePath() + "\\" + fileName;
				File file = new File(path);
				
				if(file.isFile()){
					String downloadName = fileInfo.getFileName() + "_" + ParamUtils.getCurrentTime() + "." + fileInfo.getFileExt();
					InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(downloadName, StandardCharsets.UTF_8).build());
					return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
				}else{
					log.info("존재하지 않는 파일입니다.");
				}
			}else{
				log.info("등록되지 않는 파일입니다.");
			}
		}
		return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}


}
