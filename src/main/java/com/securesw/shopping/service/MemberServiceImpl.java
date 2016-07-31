package com.securesw.shopping.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.securesw.shopping.dao.CartDAO;
import com.securesw.shopping.dao.GiftlistDAO;
import com.securesw.shopping.dao.OrdersDAO;
import com.securesw.shopping.dao.MemberDAO;
import com.securesw.shopping.vo.CartVO;
import com.securesw.shopping.vo.GiftlistVO;
import com.securesw.shopping.vo.OrdersVO;
import com.securesw.shopping.vo.MemberVO;



@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Resource(name="memberDAO")
	private MemberDAO memberDAO;
	
	@Resource(name="giftlistDAO")
	private GiftlistDAO giftlistDAO;
	
	@Resource(name="ordersDAO")
	private OrdersDAO ordersDAO;
	
	@Resource(name="cartDAO")
	private CartDAO cartDAO;
	
	@Override
	public boolean selectOne(String email) throws Exception {
		MemberVO memberVO = memberDAO.selectOne(email);
		if(memberVO != null){
			return true;
		}
		return false;
	}

	@Override
	public List<MemberVO> selectList(int start, int end, String order,String keyword) throws Exception {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("order", order);
		paraMap.put("offset", start);
		paraMap.put("limit", end);
		paraMap.put("keyword", keyword);
		return memberDAO.selectListMap(paraMap);
	}

	@Override
	public int count() throws Exception {
		return memberDAO.count();
	}

	@Override
	public int count(String keyword) throws Exception {
		return memberDAO.count(keyword);
	}
	
	@Override
	public int insert(MemberVO memberVO) throws Exception {
		return memberDAO.insert(memberVO);
	}

	@Override
	public int update(MemberVO memberVO) throws Exception {
		return memberDAO.update(memberVO);
	}

	@Override
	public int delete(String email) throws Exception {
		return memberDAO.delete(email);
	}

	@Override
	public int selectOneNo(String email) throws Exception {
		// TODO Auto-generated method stub
		MemberVO memberVO = memberDAO.selectOne(email);
		
		return memberVO.getIdx();
	}

	@Override
	public MemberVO selectOneVo(String email) throws Exception {
		// TODO Auto-generated method stub
		
		return memberDAO.selectOne(email);
	}
	
	@Override
	public int updateDate(String email) throws Exception {
		// TODO Auto-generated method stub
		return memberDAO.updateDate(email);
	}
	
	@Override
	public int updateAuth(String email,String auth) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("email", email);
		paraMap.put("authority",auth);
		return memberDAO.updateData(paraMap);
	}

	@Override
	public int updatePassword(String email, String password) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("email", email);
		paraMap.put("password",password);
		return memberDAO.updateData(paraMap);
	}

	@Override
	public int addGiftlist(String email, int idx) throws Exception{
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("memberEmail", email);
		paraMap.put("boardIdx", idx);
		return giftlistDAO.insert(paraMap);
	}

	@Override
	public int giftCount() throws Exception{
		// TODO Auto-generated method stub
		return giftlistDAO.count();
	}

	@Override
	public int giftCount(String keyword) throws Exception{
		// TODO Auto-generated method stub
		return giftlistDAO.count(keyword);
	}
	
	@Override
	public List<GiftlistVO> giftList(int start, int end, String keyword, String email) throws Exception{
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("offset", start);
		paraMap.put("limit", end);
		paraMap.put("keyword", keyword);
		paraMap.put("memberEmail", email);
		return giftlistDAO.selectListMap(paraMap);
	}

	@Override
	public int delGiftlist(String email, int idx) throws Exception{
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("memberEmail", email);
		paraMap.put("boardIdx", idx);
		return giftlistDAO.delete(paraMap);
	}

	@Override
	public boolean checkGiftlist(String email, int idx) throws Exception{
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("memberEmail", email);
		paraMap.put("boardIdx", idx);
		GiftlistVO giftlistVO = giftlistDAO.selectOne(paraMap);
		if(giftlistVO != null){
			return true;
		}
		return false;
	}

	@Override
	public List<OrdersVO> ordersList(int start, int end, String email) throws Exception{
		// TODO Auto-generated method stub
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("offset", start);
		paraMap.put("limit", end);
		paraMap.put("memberEmail", email);
		return ordersDAO.selectListMap(paraMap);
	}

	@Override
	public int orderCount() throws Exception {
		// TODO Auto-generated method stub
		return ordersDAO.count();
	}
	
	@Override
	public int orderCount(String keyword) throws Exception {
		// TODO Auto-generated method stub
		return ordersDAO.count(keyword);
	}

	@Override
	public int delorderlist(int idx) throws Exception {
		// TODO Auto-generated method stub
		return ordersDAO.delete(idx);
	}

	@Override
	public List<CartVO> cartList(String email) {
		// TODO Auto-generated method stub
		return cartDAO.selectList(email);
	}

	@Override
	public List<OrdersVO> ordersTotalList(int firstRow, int endRow, String keyword) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", firstRow);
		map.put("limit", endRow);
		map.put("keyword", keyword);
		return ordersDAO.selectTotalListMap(map);
	}

	@Override
	public int orderCountTotalList(String keyword) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);

		return ordersDAO.countTotalList(map);
	}
}
