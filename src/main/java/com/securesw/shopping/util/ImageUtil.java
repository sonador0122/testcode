package com.securesw.shopping.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



@Component("imageUtil")
public class ImageUtil {
	
	public static final int SAME = -1;
	public static final int RATIO = 0;
		public static boolean isImageFile ( String fileType)
	{
		
		String imageFormat = "";
		if ( fileType.indexOf( ".") > 0)
		{
			imageFormat = fileType.substring( fileType.lastIndexOf(".") + 1, fileType.length()).toLowerCase();
		}
		else
		{
			imageFormat = fileType;
		}
		
		if ( "jpg".equalsIgnoreCase( imageFormat) || "jpeg".equalsIgnoreCase( imageFormat) 
			 ||"gif".equalsIgnoreCase( imageFormat) || "png".equalsIgnoreCase( imageFormat))
		{
			return true;
		}
		
		return false;
	}
	
		public static void resizeImage(String loadFile, String saveFile, int maxDim) throws IOException 
	{
		File save = new File(saveFile.replaceAll("/", "\\" + File.separator));
		FileInputStream fis = new FileInputStream(loadFile.replaceAll("/", "\\"+ File.separator));
			  
		BufferedImage im = ImageIO.read(fis);
		Image inImage = new ImageIcon(loadFile).getImage();
			  
		double scale = (double) maxDim / (double) inImage.getHeight(null);
			  
		if (inImage.getWidth(null) > inImage.getHeight(null)) {
			scale = (double) maxDim / (double) inImage.getWidth(null);
		}
			  
		int scaledW = (int) (scale * inImage.getWidth(null));
		int scaledH = (int) (scale * inImage.getHeight(null));
			  
		BufferedImage thumb = new BufferedImage(scaledW, scaledH,BufferedImage.TYPE_INT_RGB);
			  
		Graphics2D g2 = thumb.createGraphics();
		g2.drawImage(im, 0, 0, scaledW, scaledH, null);
			  
		ImageIO.write(thumb, "jpg", save);//make image
	}
	
		public static void uploadImage(File src, File dest, int width, int height) throws IOException{
		FileInputStream srcIs = null;
		
		try {
			srcIs = new FileInputStream(src);
			ImageUtil.uploadImage(srcIs, dest, width, height); //오류
		} finally{
			if (srcIs != null){
				try {
					srcIs.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
	public static void uploadImage(InputStream src, File dest, int width, int height) throws IOException {
		
		BufferedImage srcImg = ImageIO.read(src);
		
		int srcWidth = srcImg.getWidth();
		int srcHeight = srcImg.getHeight();
		
		int destWidth = -1;
		int destHeight = -1;
		
		if(width == SAME){
			destWidth = srcWidth;
		}else if (width > 0){
			destWidth = width;
		}
		
		if(height == SAME){
			destHeight = srcHeight;
		}else if (width > 0){
			destHeight = height;
		}
		
		if(width == RATIO && height == RATIO){
			destWidth = srcWidth;
			destHeight = srcHeight;
		}else if (width == RATIO){
			double ratio = ((double)destHeight) / ((double)destHeight);
			destWidth = (int)((double)srcWidth * ratio);
		}else if (height == RATIO){
			double ratio = ((double)destWidth) / ((double)destWidth);
			destHeight = (int)((double)srcHeight * ratio);
		}
		
		BufferedImage destImg = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g = destImg.createGraphics();
		g.drawImage(srcImg, 0, 0, destWidth, destHeight, null);
		
		System.out.println("파일 경로:" + dest);
		ImageIO.write(destImg, "jpg", dest);//현재 오류
	}
	
	
  public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    public static String encodeToString(File image, String type) {
    	
    	BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read( image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return encodeToString( originalImage, type);
    }

    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
        }
        return imageString;
    }
}
