package com.example.practicejpa.service;

import com.example.practicejpa.modal.FileMgm;

public interface FileService {
	
	FileMgm getFileInfo(String fileName);
	
	void insertFile(FileMgm fileMgm);
	
	boolean existFile(String fileName);
	
}
