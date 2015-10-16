package com.pm.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileUpload {

    @RequestMapping(value = "/upload.do")  
    public String upload(@RequestParam(value = "imgFile", required = false) MultipartFile file, HttpServletRequest request,HttpServletResponse response, ModelMap model) {  
/*        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null; */ 
        System.out.println("开始上传ʼ");  
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = null;
		fileName = file.getOriginalFilename();
 
//        String fileName = new Date().getTime()+".jpg";  
		String localPath = path+"\\"+fileName;
        System.out.println("file.path = "+path+"\\"+fileName);  
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        //上传至服务器  
        try {  
            file.transferTo(targetFile);  
           /* bis = new BufferedInputStream(new FileInputStream(localPath));  
            bos = new BufferedOutputStream(response.getOutputStream());  
            byte[] buff = new byte[2048];  
            int bytesRead;  
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
                bos.write(buff, 0, bytesRead);  
            }  
            bis.close();  
            bos.close(); 
            bos.flush();*/
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        System.out.println(request.getContextPath()+"/upload/"+fileName);
        model.addAttribute("fileUrl", request.getContextPath()+"/upload/"+fileName);  
        model.addAttribute("filename", (Object)fileName);
        return "site/result";  
    }
    @RequestMapping("/download.do")
    	public static void download(HttpServletRequest request,  
                HttpServletResponse response,@RequestParam(value = "file")String filename) 
    			throws Exception {  
            response.setContentType("text/html;charset=UTF-8");  
            request.setCharacterEncoding("UTF-8");  
            BufferedInputStream bis = null;  
            BufferedOutputStream bos = null;  
            String ctxPath = request.getSession().getServletContext()  
                    .getRealPath("/")  
                    +"upload\\";  
            String downLoadPath = ctxPath + filename;  
            String file = request.getParameter("filename");
      
            long fileLength = new File(downLoadPath).length();  
      
            response.setContentType("application/octet-stream");  
            response.setHeader("Content-disposition", "attachment; filename="  
                    + new String(filename.getBytes("utf-8"), "ISO8859-1"));  
            response.setHeader("Content-Length", String.valueOf(fileLength));  
      
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
            bos = new BufferedOutputStream(response.getOutputStream());  
            byte[] buff = new byte[2048];  
            int bytesRead;  
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
                bos.write(buff, 0, bytesRead); 
                bos.flush();
            }  
            bis.close();  
            bos.close();
            bos.flush();
    } 
    public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
