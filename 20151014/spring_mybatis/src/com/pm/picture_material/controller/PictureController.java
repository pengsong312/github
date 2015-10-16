package com.pm.picture_material.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.omg.CORBA.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pm.help.dao.CommDao;
import com.pm.help.utils.CheckChineseName;
import com.pm.mobile_case.entity.Mobile;
import com.pm.picture_material.entity.Picture;
import com.pm.system.entity.User;
import com.pm.template_case.entity.Template;
import com.sun.org.apache.regexp.internal.recompile;

@Controller
@RequestMapping("/picture")
public class PictureController {
	@Resource
	private CommDao commDao;
	List<Picture> pictureList = null;
	
	@RequestMapping("/checkPictureIntf.do")
	public void checkSiteIntf(HttpServletRequest request,HttpServletResponse response){
		Picture picture = null;
		Picture picture1 = new Picture();
		boolean flag = false;
		String pictureIntf = request.getParameter("pictureIntf");
        //新建一个printWriter对象  
        PrintWriter pw = null;  
        //通过response 获取pw  
        try {
			pw=response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
        if(pictureIntf!=null&&!"".equals(pictureIntf)){
        	picture1.setPictureIntf(pictureIntf);
        	 try {
        		 picture  = (Picture)commDao.findOne("findAllPicture", picture1);
     			 response.setCharacterEncoding("utf-8");  	 
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        }
        flag = new CheckChineseName().checkName(pictureIntf);
        
        if(!flag){
        	pw.print("{\"result\":\"3\"}");
        }else if(pictureIntf==null||"".equals(pictureIntf)){
          	pw.print("{\"result\":\"9\"}");      
        }else if(pictureIntf.length()<3||pictureIntf.length()>10){
        	pw.print("{\"result\":\"11\"}"); 
        }else if(picture==null){  
            //用pw对象传递json  
            pw.print("{\"result\":\"0\"}");//该用户名可以使用  
        }else if(picture!=null){
        	pw.print("{\"result\":\"8\"}");
        }else{  
            pw.print("{\"result\":\"1\"}");  
        }  
        pw.flush();  
        pw.close();    
    }
	
	@RequestMapping("/findAllPicture.do")
	public String findAllPicture(Model model,Picture picture,HttpServletRequest request) {
		try {
			pictureList = commDao.findList("findAllPicture",picture);
			JSONObject json = new JSONObject();
			json.put("list", pictureList);
			model.addAttribute("json", json);
			model.addAttribute("pictureList", pictureList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询图片案例失败！");
		}
		return "picture/picture";
	}
	@RequestMapping("/findUpdatePictures.do")
	public String findUpdatePictures(Model model,Picture picture,HttpServletRequest request) {
		try {
			pictureList = commDao.findList("findAllPicture",picture);
			JSONObject json = new JSONObject();
			json.put("list", pictureList);
			model.addAttribute("json", json);
			model.addAttribute("pictureList", pictureList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询图片案例失败！");
		}
		return "picture/choosePicture";
	}
	public void findPictures(Model model) {
		try {
			pictureList = commDao.findList("findAllPicture",null);
			JSONObject json = new JSONObject();
			json.put("list", pictureList);
			model.addAttribute("json", json);
			model.addAttribute("pictureList", pictureList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询图片案例失败！");
		}
	}
	public void  findUpdatePicture(Model model,Picture picture) {
		try {
			picture = (Picture) commDao.findOne("findPictureByIntf", picture);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("updatePicture", picture);
	}
	/**
	 * 
	 * @param model
	 * @param picture
	 * @return 单个照片浏览
	 */
	@RequestMapping("findOnePicture.do")
	public String findOnePicture(Model model,Picture picture){
		Picture onePicture = null;
		try {
			onePicture = (Picture)commDao.findOne("findAllPicture", picture);
			model.addAttribute("picture", onePicture);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "picture/picture";
	}
	/**
	 * 增加访问量
	 */
	@RequestMapping("addPictureDownload.do")
	public String  addPictureDownload(Model model,Picture picture) {
		picture.setDownload(picture.getDownload()+1);
		try {
			commDao.update("addPictureDownload", picture);
			pictureList = commDao.findList("findAllPicture",null);
			model.addAttribute("pictureList", pictureList);
			this.findPictures(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "picture/picture";
	}
	/*
	 * 增加点赞 
	 */
	@RequestMapping("addPictureThumb.do")
	public String addPictureThumb(Picture picture,Model model){
		picture.setThumb(picture.getThumb()+1);
		try {
			commDao.update("addPictureThumb", picture);
			pictureList = commDao.findList("findAllPicture",null);
			this.findPictures(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "picture/picture";  
	}
	@RequestMapping("addPictureThumb1.do")
	public void addPictureThumb(Picture picture,HttpServletRequest request,HttpServletResponse response){
		int flag = 0;
		PrintWriter pw = null;  
	    //通过response 获取pw  
	        
		try {
			pw=response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			} 
		try {
			picture =(Picture) commDao.findOne("findPictureByIntf", picture);
			picture.setThumb(picture.getThumb()+1);
			flag = commDao.update("addPictureThumb", picture);
			response.setCharacterEncoding("utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("点赞失败");
		}
		if(flag>0){
       	 	pw.print("{\"result\":\"0\"}");
       }else{  
           pw.print("{\"result\":\"1\"}");  
       }  
       pw.flush();  
       pw.close();    
	}
	/**
	 * 增加查看量
	 */
	@RequestMapping("addPictureLook.do")
	public void  addPictureLook(Picture picture,HttpServletRequest request,HttpServletResponse response) {
		int flag = 0;
		PrintWriter pw = null;  
	    //通过response 获取pw  
	        
		try {
			pw=response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			} 
		try {
			picture =(Picture) commDao.findOne("findPictureByIntf", picture);
			picture.setLook(picture.getLook()+1);
			flag = commDao.update("addPictureLook", picture);
			response.setCharacterEncoding("utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("点赞失败");
		}
		if(flag>0){
       	 	pw.print("{\"result\":\"0\"}");
       }else{  
           pw.print("{\"result\":\"1\"}");  
       }  
       pw.flush();  
       pw.close();    
	}
	/**
	 * 图片下载到本地
	 */
	@RequestMapping("downloadPicture.do")
	private void downloadPicture(HttpServletRequest request,HttpServletResponse response,Picture picture,Model model) { 
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setContentType("text/html;charset=UTF-8");  
			request.setCharacterEncoding("UTF-8");   
			String ctxPath = request.getSession().getServletContext()  
			        .getRealPath("/")  
			        +"upload\\picture\\"; 
			String filename = request.getParameter("filename"); 
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
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bis.close();  
				bos.close();
				bos.flush();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
      }  
	/*
	 * 添加图片案例上传
	 * 
	 */
	@RequestMapping("/toChoosePicture.do")
	public String toChoosePicture(Model model){
		this.findPictures(model);
		return "picture/choosePicture";
	}
	
	@RequestMapping("/toUploadPicture.do")
	public String toUploadPicture(Model model,Picture picture){
		return "picture/uploadPicture";
	}
	@RequestMapping("uploadPicture.do")
	public String uploadPicture(Model model,Picture picture,
			@RequestParam(value="file",required=false)MultipartFile file,HttpServletRequest request){
		int flag=0;
		
		picture.setPictureNumber(new DecimalFormat("00000").format(Math.random()*1000000));
	
		User user = (User) request.getSession().getAttribute("user");
		picture.setUploadUser(user.getRealName());
		String path = request.getContextPath()+"/upload/picture/"+file.getOriginalFilename();
		picture.setPictureUrl(path);
		String realPath = request.getSession().getServletContext().getRealPath("upload")+"/picture";
		File targetFile = new File(realPath, file.getOriginalFilename());  
		if(targetFile.exists()){  
            targetFile.delete();   
        }  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }
        try {
			file.transferTo(targetFile);
			flag = commDao.update("uploadPicture", picture);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(flag>0){
			model.addAttribute("msg", "添加图片案例成功！");
			model.addAttribute("linked", "picture/findAllPicture.do");
			return "commons/returnPage";
		}else{
			model.addAttribute("msg", "添加图片案例失败");
			return "picture/uploadPicture";
		}	
	}
	@RequestMapping("/choosePicture.do")
	public String choosePicture(Model model){
		this.findPictures(model);
		return "picture/choosePicture";
	}
	@RequestMapping("/toUpdatePicture.do")
	public String toUpdatePicture(Model model,Picture picture){
		
		this.findUpdatePicture(model, picture);
		return "picture/updatePicture";
		
	}
	@RequestMapping("/updatePicture.do")
	public String updatePicture(Model model,Picture picture,@RequestParam(value="file",required=false)MultipartFile file,HttpServletRequest request){
		int flag = 0;
		String fileName = file.getOriginalFilename();
		if(!fileName.isEmpty()){
			String path =request.getContextPath()+"/upload/picture/" + fileName;
			picture.setPictureUrl(path);
			String realPath = request.getSession().getServletContext().getRealPath("upload")+"/picture";
			File targetFile = new File(realPath,fileName );  
			if(targetFile.exists()){  
	            targetFile.delete();  
	        }  
	        if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        }  
			try {
				 file.transferTo(targetFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		try {
			flag = commDao.update("updatePicture", picture);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.findPictures(model);
		if(flag>0){
			return "picture/choosePicture";
		}else{
			model.addAttribute("msg", "修改图片失败");
			model.addAttribute("linked","picture/choosePicture.jsp");
			return "commons/returnPage";
		}	
	}
	@RequestMapping("/delPicture.do")
	public String delPicture(Picture picture,Model model){
		int flag = 0;
		try {
			flag = commDao.update("delPicture", picture);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.findPictures(model);
		if(flag>0){
			return "picture/choosePicture";
		}else{
			model.addAttribute("msg", "删除失败");
			model.addAttribute("linked","picture/choosePicture.jsp");
			return "commons/returnPage";
		}
	}

}
