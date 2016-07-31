package com.securesw.shopping.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.securesw.shopping.dao.OrdersDAO;
import com.securesw.shopping.vo.OrdersVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Resource(name="ordersDAO")
	private OrdersDAO ordersDAO;
	
	@Override
	public List<OrdersVO> selectList(int start, int end, String keyword)
			throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		
		paraMap.put("offset", start);
		paraMap.put("limit", end);
		paraMap.put("keyword", keyword);
		return ordersDAO.selectListMap(paraMap);
	}

	@Override
	public int count(String keyword) throws Exception {
		// TODO Auto-generated method stub
		return ordersDAO.count(keyword);
	}

}
