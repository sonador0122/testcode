package com.securesw.shopping.dao;

import java.util.HashMap;
import java.util.List;

import com.securesw.shopping.vo.OrdersVO;



public interface OrdersDAO {
	
	public List<OrdersVO> selectListMap(HashMap<String,Object> paraMap) throws Exception;
	public List<OrdersVO> selectTotalListMap(HashMap<String,Object> map) throws Exception;
	public int count()throws Exception;
	public int count(String keyword) throws Exception;
	public int countTotalList(HashMap<String,Object> map) throws Exception;
	public int delete(int idx);
	public int insert(OrdersVO ordersVO);

}
