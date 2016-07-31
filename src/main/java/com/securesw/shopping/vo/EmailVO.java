package com.securesw.shopping.vo;

import com.securesw.shopping.util.Util;

public class EmailVO {
	private static Util util;
	private String subject;
    private String content;
    private String regdate;
    private String reciver;
    private static String[] emailList;
    
    public String getReciver() {
        return reciver;
    }
    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getRegdate() {
        return regdate;
    }
    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
    
    public String[] getEmailList() {
		return emailList;
	}
	public void setEmailList(String[] emailList) {
		this.emailList = emailList;
	}
	
	public static void main(String[] args){
	   
		try{
			
		EmailVO emailVO = new EmailVO();
		emailList =new String[] { "test@naver.com","test@gmail.com" }; 
	
		emailVO.setEmailList(emailList);
		emailVO.setSubject("테스트 메일");
		emailVO.setContent("메일 전송 테스트");
		
		util.SendEmail(emailVO);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}