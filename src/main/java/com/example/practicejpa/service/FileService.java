package com.example.practicejpa.service;

import com.example.practicejpa.dto.FileMgmDto;
import com.example.practicejpa.model.FileMgm;

import java.util.List;

public interface FileService {
	
	FileMgm getFileInFoById(long fileId);
	
	List<FileMgm> getFileInFoById(long[] fileId);
	
	FileMgm getFileInfo(String fileName);
	
	void insertFile(FileMgm fileMgm);
	
	void insertFile(FileMgmDto fileMgm);
	
	boolean existFile(String fileName);
	
	boolean existFile(FileMgmDto fileMgm);
	
	boolean existFile2(FileMgmDto fileMgm);
	
	List<FileMgmDto> getFileList(String page, String limit);
	
}
