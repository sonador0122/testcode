package com.securesw.shopping.dao;

import java.util.HashMap;
import java.util.List;

import com.securesw.shopping.vo.GiftlistVO;


public interface GiftlistDAO {
	
	public int insert(HashMap<String,Object> paraMap) throws Exception;
	public int count() throws Exception;
	public int count(String keyword) throws Exception;
	public List<GiftlistVO> selectListMap(HashMap<String, Object> paraMap) throws Exception;
	public int delete(HashMap<String, Object> paraMap) throws Exception;
	public GiftlistVO selectOne(HashMap<String, Object> paraMap) throws Exception;
}
