package com.example.practicejpa.service;

import com.example.practicejpa.dao.FileDao;
import com.example.practicejpa.modal.FileMgm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FileServiceImpl implements FileService {
	
	@Autowired
	FileDao fileDao;
	
	@Override
	public FileMgm getFileInfo(String fileName) {
		return fileDao.getFileInfo(fileName);
	}
}
