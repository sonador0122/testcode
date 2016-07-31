package com.securesw.shopping.dao;

import java.util.HashMap;
import java.util.List;

import com.securesw.shopping.vo.CartVO;

public interface CartDAO {

	public int insert(CartVO cartVO) throws Exception;
	public List<CartVO> selectList(String email);
	public int delete(int idx);
	public CartVO selectOne(HashMap<String, Object> paraMap);
	public int deleteMap(HashMap<String, Object> paraMap);
	public int changeMap(HashMap<String, Object> paraMap);

}
