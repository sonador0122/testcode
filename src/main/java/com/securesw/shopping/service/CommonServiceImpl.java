package com.securesw.shopping.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.securesw.shopping.dao.FileDAO;
import com.securesw.shopping.vo.FileVO;

@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Resource(name="fileDAO")
	private FileDAO fileDAO;
	
	@Override
	public FileVO selectOneVo(int boardIdx) throws Exception {
		// TODO Auto-generated method stub
		return fileDAO.selectOne(boardIdx);
	}

	@Override
	public int insert(FileVO fileVO) throws Exception {
		// TODO Auto-generated method stub
		return fileDAO.insert(fileVO);
	}

}
