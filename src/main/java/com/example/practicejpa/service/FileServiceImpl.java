package com.example.practicejpa.service;

import com.example.practicejpa.dao.FileDao;
import com.example.practicejpa.modal.FileMgm;
import com.example.practicejpa.utils.AES256;
import com.example.practicejpa.vo.FileMgmDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
public class FileServiceImpl implements FileService {
	
	@Autowired
	FileDao fileDao;
	
	@Override
	public FileMgm getFileInfo(String fileName) {
		return fileDao.getFileInfo(fileName);
	}
	
	@Override
	public void insertFile(FileMgm fileMgm) {
		fileDao.insertFile(fileMgm);
	}
	
	@Override
	public void insertFile(FileMgmDto fileMgmDto) {
		try {
			// 나중에 유저 정보 추가하면 유저 ID를 키로 저장하면 되겠지.
			MultipartFile file = fileMgmDto.getFile();
			
			//String saveFileName = AES256.encryptAES256(fileMgmDto.getFileName(), "SYSTEM");
			//saveFileName = saveFileName.replaceAll("/", "&#47;");
			
			String saveFileName = UUID.randomUUID().toString();
			
			fileMgmDto.setFileSaveName(saveFileName);
			// 물리적으로 파일 저장(물리명은 UUID로 저장함)
			File pFile = new File("D:\\저장위치\\" + saveFileName);
			file.transferTo(pFile);

			// DB에 파일정보 저장
			FileMgm fileMgm = FileMgm.builder()
			                         .fileName(fileMgmDto.getFileName())
			                         .fileExt(fileMgmDto.getFileExt())
			                         .fileByte(fileMgmDto.getFileByte())
			                         .fileSaveName(fileMgmDto.getFileSaveName())
			                         .filePath(pFile.getPath())
			                         .build();
			insertFile(fileMgm);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean existFile(String fileName) {
		return fileDao.existFile(fileName);
	}
	
	@Override
	public boolean existFile(FileMgmDto fileMgmDto) {
		return fileDao.existFile(fileMgmDto);
	}
}
