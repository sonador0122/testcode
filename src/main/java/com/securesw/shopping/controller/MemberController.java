package com.securesw.shopping.controller;

import java.io.File;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.securesw.shopping.service.LoginService;
import com.securesw.shopping.service.MemberService;
import com.securesw.shopping.service.ProductsService;
import com.securesw.shopping.util.ImageUtil;
import com.securesw.shopping.util.Util;
import com.securesw.shopping.vo.EmailVO;
import com.securesw.shopping.vo.GiftlistVO;
import com.securesw.shopping.vo.MemberVO;
import com.securesw.shopping.vo.OrdersVO;
import com.securesw.shopping.vo.PagingVO;



@Controller
@RequestMapping(value= "/member")
public class MemberController {
	public static final String loadPath = "/WEB-INF/files/";				

	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Resource(name = "loginService")
	private LoginService loginService;
	
	@Resource(name = "productsService")
    private ProductsService productsService;
	
	@Resource(name = "util")
    private Util util;
	
	@Resource(name = "imageUtil")
	private ImageUtil imageUtil;
	
	@RequestMapping(value= "/memberList.do")
	public ModelAndView memberList(@RequestParam(value="p",required=false) String p,
			@RequestParam(value="order",required=false) String order,
			@RequestParam(value="q",required=false) String q,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
        
		ModelAndView modelandview = new ModelAndView();
		
		String requestPageString = p;		
		String orderCond = order;			
		String keyword = q;					
		
        if(requestPageString == null || requestPageString.equals("")) {
            requestPageString = "1";
        }
        if(orderCond == null || orderCond.equals("")) {
        	orderCond = "idx_asc";
        }
        if(keyword == null || keyword.equals("")) {
        	keyword = null;
        }
        
        int requestPage = Integer.parseInt(requestPageString);
		
        if(requestPage <= 0){
            throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);
        }
        
        int totalCount;
        if(keyword  == null || keyword.equals(""))  {
            totalCount = memberService.count();
        } else {
            totalCount = memberService.count(keyword);
        }

        PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
        modelandview.addObject("pagingVO", pagingVO);
        modelandview.setViewName("/member/memberList");
        
        if(totalCount == 0){
            modelandview.addObject("memberVOList", Collections.<MemberVO>emptyList());
            request.setAttribute("hasMember", false);
            return modelandview;
        }
        
		List<MemberVO> lists = memberService.selectList(pagingVO.getFirstRow()-1,pagingVO.getEndRow(),orderCond,keyword);
		if(lists.isEmpty()){
			request.setAttribute("hasMember", false);
            return modelandview;
		}
		
		modelandview.addObject("memberVOList", lists);
		modelandview.addObject("order", orderCond);
		modelandview.addObject("keyword", keyword);
		request.setAttribute("hasMember", true);
		
