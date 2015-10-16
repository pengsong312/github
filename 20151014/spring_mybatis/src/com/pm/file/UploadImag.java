package com.pm.file;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/img")
public class UploadImag {
	@RequestMapping("/uploadimg.do")
	 public String uploadHeadImage(
	            HttpServletRequest request,
	            @RequestParam(value = "x") String x,
	            @RequestParam(value = "y") String y,
	            @RequestParam(value = "h") String h,
	            @RequestParam(value = "w") String w,
	            @RequestParam(value = "imgFile") MultipartFile imageFile
	    ) throws Exception{
	        System.out.println("==========Start=============");
	        String realPath = request.getSession().getServletContext().getRealPath("/");
	        String resourcePath = "img\\";
	        if(imageFile!=null){
//	            if(FileUpload.allowUpload(imageFile.getContentType())){
//	                String fileName = FileUploadUtil.rename(imageFile.getOriginalFilename());
	                String fileName = imageFile.getOriginalFilename();
	                int end = fileName.lastIndexOf(".");
	                String saveName = fileName.substring(0,end);
	                File dir = new File(realPath + resourcePath);
	                if(!dir.exists()){
	                    dir.mkdirs();
	                }
	                File file = new File(dir,saveName+"_src.jpg");
	                imageFile.transferTo(file);
	                String srcImagePath = realPath + resourcePath + saveName;
	                int imageX = Integer.parseInt(x);
	                int imageY = Integer.parseInt(y);
	                int imageH = Integer.parseInt(h);
	                int imageW = Integer.parseInt(w);
	                //
	                System.out.println("==========imageCutStart=============");
	                ImageCut.imgCut(srcImagePath,imageX,imageY,imageW,imageH);
	                System.out.println("==========imageCutEnd=============");
	            }
//	        }
	        return "img/img";
	        }
}
