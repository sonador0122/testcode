package com.securesw.shopping.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.securesw.shopping.error.CannotReplyException;
import com.securesw.shopping.error.LastChildAleadyExistsException;
import com.securesw.shopping.error.MemberIdNotEqualsException;
import com.securesw.shopping.error.MemberIdNotFoundException;
import com.securesw.shopping.error.NotFoundException;
import com.securesw.shopping.vo.EmailVO;
import com.securesw.shopping.vo.PagingVO;
import com.securesw.shopping.vo.ReplyVO;



@Component("util")
public class Util {

	@Autowired
    protected JavaMailSender  mailSender;
    public PagingVO paging(int requestPage, int countPerPage, int totalCount) {

        final int PAGING_PAGE = 10;
        int totalPageCount = 0;
        int firstRow = 0;
        int endRow = 0;
        int beginPage = 0;
        int endPage = 0;

        if(totalCount != 0 || requestPage < 0) {
           totalPageCount = totalCount / countPerPage;        
            if(totalCount % countPerPage > 0) {
                totalPageCount++;                               
            }

             firstRow = (requestPage - 1) * countPerPage + 1;
            endRow = firstRow + countPerPage - 1;               

            if(endRow > totalCount){
                endRow = totalCount;
            }

            if(totalCount != 0) {
                beginPage = (requestPage - 1) / PAGING_PAGE * PAGING_PAGE + 1;
                endPage = beginPage + PAGING_PAGE - 1;
                if(endPage > totalPageCount){
                    endPage = totalPageCount;
                }
            }
        } else {
            requestPage = 0;
        }

        PagingVO pagingVO = new PagingVO(requestPage, totalPageCount, firstRow, endRow, beginPage, endPage);

        return pagingVO;
    }

    public void isMemberId(String memberId) throws MemberIdNotFoundException {
        if(memberId == null || memberId.equals("")){
            throw new MemberIdNotFoundException("로그인을 하지 않았습니다.");
        }
    }

    public String isMemberId(Authentication auth) throws MemberIdNotFoundException{

    	UserDetails vo = (UserDetails) auth.getPrincipal();
        String memberId = vo.getUsername();

        if(memberId == null || memberId.equals("")){
            throw new MemberIdNotFoundException("로그인을 하지 않았습니다.");
        }

        return memberId;
    }

     public void isEqualMemberId(String email, String memberId) throws MemberIdNotEqualsException {
        if (!email.equals(memberId)) {
            throw new MemberIdNotEqualsException("작성자의 ID와 로그인한 사용자의 ID가 다릅니다.");
        }
    }

   public void checkParent(ReplyVO parent, int parentIdx) throws NotFoundException, CannotReplyException {
        if(parent == null){
            throw new NotFoundException("부모글이 존재하지 않음 : " + parentIdx);
        }

        int parentLevel = parent.getLevel();
        if(parentLevel == 1){
            throw new CannotReplyException("마지막 레벨 글에는 답글을 달 수 없습니다 : " + parent.getIdx());
        }
    }

