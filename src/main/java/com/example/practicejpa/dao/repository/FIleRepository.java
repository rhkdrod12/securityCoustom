package com.example.practicejpa.dao.repository;

import com.example.practicejpa.model.FileMgm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FIleRepository extends JpaRepository<FileMgm, Long> {
	boolean existsFileMgmByFileNameAndFileExt(String fileName, String fileExt);
	
	FileMgm findByFileId(long fileId);
}
