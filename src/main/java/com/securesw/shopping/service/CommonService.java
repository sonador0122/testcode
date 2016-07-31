package com.securesw.shopping.service;

import com.securesw.shopping.vo.FileVO;


public interface CommonService {

	public FileVO selectOneVo(int boardIdx) throws Exception;
   public int insert(FileVO fileVO) throws Exception;
}