    public String getSearchMinSeqNum(ReplyVO parent){
        String parentSeqNum = parent.getSequenceIdx();
        DecimalFormat decimalFormat = new DecimalFormat("000000000000");
        long parentSeqLongValue = Long.parseLong(parentSeqNum);
        long searchMinLongValue = 0;
        switch (parent.getLevel()){                                            
            case 0:
                searchMinLongValue = parentSeqLongValue / 100L * 100L;        
                break;
        }
        return decimalFormat.format(searchMinLongValue);
    }
      public String getSequenceIdx(ReplyVO parent, String lastChildSeq) throws LastChildAleadyExistsException {
        long parentSeqLong = Long.parseLong(parent.getSequenceIdx());
        int parentLevel = parent.getLevel();

        long decUnit = 0;
        if(parentLevel == 0){
            decUnit = 1L;
        }

        String sequenceIdx = null;

        DecimalFormat decimalFormat = new DecimalFormat("000000000000");
        if(lastChildSeq == null){   
            sequenceIdx = decimalFormat.format(parentSeqLong - decUnit);
        } else {    
            
            String orderOfLastChildSeq = null;
            if(parentLevel == 0){
                orderOfLastChildSeq = lastChildSeq.substring(10, 12);      
                sequenceIdx = lastChildSeq;
            }
            if(orderOfLastChildSeq.equals("00")){
                throw new LastChildAleadyExistsException("마지막 자식글이 이미 존재합니다 : " + lastChildSeq);
            }
            long seq = Long.parseLong(sequenceIdx) - decUnit;
            sequenceIdx = decimalFormat.format(seq);
        }
        return sequenceIdx;
    }

  
    public static void fileUpload(MultipartFile multipartFile, String path, String fileName) throws IOException {
    	 long fileSize = multipartFile.getSize();
    	 InputStream is = null;
    	 OutputStream out = null;
    	 
    	 try{
    		 if (fileSize > 0) {
    			 is = multipartFile.getInputStream();
    			 File realUploadDir = new File(path);
    			 if (!realUploadDir.exists()) {    
    				 realUploadDir.mkdirs();
    			 }
    			 out = new FileOutputStream(path +"/"+ fileName);
    			 FileCopyUtils.copy(is, out);       
    		 }else{
    			 new IOException("Wrong File");
    		 }
    	 }catch(Exception e){
    		 e.printStackTrace();
    		 new IOException("Upload failed");
    	 }finally{
    		 if(out != null){out.close();}
    		 if(is != null){is.close();}
    	 }
    }
    
    public static void editorUpload(MultipartFile multipartFile, String path, String fileName) throws IOException {
        
		Long size = 0L;
        File file = new File(path);
       
        if(!file.exists()) {
            file.mkdirs();
        }
        size = multipartFile.getSize();
        InputStream is = multipartFile.getInputStream();
        OutputStream os=new FileOutputStream(path + fileName);
        int numRead;
        byte b[] = new byte[(int)multipartFile.getSize()];
        while((numRead = is.read(b,0,b.length)) != -1){
            os.write(b,0,numRead);
        }
        if(is != null)  is.close();
        os.flush();
        os.close();
    }
    
     public void SendEmail(EmailVO emailVO) throws Exception {
        
        MimeMessage msg = mailSender.createMimeMessage();
        msg.setSubject(emailVO.getSubject());
        msg.setText(emailVO.getContent());
        msg.setRecipient(RecipientType.TO , new InternetAddress(emailVO.getReciver()));
         
        mailSender.send(msg); 
    }
     
     
     
     
     public static byte[] encode2bytes(String source) {
    	  byte[] result = null;
    	  try {
    	   MessageDigest md = MessageDigest.getInstance("MD5");
    	   md.reset();
    	   md.update(source.getBytes("UTF-8"));
    	   result = md.digest();
    	  } catch (NoSuchAlgorithmException e) {
    	   e.printStackTrace();
    	  } catch (UnsupportedEncodingException e) {
    	   e.printStackTrace();
    	  }

    	  return result;
    	 }

    	 public static String encode2hex(String source) {
    	  byte[] data = encode2bytes(source);

    	  StringBuffer hexString = new StringBuffer();
    	  for (int i = 0; i <data.length; i++) {
    	   String hex = Integer.toHexString(0xff & data[i]);

    	   if (hex.length() == 1) {
    	    hexString.append('0');
    	   }

    	   hexString.append(hex);
    	  }

    	  return hexString.toString();
    	 }

    	
    	 public static boolean validate(String unknown , String okHex) {
    	  return okHex.equals(encode2hex(unknown));
    	 }
     
    	 public static int validateCode(){
    	   int validateCode =(int) Math.floor(Math.random() * 1000000)+100000;
 		     if(validateCode>1000000){
 		    	validateCode = validateCode - 100000;
 	      	}
 		  return validateCode;
       }
}
