package com.securesw.shopping.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.securesw.shopping.service.CommonService;
import com.securesw.shopping.util.ImageUtil;
import com.securesw.shopping.util.Util;
import com.securesw.shopping.vo.EmailVO;
import com.securesw.shopping.vo.FileVO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class CommonController {
	
	@Resource(name = "util")
    private Util util;
	
	@Resource(name = "imageUtil")
	private ImageUtil imageUtil;
	
	@Resource(name = "commonService")
	private CommonService commonService;
	
	@RequestMapping(value="/upload.do")
	public String upload() throws Exception{
		return "/common/upload";
	}


	@SuppressWarnings("static-access")
	@RequestMapping(value="/upload.do",method=RequestMethod.POST)
	public String upload(@RequestParam(value="file1",required=false) MultipartFile multipartFile, Model model,
			Authentication auth) throws Exception{
		Calendar cal = Calendar.getInstance();
		UserDetails vo = (UserDetails) auth.getPrincipal();
		String loadPath = "/WEB-INF/files/";
		String uploader = vo.getUsername();
	    String fileName = multipartFile.getOriginalFilename();
		String fileExt = multipartFile.getOriginalFilename().substring( multipartFile.getOriginalFilename().lastIndexOf( ".") + 1, multipartFile.getOriginalFilename().length());
	    String tempName =  cal.getTimeInMillis() + "";
	    String replaceName = tempName +"."+ fileExt;
	    util.fileUpload(multipartFile, loadPath, replaceName);
	    FileVO fileVO = new FileVO(fileName, tempName, fileExt, uploader, 0);
	    commonService.insert(fileVO);
		return "/common/upload";
	}
	

	@RequestMapping(value = "/download.do", method = RequestMethod.GET)
	public ModelAndView download(HttpServletRequest request) throws Exception{
	  File file = new File("/WEB-INF/files/","images.jpg");
	  int boardIdx = 0;
	  FileVO fileVO = commonService.selectOneVo(boardIdx);
	  String fileName = fileVO.getName()+"."+fileVO.getExt();
	  request.setAttribute("fileName", fileName);  
	  
	  return new ModelAndView("fileDownloadView","filedown", file);  
	}
	

	@SuppressWarnings("static-access")
	@RequestMapping(value="/imageUpload.do",method=RequestMethod.POST)
	public String imageUpload(@RequestParam(value="file1",required=false) MultipartFile multipartFile, Model model) throws Exception{
		
		if ( multipartFile == null) return "/main/main.do";
		
		String fileExt = multipartFile.getOriginalFilename().substring( multipartFile.getOriginalFilename().lastIndexOf( ".") + 1, multipartFile.getOriginalFilename().length());
		
		File uploadFile =  File.createTempFile( "/WEB-INF/files/temp/", "." + fileExt);
		multipartFile.transferTo( uploadFile);
		
		File imageFile =  File.createTempFile( "/WEB-INF/files/temp/", "." + fileExt);
		
		if ( imageUtil.isImageFile ( fileExt))
		{
			imageUtil.uploadImage( uploadFile,imageFile, 100, 100);
			String imageBase64 = imageUtil.encodeToString( imageFile, fileExt);
			model.addAttribute("imageBase64", "data:image/png;base64," + imageBase64);
		}
		
		model.addAttribute("fileName", multipartFile.getOriginalFilename());		
		return "/common/upload";
	}


	@RequestMapping(value = "/isLogin.do")
	public String isLogin(Authentication auth) throws Exception {

		util.isMemberId(auth);

		return "common/success";
	}
	

	@SuppressWarnings("static-access")
	@RequestMapping(value="/fileUpload.do",method=RequestMethod.POST)
	public void fileUpload(@RequestParam(value="image_file",required=false) MultipartFile multipartFile,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		PrintWriter pw = response.getWriter();
     	String fileExt = multipartFile.getOriginalFilename().substring( multipartFile.getOriginalFilename().lastIndexOf( ".") + 1, multipartFile.getOriginalFilename().length());
		// String path = "/WEB-INF/files/";
		String path = "C:\\sw_contest\\workspace\\secureswshopping\\src\\main\\webapp\\resource\\upload\\";
		//String path = "/resource/upload/";
		String realname = UUID.randomUUID().toString() + "." + fileExt;

		if(multipartFile.getSize() > 0) {
			util.editorUpload(multipartFile, path, realname);
			System.out.println("Uploaded!!");
		}else{
			pw.print("400");
			pw.flush();
			pw.close();
			return;
		}
		
		response.setContentType("text/plain; charset=UTF-8");
	    
	      pw.print("{\"imageurl\" : \"/secureswshopping/resource/upload/"+realname+"\",\"filename\":\""+realname+"\",\"filesize\": 600,\"imagealign\":\"C\"}");
	    pw.flush();
	    pw.close();
	}
	
	@RequestMapping("/email.do")
	public String email() throws Exception{
		return "/common/testEmail";
	}
	@RequestMapping(value="/email.do",method=RequestMethod.POST)
	public String email(@RequestParam("reciver")String reciver,
			@RequestParam("title")String title,
			@RequestParam("content")String content,
			Model model, HttpServletRequest request) throws Exception {
	 
		EmailVO emailVO = new EmailVO();
	    
		if(reciver.isEmpty() || title.isEmpty() || content.isEmpty()){
			model.addAttribute("say", "Please fill all fields!");
			model.addAttribute("url", request.getContextPath()+"/email.do");
		}
	         
		emailVO.setReciver(reciver);
		emailVO.setSubject(title);
		emailVO.setContent(content);
		util.SendEmail(emailVO);
	         
		model.addAttribute("say", "Send it successfully");
		model.addAttribute("url", request.getContextPath()+"/email.do");	
		return "/error/alert";	
	}
	
	@RequestMapping("/redirect.do")
	public String redirect() throws Exception {
		return "/common/redirect";
	}
}
