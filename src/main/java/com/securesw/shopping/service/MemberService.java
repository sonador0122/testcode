package com.securesw.shopping.service;

import java.util.List;

import com.securesw.shopping.vo.CartVO;
import com.securesw.shopping.vo.OrdersVO;
import com.securesw.shopping.vo.MemberVO;
import com.securesw.shopping.vo.GiftlistVO;



public interface MemberService {

	public MemberVO selectOneVo(String email) throws Exception;
    public boolean selectOne(String email) throws Exception;
    public int selectOneNo(String email) throws Exception;
    public List<MemberVO> selectList(int start,int end,String order,String keyword) throws Exception;
    public int count() throws Exception;
    public int count(String keyword) throws Exception;
    public int insert(MemberVO memberVO) throws Exception;
    public int update(MemberVO memberVO) throws Exception;
    public int delete(String email) throws Exception;
    public int updateDate(String email) throws Exception;
    public int updateAuth(String email, String auth) throws Exception;
    public int updatePassword(String email,String password) throws Exception;
	public int addGiftlist(String email, int idx) throws Exception;
	public int giftCount() throws Exception;
	public int giftCount(String keyword) throws Exception;
	public List<GiftlistVO> giftList(int start, int end, String keyword, String email) throws Exception;
	public int delGiftlist(String email, int idx) throws Exception;
	public boolean checkGiftlist(String email, int idx) throws Exception;
	public List<OrdersVO> ordersList(int start, int end,String email) throws Exception;
	public int orderCount() throws Exception;
	public int orderCount(String keyword) throws Exception;
	public int delorderlist(int idx) throws Exception;
	public List<CartVO> cartList(String email);
    public List<OrdersVO> ordersTotalList(int firstRow, int endRow, String keyword) throws Exception;
    public int orderCountTotalList(String keyword) throws Exception;

}
