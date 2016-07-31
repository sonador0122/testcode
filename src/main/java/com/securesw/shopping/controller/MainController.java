package com.securesw.shopping.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping(value="/main")
public class MainController {
	
	
	@RequestMapping(value="/main.do")
	public ModelAndView showMain(Authentication auth) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		
		if(auth != null){
			UserDetails vo = (UserDetails) auth.getPrincipal();
			modelAndView.addObject("vo", vo);
		}
		
		modelAndView.setViewName("/main/main");
		return modelAndView;
	}
	
	@RequestMapping("/admin.do")
	public String showAdmin() throws Exception{
		return "/main/admin";
	}
	
	
	
	@RequestMapping("/login.do")
	public String login(@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request, HttpServletResponse response, Model model,Authentication auth) throws Exception{
		
		if (error != null) {
			String targetUrl = getRememberMeTargetUrlFromSession(request, response);
						
			if(StringUtils.hasText(targetUrl)){
				model.addAttribute("targetUrl", targetUrl);
				model.addAttribute("loginUpdate", true);
				return "/member/memberEdit";
			}		
			
			model.addAttribute("say", "Check your Email and Password again");
			model.addAttribute("url", request.getContextPath()+"/main/login.do");
			return "/error/alert";
		}
		return "/main/login";
	}
	
	private String getRememberMeTargetUrlFromSession(HttpServletRequest request, HttpServletResponse response){
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if(session!=null){
			targetUrl = session.getAttribute("targetUrl")==null?"":session.getAttribute("targetUrl").toString();
		}
		
		// language cookie
		final Cookie locale = new Cookie("sw_contest", targetUrl);
		locale.setMaxAge(60*60*24*365);
		locale.setPath("/"); 
		response.addCookie(locale);
		
		return targetUrl;
	}
	
	public static void main(String[] args){
		System.out.println("main call");
		try{
			//parameter test
			boolean targetUrl = StringUtils.hasText("http://localhost:8080/secureswshopping/login.do");
			System.out.println(targetUrl);
			
		}catch(Exception e){
		}
	}
}
