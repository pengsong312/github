package com.pm.site_design.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pm.help.dao.CommDao;
import com.pm.help.utils.CheckChineseName;
import com.pm.site_design.entity.WebCase;

@Controller
@RequestMapping("/site")
public class WebCaseController {
	@Resource
	CommDao dao;
	List<WebCase> webCaseList = null;
	/*
	 * 单纯查询全部网站案例信息 
	 */
	public void findAllWebCase(Model model) {
		String pictureStr  = null;
		try {
			webCaseList = dao.findList("findAllWebCase", null);
			for(int i = 0;i<webCaseList.size();i++){
				pictureStr = webCaseList.get(i).getPicture();
				if(pictureStr!=null&&!"".equals(pictureStr)){
					if(pictureStr.indexOf(",")<0){
						webCaseList.get(i).setPicture(webCaseList.get(i).getPicture());
					}else{
						webCaseList.get(i).setPicture(pictureStr.substring(0,webCaseList.get(i).getPicture().indexOf(",")));
					}	
					webCaseList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
				}			
			}
			JSONObject json = new JSONObject();
			json.put("list",webCaseList);
			model.addAttribute("json", json);
			model.addAttribute("webCaseList",webCaseList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping("/checkSiteIntf.do")
	public void checkSiteIntf(WebCase webCase,HttpServletRequest request,HttpServletResponse response){
		WebCase site = null;
		
		String siteIntf = request.getParameter("siteIntf");
        //新建一个printWriter对象  
        PrintWriter pw = null;  
        boolean flag = false;
        //通过response 获取pw  
        try {
			pw=response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
        if(siteIntf!=null&&!"".equals(siteIntf)){
        	webCase.setSiteIntf(siteIntf);
        	 try {
             	site  = (WebCase)dao.findOne("findOneWebCase", webCase);
     			response.setCharacterEncoding("utf-8");  	 
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        }
        flag = new CheckChineseName().checkName(siteIntf);
        if(!flag){
        	pw.print("{\"result\":\"3\"}");
        }else if(siteIntf==null||"".equals(siteIntf)){
       	 	pw.print("{\"result\":\"9\"}");  
        }else if(siteIntf.length()>10||siteIntf.length()<3){
        	pw.print("{\"result\":\"11\"}");
        }else if(site==null){  
            //用pw对象传递json  
            pw.print("{\"result\":\"0\"}");//该用户名可以使用  
        }else if(site!=null){
        	pw.print("{\"result\":\"8\"}");//该用户名可以使用  
        }else{  
            pw.print("{\"result\":\"1\"}");  
        }  
        pw.flush();  
        pw.close();    
    }
	@RequestMapping("/findAllWebCase.do")
	public String findAllWebCase(Model model,WebCase webCase,HttpServletRequest request) {
		String pictureStr  = null;
		try {
			webCaseList = dao.findList("findAllWebCase", webCase);
			for(int i = 0;i<webCaseList.size();i++){
				pictureStr = webCaseList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					webCaseList.get(i).setPicture(webCaseList.get(i).getPicture());
				}else{
					webCaseList.get(i).setPicture(pictureStr.substring(0,webCaseList.get(i).getPicture().indexOf(",")));
				}	
				webCaseList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
			JSONObject json = new JSONObject();
			json.put("list",webCaseList);
			model.addAttribute("json", json);
			request.getSession().setAttribute("json", json);
			request.getSession().setAttribute("webCaseList", webCaseList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("webCaseListRequest", webCaseList);
		
		return "index";	
	}
	@RequestMapping("/findUpdateSite.do")
	public String findUpdateSite(Model model,WebCase webCase,HttpServletRequest request) {
		String pictureStr  = null;
		try {
			webCaseList = dao.findList("findAllWebCase", webCase);
			for(int i = 0;i<webCaseList.size();i++){
				pictureStr = webCaseList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					webCaseList.get(i).setPicture(webCaseList.get(i).getPicture());
				}else{
					webCaseList.get(i).setPicture(pictureStr.substring(0,webCaseList.get(i).getPicture().indexOf(",")));
				}	
				webCaseList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
			JSONObject json = new JSONObject();
			json.put("list",webCaseList);
			model.addAttribute("json", json);
			request.getSession().setAttribute("json", json);
			request.getSession().setAttribute("webCaseList", webCaseList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("webCaseListRequest", webCaseList);
		
		return "site/chooseSite";	
	}
	@RequestMapping("findMoreWebCase.do")
	public String findMoreWebCase(Model model,WebCase webCase,HttpServletRequest request) {
		String pictureStr  = null;
		try {
			webCaseList = dao.findList("findAllWebCase", webCase);
			for(int i = 0;i<webCaseList.size();i++){
				pictureStr = webCaseList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					webCaseList.get(i).setPicture(webCaseList.get(i).getPicture());
				}else{
					webCaseList.get(i).setPicture(pictureStr.substring(0,webCaseList.get(i).getPicture().indexOf(",")));
				}	
				webCaseList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("webCaseList", webCaseList);
		request.getSession().setAttribute("webCaseListRequest", webCaseList);
		return "index";	
	}
	public void addSiteLook(WebCase webCase) {
		webCase.setLook(webCase.getLook()+1);
		try {
			dao.update("addSiteLook", webCase);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("添加网站浏览与点赞失败");
		}
	}
	public void  findOneSiteByNumber(WebCase webCase) {
		
	}
	@RequestMapping("/addSiteThumb.do")
	public void addSiteThumb(WebCase webCase,Model model,HttpServletRequest request,HttpServletResponse response){
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
			webCase =(WebCase) dao.findOne("findSiteByNumber", webCase);
			webCase.setThumb(webCase.getThumb()+1);
			flag = dao.update("addSiteThumb", webCase);
			response.setCharacterEncoding("utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("添加网站点赞失败");
		}
		if(flag>0){
       	 	pw.print("{\"result\":\"0\"}");
       }else{  
           pw.print("{\"result\":\"1\"}");  
       }  
       pw.flush();  
       pw.close();    
	}
	@RequestMapping("/findOneSiteByIntf.do")
	public String findOneSiteByIntf(Model model,WebCase webCase,HttpServletRequest request){
		String pictureStr  = null;
		WebCase webCaseDetail = null;
		WebCase webCaseDetail2 = null;
		String siteIntf = (String) request.getParameter("siteIntf");
		webCase.setSiteIntf(siteIntf);
		try {
			webCaseDetail = (WebCase)dao.findOne("findAllWebCase", webCase);
			this.addSiteLook(webCaseDetail);
			webCaseDetail2 = (WebCase)dao.findOne("findAllWebCase", webCase);
			pictureStr = webCaseDetail2.getPicture();
			if(pictureStr.indexOf(",")<0){
				webCaseDetail2.setPicture(webCaseDetail2.getPicture());
			}else{
				webCaseDetail2.setPicture(pictureStr.substring(0,webCaseDetail2.getPicture().indexOf(",")));
			}	
			webCaseDetail2.setPictureList(Arrays.asList(pictureStr.split(",")));
			model.addAttribute("webCaseDetail", webCaseDetail2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "site/detail";
	}
	public void findOneSiteByIntf(Model model,WebCase webCase){
		String pictureStr  = null;
		WebCase webCaseDetail = null;
		WebCase webCaseDetail2 = null;
		try {
			webCaseDetail = (WebCase)dao.findOne("findAllWebCase", webCase);
			webCaseDetail2 = (WebCase)dao.findOne("findAllWebCase", webCase);
			pictureStr = webCaseDetail2.getPicture();
			if(pictureStr.indexOf(",")<0){
				webCaseDetail2.setPicture(webCaseDetail2.getPicture());
			}else{
				webCaseDetail2.setPicture(pictureStr.substring(0,webCaseDetail2.getPicture().indexOf(",")));
			}	
			webCaseDetail2.setPictureList(Arrays.asList(pictureStr.split(",")));
			model.addAttribute("webCaseDetail", webCaseDetail2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/toUploadSite.do")
	public String toUploadSite(){
		return "site/uploadSite";
	}
	@RequestMapping("/uploadSite.do")
	public String uploadSite(Model model,WebCase webCase,
				HttpServletRequest request,@RequestParam(value="file")CommonsMultipartFile[] files){		
		int flag = 0;
		File targetFile=null;
		String path = "";
		StringBuffer sb = new StringBuffer();	
		webCase.setSiteNumber(new DecimalFormat("00000").format(Math.random()*1000000));
		for(int i=0;i<files.length;i++){
			String fileName = request.getContextPath()+"/upload/web/" +files[i].getOriginalFilename();
			sb.append(fileName);
			sb.append(",");
		}
		int lastIndex = sb.toString().lastIndexOf(",");
		path = sb.toString().substring(0, lastIndex);
		webCase.setPicture(path);
		String realPath = request.getSession().getServletContext().getRealPath("upload")+"/web";
		for(int j=0;j<files.length;j++){
			targetFile = new File(realPath, files[j].getOriginalFilename());  
			
			if(targetFile.exists()){  
	            targetFile.delete();  
	        }  
	        if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        }  
	        try {
				files[j].transferTo(targetFile);				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 try {
				
			 flag = dao.update("uploadSite", webCase);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
/*		String path =request.getContextPath()+"/upload/" + file.getOriginalFilename();
		webCase.setPicture(path);
		String realPath = request.getSession().getServletContext().getRealPath("upload");
		File targetFile = new File(realPath, file.getOriginalFilename());  
		if(targetFile.exists()){  
            targetFile.delete();  
        }  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
		try {
			 file.transferTo(targetFile);
			 flag = dao.update("uploadSite", webCase);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/	
		if(flag>0){
			model.addAttribute("msg","上传网站案例成功！");
			model.addAttribute("linked","site/findAllWebCase.do");
			return "commons/returnPage";
		}else{
			model.addAttribute("msg","添加网站案例失败！");
			return "site/uploadSite";
		}	
	}
	@RequestMapping("chooseSite.do")
	public String chooseSite(Model model){
		this.findAllWebCase(model);
		return "site/chooseSite";
		
	}
	@RequestMapping("/toUpdateSite.do")
	public String toUpdateSite(Model model,WebCase webCase){
		WebCase updateSite  = null;
		try {
			updateSite = (WebCase)dao.findOne("findOneWebCase", webCase);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("updateSite", updateSite);
		return "site/updateSite";
		
	}
	@RequestMapping("updateSite.do")
	public String updateSite(Model model,WebCase webCase,HttpServletRequest request,@RequestParam(value="file")CommonsMultipartFile[] files){
		WebCase updateWebCase = null;
		int flag = 0;
		File targetFile =null;
		if(files.length>1){
			String path = "";
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<files.length;i++){
				String fileName = request.getContextPath()+"/upload/web/" +files[i].getOriginalFilename();
				String realPath = request.getSession().getServletContext().getRealPath("upload")+"/web";
				targetFile = new File(realPath, files[i].getOriginalFilename());  
				if(targetFile.exists()){  
			        targetFile.delete();  
			    }  
			    if(!targetFile.exists()){  
			        targetFile.mkdirs();  
			    } 
			    try {
					files[i].transferTo(targetFile);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sb.append(fileName);
				sb.append(",");
			}
			path = sb.toString().substring(0,sb.toString().lastIndexOf(","));
			webCase.setPicture(path);
		}
		try {
			
			 flag = dao.update("updateSite", webCase);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		this.findAllWebCase(model);
		if(flag>0){
			return "site/chooseSite";
		}else{
			model.addAttribute("msg", "修改网站案例失败");
			model.addAttribute("linked","site/chooseSite.jsp");
			return "commons/returnPage";
		}			 
	}
	
	@RequestMapping("/delSite.do")
	public String delSite(Model model,HttpServletRequest request,WebCase webCase){
		int flag = 0;
		try {
			flag = dao.update("delSite", webCase);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag>0){
			
			this.findAllWebCase(model);
			return "site/chooseSite";
			
		}else{
			model.addAttribute("msg", "删除网站案例失败");
			model.addAttribute("linked","site/chooseSite.jsp");
			return "commons/returnPage";
		}
	}
	@RequestMapping("/toAddPic.do")
	public String toAddPic(WebCase webCase,Model model){
		WebCase webPic = null;
		String[] picArra = null;
		try {
			webPic = (WebCase)dao.findOne("findAllWebCase", webCase);
			picArra = webPic.getPicture().split(",");
			webPic.setPictureList(Arrays.asList(picArra));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("webPic", webPic);
		return "site/addSite";
	}
	@RequestMapping("/addSitePicture.do")
	public String addSitePicture(WebCase webCase,@RequestParam(value="file")CommonsMultipartFile[] files,
			Model model,HttpServletRequest request){
		int flag = 0;
		File targetFile =null;
//		if(files.length>1){// 注释掉目的：防止添加一张图片直接跳过，可支持一张图片的添加
			String path = "";
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<files.length;i++){
				String fileName = request.getContextPath()+"/upload/web/" +files[i].getOriginalFilename();
				String realPath = request.getSession().getServletContext().getRealPath("upload")+"/web";
				targetFile = new File(realPath, files[i].getOriginalFilename());  
				if(targetFile.exists()){  
			        targetFile.delete();  
			    }  
			    if(!targetFile.exists()){  
			        targetFile.mkdirs();  
			    } 
			    try {
					files[i].transferTo(targetFile);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sb.append(fileName);
				sb.append(",");
//			}
			path = sb.toString().substring(0,sb.toString().lastIndexOf(","));
			String pictureList = webCase.getPictureList().toString();
			String headPath = webCase.getPictureList().toString().substring(2,pictureList.length()-2);
			String picture =headPath +","+ path;
			webCase.setPicture(picture);
			try {
				flag = dao.update("addSitePicture", webCase);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(flag>0){
			
			this.findAllWebCase(model);
			return "site/chooseSite";
			
		}else{
			model.addAttribute("msg", "图片增加失败");
			model.addAttribute("linked","site/chooseSite.jsp");
			return "commons/returnPage";
		}
	}
	@RequestMapping("/toDelPic.do")
	public String toDelPic(WebCase webCase,Model model){
		WebCase webPic = null;
		String[] picArra = null;
		try {
			webPic = (WebCase)dao.findOne("findAllWebCase", webCase);
			picArra = webPic.getPicture().split(",");
			webPic.setPictureList(Arrays.asList(picArra));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("webPic", webPic);
		return "site/delPic";
	}

	@RequestMapping("delSitePicture.do")
	public String delSitePicture(HttpServletRequest request,HttpServletResponse response,WebCase webCase,Model model){
		WebCase webPic = null;
		String[] picArra = null;
		List<String> strList = null;
		List<String> delNumerList = null;
		int flag = 0;
		response.setCharacterEncoding("UTF-8");
		String[] delNumer = request.getParameterValues("subBox");
		try {
			webPic = (WebCase)dao.findOne("findAllWebCase", webCase);
			picArra = webPic.getPicture().split(",");
			strList = new ArrayList(Arrays.asList(picArra));
			delNumerList = Arrays.asList(delNumer);
			for(int i=0;i<strList.size();i++){
				for(int j =0;j< delNumerList.size();j++){
					int num = Integer.parseInt(delNumerList.get(j));
					if(i == num){
						strList.set(i, "");
					}
				}
			
			}
			for(int j =0;j< delNumerList.size();j++){
				strList.remove("");
			}

			String picture  = strList.toString().substring(1,strList.toString().length()-1);
			webPic.setPicture(picture);
			
			flag = dao.update("delSitePicture", webPic);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		if(flag>0){
			this.findAllWebCase(model);
			model.addAttribute("msg", "图片删除成功");
			model.addAttribute("linked","site/chooseSite.do");
			
		}else{
			model.addAttribute("msg", "图片删除失败");
			model.addAttribute("linked","site/chooseSite.jsp");
			
		}
		return "commons/returnPage";
	}
}
