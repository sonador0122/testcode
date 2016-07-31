package com.securesw.shopping.service;

import java.util.List;

import com.securesw.shopping.vo.OrdersVO;



public interface AdminService {

    public List<OrdersVO> selectList(int start,int end,String keyword) throws Exception;
   public int count(String keyword) throws Exception;

}
