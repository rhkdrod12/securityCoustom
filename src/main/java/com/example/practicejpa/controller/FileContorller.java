package com.example.practicejpa.controller;

import com.example.practicejpa.dto.FileMgmDto;
import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.model.FileMgm;
import com.example.practicejpa.service.FileService;
import com.example.practicejpa.utils.codeMessage.FileFailMessage;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.other.ParamUtils;
import com.example.practicejpa.utils.responseEntity.CommResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@RestController
@RequestMapping("/api")
public class FileContorller {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	FileService fileService;
	
	@GetMapping("/getFileList")
	public ResponseEntity<?> fileList(@RequestParam(name = "page", required = false, defaultValue = "1") String page,
	                                  @RequestParam(name = "pageCount", required = false, defaultValue = "10") String limit){
		return CommResponse.done(fileService.getFileList(page, limit));
	}
	
	
	@DeleteMapping("/deleteFileList")
	public ResponseEntity<?> deleteFileList(@RequestParam(name = "fileId") long[] fileId){
		if(fileId.length > 0){
			fileService.deleteFileById(fileId);
			return CommResponse.done();
		}else{
			return CommResponse.fail(SystemMessage.NOT_EXIST_PARAM);
		}
	}
	
	
	@GetMapping(value = "/multiDownload")
	public void fileMultiDownload(@RequestParam(name = "fileId") long[] fileId, HttpServletResponse response) throws IOException {
		log.info("파일 멀티 다운로드 요청 {}", fileId);
		if (ParamUtils.isNotEmpty(fileId)) {
			List<FileMgm> fileList = fileService.getFileInFoById(fileId);
			
			if(fileList != null && fileList.size() > 0){
				// 파일을 내려보내기 위한 헤더설정
				response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
				response.setHeader("Content-Disposition", "attachment;filename=mutiFile.zip");
				
				// 입력받은 zipEntry들을 모아 하나의 zip을 만들어내는 stream
				ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
				for (FileMgm fileMgm : fileList) {
					FileInputStream fileInputStream = new FileInputStream(fileMgm.getFilePath());
					// 입력한 이름으로 압축 파일 내 파일 생성
					zipOutputStream.putNextEntry(new ZipEntry(fileMgm.getFileName() + "." + fileMgm.getFileExt()));
					// 파일 내용을 zipOutStream으로 옮김 -> 현재 설정된 zipEntry쪽으로 데이터가 옮겨짐
					StreamUtils.copy(fileInputStream, zipOutputStream);
					fileInputStream.close();
					zipOutputStream.closeEntry();
				}
				// close 및 입력받은 outputSream으로 값을 내보냄 -> response의 output를 넣어줬으니 클라이언트 쪽으로 값을 내려보냄
				zipOutputStream.finish();
				
			}else{
				throw new GlobalException(FileFailMessage.NOT_FOUND_FILE);
			}
		}else{
			throw new GlobalException(SystemMessage.NOT_EXIST_PARAM);
		}
	}
	
	@GetMapping(value = "/download")
	public ResponseEntity<Object> fileDownload(@RequestParam(name = "fileId" , defaultValue = "-1") long fileId) throws IOException {
		log.info("파일 다운로드 요청");
		if (ParamUtils.isNotEmpty(fileId)) {
			FileMgm fileInfo = fileService.getFileInFoById(fileId);
			if (fileInfo != null) {
				String path = fileInfo.getFilePath();
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
					httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					httpHeaders.setContentDisposition(ContentDisposition.attachment()
					                                                    .name(downloadName)
					                                                    .filename(downloadName, StandardCharsets.UTF_8)
					                                                    .build());
					
					// 파일을 내려보내는 거라 이렇게 내려보내야겠네..
					return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
					
				} else {
					log.info("존재하지 않는 파일입니다.");
					return CommResponse.fail(FileFailMessage.NOT_FOUND_FILE);
				}
			} else {
				log.info("등록되지 않는 파일입니다.");
				return CommResponse.fail(FileFailMessage.NOT_EXIST_FILE);
			}
		}
		return CommResponse.fail(SystemMessage.NOT_EXIST_PARAM);
	}
	
	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	public ResponseEntity<?> fileUpload(@ModelAttribute(name = "fileInfo") FileMgmDto fileInfo) {
		if (fileInfo != null && ParamUtils.isNotEmpty(fileInfo.getFileName())) {
			if (!fileService.existFile(fileInfo)) {
				log.info("도착 {}", fileInfo);
				fileService.insertFile(fileInfo);
				return CommResponse.done(true);
			} else {
				return CommResponse.fail(false, FileFailMessage.EXIST_FILE);
			}
		}
		return CommResponse.fail(SystemMessage.NOT_EXIST_PARAM);
	}
	
	@RequestMapping(value = "/exist")
	public ResponseEntity<?> fileExist(@RequestBody FileMgmDto fileMgmDto) {
		if (fileMgmDto != null) {
			if (!fileService.existFile(fileMgmDto)) {
				return CommResponse.done(true);
			} else {
				return CommResponse.fail(false, FileFailMessage.EXIST_FILE, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return CommResponse.fail(SystemMessage.NOT_EXIST_PARAM);
	}
	
	@RequestMapping(value = "/exist2")
	public ResponseEntity<?> fileExist2(@RequestBody FileMgmDto fileMgmDto) {
		if (fileMgmDto != null) {
			if (!fileService.existFile2(fileMgmDto)) {
				return CommResponse.done(true);
			} else {
				return CommResponse.done(false, FileFailMessage.EXIST_FILE);
			}
		}
		return CommResponse.fail(SystemMessage.NOT_EXIST_PARAM);
	}
}
