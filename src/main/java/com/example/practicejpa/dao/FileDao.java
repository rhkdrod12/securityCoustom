package com.example.practicejpa.dao;

import com.example.practicejpa.dto.FileMgmDto;
import com.example.practicejpa.model.FileMgm;
import com.example.practicejpa.model.QFileMgm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileDao extends BaseJpaEntityDao {
	
	public List<FileMgm> getFileList(String page, String limit){
		QFileMgm fileMgm = QFileMgm.fileMgm;
		
		long pagePhase = Long.parseLong(page) - 1;
		long limitPhase = Long.parseLong(limit);
		
		List<FileMgm> result = jpaQueryFactory.selectFrom(fileMgm)
		                                      .where(fileMgm.creator.eq("SYSTEM"))
		                                      .offset(pagePhase * limitPhase)
		                                      .limit(limitPhase)
		                                      .fetch();
		return result;
	}
	
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
	
	public void insertFile(FileMgm fileMgm) {
		if (fileMgm != null) {
			super.save(fileMgm);
		}
	}
	
	public boolean existFile(FileMgmDto fileMgmDto) {
		QFileMgm fileMgm = QFileMgm.fileMgm;
		Long result = jpaQueryFactory.select(fileMgm.count())
		                             .from(fileMgm)
		                             .where(fileMgm.fileName.eq(fileMgmDto.getFileName()),
											fileMgm.fileExt.eq(fileMgmDto.getFileExt()),
		                                    fileMgm.creator.eq("SYSTEM"))
		                             .fetchFirst();
		return result > 0;
	}
	
	public boolean existFile(String fileName) {
		
		QFileMgm fileMgm = QFileMgm.fileMgm;
		
		Long result = jpaQueryFactory.select(fileMgm.count())
		                             .from(fileMgm)
		                             .where(fileMgm.fileName.eq(fileName))
		                             .fetchFirst();
		return result > 0;
	}
	
}