		return modelandview;
	}
	
	@RequestMapping("/memberAdd.do")
	public String memberAdd() throws Exception{
		return "/member/memberAdd";
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value= "/memberAdd.do", method=RequestMethod.POST)
	public String memberAdd(@RequestParam("firstName")String firstName,
			@RequestParam("lastName")String lastName,
			@RequestParam("email")String email,
			@RequestParam("password")String password,
			@RequestParam("address")String address,
			@RequestParam("postcode")String postcode,
			@RequestParam(value="thumnail",required=false) MultipartFile multipartFile,
			Model model,HttpServletRequest request, HttpSession session) throws Exception{
		
		String imagePath = "default.jpg";
		int intcode;
		
		if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || postcode.isEmpty()){
			model.addAttribute("say", "잘못된 입력값이 있습니다.");
			model.addAttribute("url", request.getContextPath()+"/membe/memberAdd.do");
			return "/error/alert";
		}
		
		if(firstName.length() < 3 || password.length()<3|| email.length()<6 || lastName.length() < 3){
			model.addAttribute("say", "잘못된 입력값이 있습니다.");
			model.addAttribute("url", request.getContextPath()+"/member/memberAdd.do");
			return "/error/alert";
		}
		
		try{
			intcode = Integer.parseInt(postcode);
		}catch(Exception e){
			model.addAttribute("say", "잘못된 우편번호 입니다");
			model.addAttribute("url", request.getContextPath()+"/member/memberAdd.do");
			return "/error/alert";
		}
		
		if (intcode>999999 || intcode<100000) {
			model.addAttribute("say", "잘못된 우편번호 입니다");
			model.addAttribute("url", request.getContextPath()+"/member/memberAdd.do");
			return "/error/alert";
		}
		
		if (memberService.selectOne(email)){
			model.addAttribute("say", "이미 사용중인 이메일 입니다");
			model.addAttribute("url", request.getContextPath()+"/member/memberAdd.do");
			return "/error/alert";
		}
		
	
		if ( multipartFile.getSize() > 0 ) {
			String uploadPath = session.getServletContext().getRealPath("/")+"/WEB-INF/files/";
			
			String fileName = multipartFile.getOriginalFilename();
			if ( imageUtil.isImageFile ( fileName))
			{
				Calendar cal = Calendar.getInstance();
				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
				File uploadFile =  File.createTempFile( uploadPath +"temp/", fileType);
				multipartFile.transferTo( uploadFile);
				String tempName =  cal.getTimeInMillis() + "";
				String replaceName = tempName+"_thum"+ fileType;
				File thumbnail =  new File (uploadPath+replaceName);
				imageUtil.uploadImage( uploadFile, thumbnail, 100, 100);
				imagePath = replaceName;
			}
			else{
				model.addAttribute("say", "잘못된 이미지 파일입니다");
				model.addAttribute("url", request.getContextPath()+"/member/memberAdd.do");
				return "/error/alert";
			}
		}
		
		String encode = loginService.encoding(password);
		MemberVO memberVO = new MemberVO(firstName,lastName,email,encode,address,intcode,imagePath);
		memberService.insert(memberVO);
		
		return "redirect:/main/main.do";
	}
	
	@RequestMapping("/memberEdit.do")
	public String memberEdit(Authentication auth,Model model,HttpServletRequest request) throws Exception{
		if (isRememberMeAuthenticated()) {
			//send login for update
			setRememberMeTargetUrlToSession(request);
			model.addAttribute("loginUpdate", true);
			return "/main/login";
		} else {
			UserDetails vo = (UserDetails) auth.getPrincipal();
			MemberVO memberVO = memberService.selectOneVo(vo.getUsername());
			model.addAttribute("membervo", memberVO);
			return "/member/memberEdit";
		}
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value= "/memberEdit.do", method=RequestMethod.POST)
	public String memberEdit(@RequestParam("firstName")String firstName,
			@RequestParam("lastName")String lastName,
			@RequestParam("address")String address,
			@RequestParam("postcode")String postcode,
			@RequestParam(value="thumnail",required=false) MultipartFile multipartFile,
			Model model,Authentication auth,HttpServletRequest request,HttpSession session) throws Exception{
		
		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		MemberVO memberVO = memberService.selectOneVo(email);
		String imagePath = memberVO.getImagePath();
		int intcode;
		
		if(firstName.isEmpty() || lastName.isEmpty() || address.isEmpty()){
			firstName = memberVO.getFirstName();
			lastName = memberVO.getLastName();
			address = memberVO.getAddress();
		}
		try{
			intcode = Integer.parseInt(postcode);
		}catch(Exception e){
			System.err.println("변환오류 이유:" + e.getMessage());
			model.addAttribute("say", "잘못된 우편번호 입니다");
			model.addAttribute("url", request.getContextPath()+"/member/memberAdd.do");
			return "/error/alert";
		}
		
		if (intcode>999999 || intcode<100000) {
			model.addAttribute("say", "잘못된 우편번호 입니다");
			model.addAttribute("url", request.getContextPath()+"/member/memberAdd.do");
			return "/error/alert";
		}
		
		if(firstName.length() < 3 || lastName.length() < 3 || address.length()<3){
			model.addAttribute("say", "잘못된 입력값이 있습니다");
			model.addAttribute("url", request.getContextPath()+"/member/memberEdit.do");
			return "/error/alert";
		}
		
		//upload thumnail
		if ( multipartFile.getSize() > 0 ) {
			String uploadPath = session.getServletContext().getRealPath("/")+"/WEB-INF/files/";
			
			String fileName = multipartFile.getOriginalFilename();
			if ( imageUtil.isImageFile (fileName))
			{
				Calendar cal = Calendar.getInstance();
				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase();
				File uploadFile =  File.createTempFile( uploadPath +"temp/", fileType);
				multipartFile.transferTo( uploadFile);
				String tempName =  cal.getTimeInMillis() + "";
				String replaceName = tempName+"_thum"+ fileType;
				File thumbnail =  new File (uploadPath+replaceName);
				imageUtil.uploadImage( uploadFile, thumbnail, 100, 100);
				imagePath = replaceName;
			}
			else{
				model.addAttribute("say", "잘못된 이미지 입니다.");
				model.addAttribute("url", request.getContextPath()+"/member/memberAdd.do");
				return "/error/alert";
			}
		}

		MemberVO loadVO = new MemberVO(firstName,lastName,email,address,intcode,imagePath);
		memberService.update(loadVO);
	
		return "redirect:/main/main.do";
	}
	
	@RequestMapping("/memberDelete.do")
	public String memberDelete() throws Exception{
		return "/member/memberDelete";
	}
	@RequestMapping(value= "/memberDelete.do", method=RequestMethod.POST)
	public String memberDelete(Authentication auth,HttpServletRequest request, Model model) throws Exception{
		String reason = request.getParameter("check");
		System.out.println(reason);
		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		if(memberService.orderCount(email)!=0){
			model.addAttribute("say", "주문 정보가 존재합니다");
			model.addAttribute("url", request.getContextPath()+"/main/main.do");
			return "/error/alert";
		}
		memberService.delete(email);
		request.getSession().invalidate();
		return "redirect:/main/main.do";
	}
	
	
	@RequestMapping(value="/giveAuth.do")
	public String memberGiveAuth(@RequestParam("email")String email,
			@RequestParam("auth")String auth) throws Exception{
		
		if(auth == null) auth="true";
		String trimAuth = auth;
		String putAuth = null;
		
		if(trimAuth.compareTo("false") == 0){
			putAuth = "ROLE_ADMIN";
		}else{
			putAuth = "ROLE_USER";
		}
		
		memberService.updateAuth(email, putAuth);
		return "redirect:/member/memberList.do";
	}
	

	@RequestMapping(value="/addGiftlist.do", method=RequestMethod.POST)
	public void addGiftlist(@RequestParam("email")String email,
	@RequestParam("check")String check, @RequestParam("idx")int boardIdx,
	HttpServletResponse response) throws Exception{

		if(check == null) check="idx";
		
		if(check.compareTo("true") != 0){
			response.getWriter().print("404");
			return;
		}
		
		if(memberService.checkGiftlist(email, boardIdx)){
			response.getWriter().print("400");
			return;
		}else{
			memberService.addGiftlist(email, boardIdx);
			response.getWriter().print("200");
			return;
		}
	}
	
	@RequestMapping(value="/giftlist.do")
	public ModelAndView giftList(@RequestParam(value="p",required=false) String p,
			@RequestParam(value="q",required=false) String q,
			HttpServletRequest request, Authentication auth) throws Exception {
		
		ModelAndView modelandview = new ModelAndView();
		String requestPageString = p;		//paging
		String keyword = q;					//searching
		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		
		if(requestPageString == null || requestPageString.equals("")) {
            requestPageString = "1";
        }
        if(keyword == null || keyword.equals("")) {
        	keyword = null;
        }
        
        int requestPage = Integer.parseInt(requestPageString);
        
        if(requestPage <= 0){
            throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);
        }
        
        int totalCount;
        if(keyword  == null || keyword.equals(""))  {
            totalCount = memberService.giftCount();
        } else {
            totalCount = memberService.giftCount(keyword);
        }
        
     
        PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
        modelandview.addObject("pagingVO", pagingVO);
        modelandview.setViewName("/member/giftlist");
        
        if(totalCount == 0){
            modelandview.addObject("giftlist", Collections.<MemberVO>emptyList());
            request.setAttribute("hasMember", false);
            return modelandview;
        }
        
        List<GiftlistVO> lists = memberService.giftList(pagingVO.getFirstRow()-1,pagingVO.getEndRow(),keyword,email);
        if(lists.isEmpty()){
			request.setAttribute("hasMember", false);
            return modelandview;
		}
        modelandview.addObject("giftlist", lists);
		modelandview.addObject("keyword", keyword);
		request.setAttribute("hasUser", true);
		
		return modelandview; 
	}
	

	@RequestMapping(value="/delGiftlist.do")
	public String delGiftlist(@RequestParam("choice")String choice,
			@RequestParam("email")String email,@RequestParam("idx")int boardIdx,
			HttpServletRequest request, Model model) throws Exception{
		
		if(choice == null || choice.isEmpty()){
			model.addAttribute("say", "잘못된 입력값이 있습니다");
			model.addAttribute("url", request.getContextPath()+"/member/giftlist.do");
			return "/error/alert";
		}
		
		if(choice.compareTo("true") != 0){
			return "redirect:/member/giftlist.do";
		}
		
		if(memberService.delGiftlist(email,boardIdx) == 0){
			model.addAttribute("say", "이미 삭제된 정보입니다");
			model.addAttribute("url", request.getContextPath()+"/member/giftlist.do");
			return "/error/alert";
		}
		
		return "redirect:/member/giftlist.do";
	}
	
	
	@RequestMapping(value="/changePwd.do", method=RequestMethod.POST)
	public void changePwd(@RequestParam("password") String password,
			HttpServletResponse response,Authentication auth
			) throws Exception {
		
		if(password.length() < 3 || password.isEmpty()){
			response.getWriter().print("404");
			return;
		}
		
		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
		String encode = loginService.encoding(password);
		memberService.updatePassword(email, encode);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print("패스워드가 변경되었습니다");
	}

	
	@RequestMapping(value="/checkEmail.do", method=RequestMethod.POST)
	public void checkEmail(@RequestParam("email") String email,
			HttpServletResponse response) throws Exception {
		
		if(email.length() < 3 || email.isEmpty()){
			response.getWriter().print("404");
			return;
		}
		
		if (memberService.selectOne(email)){
			response.getWriter().print("400");
			return;
		}else{
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("사용가능한 이메일 주소입니다. 입력하신 이메일에서 인증키를 확인하여 주세요");
		}
		
		String activateEmail = util.encode2hex(email);
		
		System.out.println("활성메일:" + activateEmail);
	    ///메일 내용
        StringBuffer sb=new StringBuffer("아래 링크를 누르면 계정 활성화됩니다., 24시간 적용됩니다. 그렇지 않으면 다시 등록하여 주시면 감사 하겠습니다!</br>");
        
        sb.append("<a href=\"http://localhost:8080/secureswshopping/member/register.do?action=activate&email=");
        sb.append(activateEmail);
        sb.append("&validateCode=");
        sb.append(util.validateCode());
        sb.append("\">");
        sb.append("이메일 활성화 링크");
        sb.append("</a>");

    
        EmailVO emailVO = new EmailVO();
	    
		        
		emailVO.setReciver(email);
		emailVO.setSubject("이메일 인증 서비스");
		emailVO.setContent(sb.toString());
		util.SendEmail(emailVO);
         
	}
	
	@RequestMapping(value="/register.do",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView  register(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
	        String action = request.getParameter("action");
	        
	        ModelAndView modelandview=new ModelAndView();

	        if("register".equals(action)) {
	            //등록
	            String validateCode = request.getParameter("validateCode");
	         
	            modelandview.addObject("text","등록 성공");
	            modelandview.setViewName("/member/register_success");
	        }
	        return modelandview;
	    }
         
	
	
	@RequestMapping("/orders.do")
	public ModelAndView orderList(@RequestParam(value="p",required=false) String p,
			HttpServletRequest request, Authentication auth) throws Exception{
		
		ModelAndView modelandview = new ModelAndView();
		String requestPageString = p;		//paging
		UserDetails vo = (UserDetails) auth.getPrincipal();
		String email = vo.getUsername();
	
		if(requestPageString == null || requestPageString.equals("")) {
            requestPageString = "1";
        }
		
		int requestPage = Integer.parseInt(requestPageString);
		
        if(requestPage <= 0){
        	throw new IllegalArgumentException("requestPage <= 0 : " + requestPage);
        }
        
		int totalCount = memberService.orderCount();
		
	
        PagingVO pagingVO = util.paging(requestPage, 10, totalCount);
        modelandview.addObject("pagingVO", pagingVO);
        modelandview.setViewName("/member/memberOrder");
        if(totalCount == 0){
            modelandview.addObject("lists", null);
            return modelandview;
        }
        
		List<OrdersVO> lists = memberService.ordersList(pagingVO.getFirstRow()-1,pagingVO.getEndRow(),email);
		if(lists.isEmpty()){
			modelandview.addObject("lists", null);
            return modelandview;
		}
		modelandview.addObject("lists", lists);

		return modelandview;
	}
	

	@RequestMapping(value="/delOrderlist.do")
	public String delOrderlist(@RequestParam("choice")String choice,
			@RequestParam("idx")int idx,
			HttpServletRequest request, Model model) throws Exception{
			
		if(choice == null || choice.isEmpty()){
			model.addAttribute("say", "잘못된 입력값이 있습니다");
			model.addAttribute("url", request.getContextPath()+"/member/orders.do");
			return "/error/alert";
		}
			
		if(choice.compareTo("true") != 0){
			return "redirect:/member/orders.do";
		}
			
		if(memberService.delorderlist(idx) == 0){
			model.addAttribute("say", "이미 삭제된 주문 정보 입니다");
			model.addAttribute("url", request.getContextPath()+"/member/orders.do");
			return "/error/alert";
		}
			
		return "redirect:/member/orders.do";	
	}
		

	private boolean isRememberMeAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() == null) {
		
			return false;
		}
		
		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
		
	}
		private void setRememberMeTargetUrlToSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.setAttribute("targetUrl", "/member/memberEdit");
		}
	}
}
