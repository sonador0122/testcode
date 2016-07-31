package com.securesw.shopping.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;



@Component(value="fileDownloadView")
public class FiledownUtil extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		File file = (File)model.get("filedown");
		res.setContentType("application/download; utf-8");
		res.setContentLength((int) file.length());
		 
		String userAgent = req.getHeader("User-Agent");
		String rename = (String) req.getAttribute("fileName");
		String fileName = rename == null ? file.getName() : rename;
		 
		if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1) {  res.setHeader("Content-Disposition", "attachment; filename="
		     + java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "\\ ") + ";");
		} else {                                                                                              
			res.setHeader("Content-Disposition",
		     "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1").replaceAll("\\+", "\\ ") + ";");
		}
		  
		res.setHeader("Content-Transfer-Encoding", "binary");
		OutputStream out = res.getOutputStream();
		FileInputStream fis = null;
		try {
		   fis = new FileInputStream(file);
		   FileCopyUtils.copy(fis, out);
		} catch (Exception e) {
		   e.printStackTrace();
		} finally {
		   if (fis != null) {
			   try {
				   fis.close();
			   } catch (Exception e) {
			   }
		   }
		}
		out.flush();
	}
}
