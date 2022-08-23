package com.example.practicejpa.service;

import com.example.practicejpa.modal.FileMgm;
import com.example.practicejpa.vo.FileMgmDto;

public interface FileService {
	
	FileMgm getFileInfo(String fileName);
	
	void insertFile(FileMgm fileMgm);
	
	void insertFile(FileMgmDto fileMgm);
	
	boolean existFile(String fileName);
	
	boolean existFile(FileMgmDto fileMgm);
}
