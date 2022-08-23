package com.example.practicejpa.controller;

import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.modal.FileMgm;
import com.example.practicejpa.service.FileService;
import com.example.practicejpa.utils.ParamUtils;
import com.example.practicejpa.vo.FileMgmDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/api")
public class FileContorller {
	
	@Autowired
	FileService fileService;
	
	@GetMapping("/download")
	public ResponseEntity<Object> fileDownload(@RequestParam(name = "fileName") String fileName) throws IOException {
		log.info("파일 다운로드 요청");
		if (ParamUtils.isNotEmpty(fileName)) {
			FileMgm fileInfo = fileService.getFileInfo(fileName);
			if (fileInfo != null) {
				String path = fileInfo.getFilePath() + "\\" + fileName;
				File file = new File(path);
				
				if (file.isFile()) {
					String downloadName = fileInfo.getFileName() + "_" + ParamUtils.getCurrentTime() + "." + fileInfo.getFileExt();
					InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
					HttpHeaders httpHeaders = new HttpHeaders();
					
					// content-Disposition
					// body에 대한 처리 방식을 정의하는 응답헤더
					// 응답방식에는 inline, attachment 두가지가 있으며
					// inline : 해당 파일 정보를 받아 브라우저 내에서 사용하는 경우 ex) PDF파일을 받아 웹화면 내에서 보여주는 경우에 해당
					// attachment : 해당 파일을 바로 다운로드 하는 경우 ex) zip파일을 바로 다운받는 경우에 해당
					// attachment의 경우 Content-Disposition: attachment; filename="filename.jpg" 와 같이 다운받을 파일에 대한 이름 지정이 가능
					//
					// 대용량 데이터에 대한 처리의 경우
					// content-Disposition: form-data 형태로 사용
					
					httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(downloadName, StandardCharsets.UTF_8).build());
					return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
				} else {
					log.info("존재하지 않는 파일입니다.");
				}
			} else {
				log.info("등록되지 않는 파일입니다.");
			}
		}
		
		return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	}
	
	/**
	 * 한번에 전송하는 것도 좋긴한데..
	 * @param fileInfo
	 * @param file
	 * @param httpSession
	 */
	@PostMapping("/upload")
	public void fileUpload(@ModelAttribute(name = "fileInfo") FileMgmDto fileInfo, @RequestParam(name = "file") MultipartFile file, HttpSession httpSession) {
		if (fileInfo != null && ParamUtils.isNotEmpty(fileInfo.getFileName())) {
			if (fileService.existFile(fileInfo)) {
				fileService.insertFile(fileInfo);
				log.info("도착도착 {}", fileInfo);
			}
		}
	}
	
	//@CrossOrigin(origins="*")
	@PostMapping(value = "/upload2", consumes = "multipart/form-data")
	public void fileUpload2(@ModelAttribute(name = "fileInfo") FileMgmDto fileInfo) {
		if (fileInfo != null && ParamUtils.isNotEmpty(fileInfo.getFileName())) {
			if (!fileService.existFile(fileInfo)) {
				log.info("도착 {}", fileInfo);
				fileService.insertFile(fileInfo);
			}else{
				throw new GlobalException("동일한 파일명을 가진 파일이 존재합니다. 파일명을 변경하여 올려주세요");
			}
		}
	}
}
