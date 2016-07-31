package com.securesw.shopping.dao;

import java.util.HashMap;
import java.util.List;

import com.securesw.shopping.vo.MemberVO;



public interface MemberDAO {

	public MemberVO selectOne(String email) throws Exception;
	public List<MemberVO> selectListMap(HashMap<String,Object> paraMap) throws Exception;
	public int count();
	public int count(String keyword);
	public int insert(MemberVO memberVO);
	public int update(MemberVO memberVO);
	public int delete(String email);
	public int updateDate(String email);
	public int updateData(HashMap<String,Object> paraMap);
}
