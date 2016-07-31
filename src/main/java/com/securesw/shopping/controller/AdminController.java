package com.securesw.shopping.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.securesw.shopping.service.AdminService;
import com.securesw.shopping.service.ProductsService;
import com.securesw.shopping.service.MemberService;
import com.securesw.shopping.util.Util;
import com.securesw.shopping.vo.ProductsVO;
import com.securesw.shopping.vo.OrdersVO;
import com.securesw.shopping.vo.PagingVO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Resource(name = "adminService")
	private AdminService adminService;

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "productsService")
	private ProductsService productsService;
	
	@Resource(name = "util")
    private Util util;
	
	@RequestMapping("/search.do")
	public ModelAndView searchMember(@RequestParam(value="p",required=false) String p,
			@RequestParam(value="q",required=false) String q,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		ModelAndView modelandview = new ModelAndView();
		
		String requestPageString = p;
		String keyword = q;
		
		if(keyword == null || keyword.equals("")) {
        	keyword = null;
        	modelandview.addObject("lists", null);
        	modelandview.setViewName("/admin/searchMember");
        	return modelandview;
        }
		if(requestPageString == null || requestPageString.equals("")) {
            requestPageString = "1";
        }
		
		int requestPage = Integer.parseInt(requestPageString);
		
        if(requestPage <= 0){
            throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);
        }
        
		int totalCount = adminService.count(keyword);
		

        PagingVO pagingVO = util.paging(requestPage, 5, totalCount);
        modelandview.addObject("pagingVO", pagingVO);
        modelandview.setViewName("/admin/searchMember");
        if(totalCount == 0){
            modelandview.addObject("lists", null);
            return modelandview;
        }
        
		List<OrdersVO> lists = adminService.selectList(pagingVO.getFirstRow()-1,pagingVO.getEndRow(),keyword);
		
		if(lists.isEmpty()){
			modelandview.addObject("lists", null);
            return modelandview;
		}
		modelandview.addObject("lists", lists);
		modelandview.addObject("keyword", keyword);
		return modelandview;
	}


	@RequestMapping("/orderList.do")
	public ModelAndView orderList(@RequestParam(value="p",required=false) String p,
								  @RequestParam(value="q",required=false) String keyword) throws Exception{

		ModelAndView modelandview = new ModelAndView();
		String requestPageString = p;

		if(requestPageString == null || requestPageString.equals("")) {
			requestPageString = "1";
		}

		int requestPage = Integer.parseInt(requestPageString);

		if(requestPage <= 0){
			throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);
		}

		if(keyword == null || keyword.equals("")) {
			keyword = null;
		}

		int totalCount = memberService.orderCountTotalList(keyword);
		modelandview.addObject("totalCount", totalCount);


		PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
		modelandview.addObject("pagingVO", pagingVO);
		modelandview.setViewName("/admin/orderList");

		int totalPrice = 0;

		if(totalCount == 0){
			modelandview.addObject("lists", null);
			modelandview.addObject("totalPrice", totalPrice);
			return modelandview;
		}

		List<OrdersVO> lists = memberService.ordersTotalList(pagingVO.getFirstRow()-1,pagingVO.getEndRow(), keyword);

		if(lists.isEmpty()){
			modelandview.addObject("lists", null);
			modelandview.addObject("totalPrice", totalPrice);
			return modelandview;
		}

		for(OrdersVO list : lists){
			totalPrice += list.getQuantity()*list.getTotalPrice();
		}
		modelandview.addObject("lists", lists);
		modelandview.addObject("totalPrice", totalPrice);

		return modelandview;
	}


	@RequestMapping(value = "/productsList.do")
	public ModelAndView productsList(@RequestParam(value = "p", required = false) String requestPageString,
								  @RequestParam(value="q",required=false) String keyword) throws Exception{

		ModelAndView modelAndView = new ModelAndView();

		if(requestPageString == null || requestPageString.equals("")) requestPageString = "1";
		int requestPage = Integer.parseInt(requestPageString);
		if(requestPage <= 0) return (ModelAndView)new ModelAndView("redirect:/products/list.do");

		if(keyword == null || keyword.equals("")) {
			keyword = null;
		}

		int totalCount = productsService.selectCountForStock(10, 0, keyword);



		PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
		modelAndView.addObject("pagingVO", pagingVO);
		modelAndView.setViewName("/admin/productsList");


		if(totalCount == 0){
			modelAndView.addObject("productsVOList", Collections.<ProductsVO>emptyList());
			modelAndView.addObject("hasProducts", false);
			return modelAndView;
		}

		List<ProductsVO> productsVOList = productsService.selectListForStock(10, 0, pagingVO.getFirstRow(), pagingVO.getEndRow(), keyword);

		modelAndView.addObject("productsVOList", productsVOList);
		modelAndView.addObject("hasProducts", true);

		return modelAndView;
	}

	@RequestMapping(value = "/statistics.do")
	public String statistics(){
		return "/admin/statistics";
	}
}
