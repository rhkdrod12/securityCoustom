package com.example.practicejpa.dao;

import com.example.practicejpa.modal.FileMgm;
import com.example.practicejpa.modal.QFileMgm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileDao extends BaseJpaEntityDao {
	
	public FileMgm getFileInfo(String fileName) {
		
		QFileMgm fileMgm = QFileMgm.fileMgm;
		
		List<FileMgm> fetch = jpaQueryFactory.selectFrom(fileMgm)
		                                     .where(fileMgm.fileName.append(".").append(fileMgm.fileExt).eq(fileName),
		                                            fileMgm.creator.eq("SYSTEM"))
		                                     .fetch();
		if (fetch.size() > 0) {
			return fetch.get(0);
		}
		return null;
	}
	
}
