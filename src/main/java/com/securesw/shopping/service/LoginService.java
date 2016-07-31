package com.securesw.shopping.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import com.securesw.shopping.dao.MemberDAO;
import com.securesw.shopping.vo.MemberVO;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service("loginService")
public class LoginService implements UserDetailsService{

	@Resource(name="memberDAO")
	private MemberDAO memberDAO;
	
	@Resource(name="passwordEncoder")
	private ShaPasswordEncoder encoder;
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Override
	public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		try {
			memberService.updateDate(email);
			MemberVO memberVO = memberDAO.selectOne(email);
			if(memberVO == null){
				return null;
			}
			String password = memberVO.getPassword();
			String role = memberVO.getAuthority();
			
			Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
			if("ROLE_USER".equals(role)){
				roles.add(new SimpleGrantedAuthority("ROLE_USER"));
				
			}else{
				roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}
			UserDetails user = new User(email,password,roles);
			return user;
 		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String encoding(String str){
		return encoder.encodePassword(str,null);
	}
}
