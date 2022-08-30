package com.example.practicejpa.service;

import com.example.practicejpa.dao.FileDao;
import com.example.practicejpa.dao.repository.FIleRepository;
import com.example.practicejpa.dto.FileMgmDto;
import com.example.practicejpa.exception.GlobalException;
import com.example.practicejpa.model.FileMgm;
import com.example.practicejpa.utils.code.FileFailMessage;
import com.example.practicejpa.utils.other.CopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class FileServiceImpl implements FileService {
	
	@Autowired
	FileDao fileDao;
	
	@Autowired
	FIleRepository fIleRepository;
	
	@Override
	public FileMgm getFileInFoById(long fileId) {
		return fIleRepository.findByFileId(fileId);
	}
	
	@Override
	public FileMgm getFileInfo(String fileName) {
		return fileDao.getFileInfo(fileName);
	}
	@Override
	public List<FileMgmDto> getFileList(String page, String limit) {
		List<FileMgm> fileList = fileDao.getFileList(page, limit);
		return CopyUtils.CopyCollection(FileMgmDto.class, fileList);
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
			log.warn(e.getMessage());
			throw new GlobalException(FileFailMessage.FAIL_SAVE_FILE);
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
	
	@Override
	public boolean existFile2(FileMgmDto fileMgm) {
		return fIleRepository.existsFileMgmByFileNameAndFileExt(fileMgm.getFileName(), fileMgm.getFileExt());
	}
	
	
	
}
