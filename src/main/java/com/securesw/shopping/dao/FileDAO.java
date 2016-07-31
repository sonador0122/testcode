package com.securesw.shopping.dao;

import com.securesw.shopping.vo.FileVO;


public interface FileDAO {

	public FileVO selectOne(int boardIdx) throws Exception;
	public int insert(FileVO fileVO) throws Exception;
}
