package com.pm.template_case.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pm.help.dao.CommDao;
import com.pm.help.utils.CheckChineseName;
import com.pm.mobile_case.entity.App;
import com.pm.mobile_case.entity.Mobile;
import com.pm.picture_material.entity.Picture;
import com.pm.site_design.entity.WebCase;
import com.pm.template_case.entity.Template;
@Controller
@RequestMapping("/template")
public class TemplateController {
	@Resource
	private CommDao commDao;
	List<Template> templateList = null;
	StringBuffer sb = null;
	String pictureStr = null;
	
	public void findOneTemplate(Template template,Model model) {
		try {
			template = (Template) commDao.findOne("findOneTemplate", template);
			pictureStr = template.getPicture();
			if(pictureStr.indexOf(",")<0){
				template.setPicture(template.getPicture());
			}else{
				template.setPicture(pictureStr.substring(0,template.getPicture().indexOf(",")));
			}	
			template.setPictureList(Arrays.asList(pictureStr.split(",")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("updateTemplate", template);
	}
	public void findAllTemplate(Model model) {
		
		try {
			templateList = commDao.findList("findAllTemplate",null);
			for(int i = 0;i<templateList.size();i++){
				pictureStr = templateList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					templateList.get(i).setPicture(templateList.get(i).getPicture());
				}else{
					templateList.get(i).setPicture(pictureStr.substring(0,templateList.get(i).getPicture().indexOf(",")));
				}	
				templateList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
			JSONObject json = new JSONObject();
			json.put("list", templateList);
			model.addAttribute("json", json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param model
	 * @param template
	 * @param request
	 * @return 添加浏览与点赞量
	 */
	public void addTemplateLook(Template template) {
		template.setLook(template.getLook()+1);
		try {
			commDao.update("addTemplateLook", template);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("添加Template浏览与点赞失败");
		}
	}
	
	@RequestMapping("/checkTemplateIntf.do")
	public void checkTemplateIntf(HttpServletRequest request,HttpServletResponse response){
		boolean flag = false;
		Template template = null;
		Template template1 = new Template();
		String templateIntf = request.getParameter("templateIntf");
        //新建一个printWriter对象  
        PrintWriter pw = null;  
        //通过response 获取pw  
        try {
			pw=response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        if(templateIntf!=null&&!"".equals(templateIntf)){
        	template1.setTemplateIntf(templateIntf);
        	 try {
        		 template  = (Template)commDao.findOne("findOneTemplate", template1);
     			 response.setCharacterEncoding("utf-8");  	 
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        }
        flag = new CheckChineseName().checkName(templateIntf);
        if(templateIntf==null||"".equals(templateIntf)){
        	pw.print("{\"result\":\"9\"}");//编号不能为空
        }else if(!flag){
        	pw.print("{\"result\":\"3\"}");//不合法编号     	
        }else if(templateIntf.length()<3||templateIntf.length()>10){
        	pw.print("{\"result\":\"11\"}");//长度不合法
        }else if(template==null){  
            //用pw对象传递json  
            pw.print("{\"result\":\"0\"}");//该用户名可以使用  
        }else if(template!=null){  
            //用pw对象传递json  
            pw.print("{\"result\":\"8\"}");//编号被占用
        }else{  
            pw.print("{\"result\":\"1\"}");  
        }  
        pw.flush();  
        pw.close();    
    }
	
	@RequestMapping("/findAllTemplate.do")
	public String findAllTemplate(Model model,Template template,HttpServletRequest request) {
		/*
		int offset = 0;
		int limit = 8;
		RowBounds rb =new RowBounds(offset, limit);*/
		try {
			if(template.getStyle()!=null&&!"".equals(template.getStyle())){
					
					template.setStyle(new String(template.getStyle().getBytes("ISO8859-1"),"UTF-8"));
			}
			templateList = commDao.findList("findAllTemplate",template);
			for(int i = 0;i<templateList.size();i++){
				pictureStr = templateList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					templateList.get(i).setPicture(templateList.get(i).getPicture());
				}else{
					templateList.get(i).setPicture(pictureStr.substring(0,templateList.get(i).getPicture().indexOf(",")));
				}	
				templateList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
			JSONObject json = new JSONObject();
			json.put("list", templateList);
			model.addAttribute("json", json);
			request.getSession().setAttribute("templateList", templateList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询模板案例失败！");
		}
		return "template/template";
	}
	@RequestMapping("/findUpdateTemplate.do")
	public String findUpdateTemplate(Model model,Template template,HttpServletRequest request) {
		/*
		int offset = 0;
		int limit = 8;
		RowBounds rb =new RowBounds(offset, limit);*/
		try {
			if(template.getStyle()!=null&&!"".equals(template.getStyle())){
					
					template.setStyle(new String(template.getStyle().getBytes("ISO8859-1"),"UTF-8"));
			}
			templateList = commDao.findList("findAllTemplate",template);
			for(int i = 0;i<templateList.size();i++){
				pictureStr = templateList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					templateList.get(i).setPicture(templateList.get(i).getPicture());
				}else{
					templateList.get(i).setPicture(pictureStr.substring(0,templateList.get(i).getPicture().indexOf(",")));
				}	
				templateList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
			JSONObject json = new JSONObject();
			json.put("list", templateList);
			model.addAttribute("json", json);
			request.getSession().setAttribute("templateList", templateList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询模板案例失败！");
		}
		return "template/chooseTemplate";
	}
	@RequestMapping("/findOneTemplateByIntf.do")
	public String findOneTemplateByIntf(Model model,Template template,HttpServletRequest request){

		Template templateDetail = null;
		Template templateDetail2 = null;
		String templateIntf = (String) request.getParameter("templateIntf");
		template.setTemplateIntf(templateIntf);
		try {
			templateDetail = (Template)commDao.findOne("findOneTemplate", template);
			this.addTemplateLook(templateDetail);
			templateDetail2 = (Template)commDao.findOne("findOneTemplate", template);
			pictureStr = templateDetail2.getPicture();
			if(pictureStr.indexOf(",")<0){
				templateDetail2.setPicture(templateDetail2.getPicture());
			}else{
				templateDetail2.setPicture(pictureStr.substring(0,templateDetail2.getPicture().indexOf(",")));
			}	
			templateDetail2.setPictureList(Arrays.asList(pictureStr.split(",")));
			model.addAttribute("templateDetail", templateDetail2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "template/detailTemplate";
	}
	public void findOneTemplateByIntf(Model model,Template template){

		Template templateDetail2 = null;
		try {
			templateDetail2 = (Template)commDao.findOne("findAllTemplate", template);
			pictureStr = templateDetail2.getPicture();
			if(pictureStr.indexOf(",")<0){
				templateDetail2.setPicture(templateDetail2.getPicture());
			}else{
				templateDetail2.setPicture(pictureStr.substring(0,templateDetail2.getPicture().indexOf(",")));
			}	
			templateDetail2.setPictureList(Arrays.asList(pictureStr.split(",")));
			model.addAttribute("templateDetail", templateDetail2);
		} catch (IOException e) {
			// TODO Auto-generated catch block   
			e.printStackTrace();
		}
	}
	@RequestMapping("/findMoreTemplate.do")
	public String findMoreTemplate(Model model,Template template){
		
		try {
			templateList = commDao.findList("findAllTemplate",template);
			for(int i = 0;i<templateList.size();i++){
				pictureStr = templateList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					templateList.get(i).setPicture(templateList.get(i).getPicture());
				}else{
					templateList.get(i).setPicture(pictureStr.substring(0,templateList.get(i).getPicture().indexOf(",")));
				}	
				templateList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
			
			model.addAttribute("templateList", templateList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询模板案例失败！");
		}
		return "template/template";
	}
	
	/*
	 * 添加模板案例上传
	 * 
	 */
	@RequestMapping("/toUploadTemplate.do")
	public String toUploadTemplate(){
		return "template/uploadTemplate";
	}
	@RequestMapping("uploadTemplate.do")
	public String uploadTemplate(Model model,Template template,
			@RequestParam(value="file",required=false)CommonsMultipartFile[] files,HttpServletRequest request){
		int flag=0;
		String path = "";
		sb = new StringBuffer();
		
		template.setTemplateNumber(new DecimalFormat("00000").format(Math.random()*1000000));
	
		for(int i=0;i<files.length;i++){
			String fileName = request.getContextPath()+"/upload/template/" +files[i].getOriginalFilename();
			sb.append(fileName);
			sb.append(",");
		}
		int lastIndex = sb.toString().lastIndexOf(",");
		path = sb.toString().substring(0, lastIndex);
		template.setPicture(path);
		String realPath = request.getSession().getServletContext().getRealPath("upload")+"/template";
		for(int j=0;j<files.length;j++){
			File targetFile = new File(realPath, files[j].getOriginalFilename());  
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
			flag = commDao.update("uploadTemplate", template);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		if(flag>0){
			model.addAttribute("msg", "添加模板案例成功！");
			model.addAttribute("linked", "template/findAllTemplate.do");
			return "commons/returnPage";
		}else{
			model.addAttribute("msg", "添加模板案例失败");
			return "template/uploadTemplate";
		}	
	}
	
	/*
	 * 1.增加访问量 
	 * 2.增加点赞量
	 */
	@RequestMapping("/addTemplateLook.do")
	public String addTemplateLook(Template template,Model model){
		template.setLook(template.getLook()+1);
		try {
			commDao.update("addTemplateLook", template);
			this.findAllTemplate(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "template/template";	
	}
	@RequestMapping("/addTemplateThumb.do")
	public void addTemplateThumb(Template template,HttpServletRequest request,HttpServletResponse response){
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
			template =(Template) commDao.findOne("findTemplateByNumber", template);
			template.setThumb(template.getThumb()+1);
			flag = commDao.update("addTemplateThumb", template);
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
	@RequestMapping("/chooseTemplate.do")
	public String chooseTemplate(Model model){
		this.findAllTemplate(model);
		return "template/chooseTemplate";
		
	}
	@RequestMapping("/toUpdateTemplate.do")
	public String toUpdateTemplate(Model model,Template template){
		this.findOneTemplate(template, model);
		return "template/updateTemplate";
	}
	@RequestMapping("/updateTemplate.do")
	public String updateTemplate(Model model,Template template,@RequestParam(value="file")CommonsMultipartFile[] files,HttpServletRequest request){
		Template updateTemplate = null;
		int flag = 0;
		File targetFile =null;
		sb = new StringBuffer();
		if(files.length>1){
			String path = "";
			for(int i=0;i<files.length;i++){
				String fileName = request.getContextPath()+"/upload/template/" +files[i].getOriginalFilename();
				String realPath = request.getSession().getServletContext().getRealPath("upload")+"/template";
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
			template.setPicture(path);
		}
		 try {
			flag = commDao.update("updateTemplate", template);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.findAllTemplate(model);
		if(flag>0){
			return "template/chooseTemplate";
		}else{
			model.addAttribute("msg", "修改模板失败");
			model.addAttribute("linked","template/chooseTemplate.jsp");
			return "commons/returnPage";
		}			
	}
	@RequestMapping("/delTemplate.do")
	public String delTemplate(Model model,Template template){
		int flag = 0;
		try {
			flag = commDao.update("delTemplate", template);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.findAllTemplate(model);
		if(flag>0){
			return "template/chooseTemplate";
		}else{
			model.addAttribute("msg", "删除失败");
			model.addAttribute("linked","template/chooseTemplate.jsp");
			return "commons/returnPage";
		}		
	}
	
	@RequestMapping("/toAddTemplate.do")
	public String toAddApp(Model model,Template template){
		Template templatePic = null;
		String[] picArra = null;
		try {
			templatePic = (Template)commDao.findOne("findOneTemplate", template);
			picArra = templatePic.getPicture().split(",");
			templatePic.setPictureList(Arrays.asList(picArra));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("templatePic", templatePic);
		return "template/addTemplate";	
	}
	
	@RequestMapping("/addTemplatePicture.do")
	public String addAppPicture(Template template,@RequestParam(value="file")CommonsMultipartFile[] files,
			Model model,HttpServletRequest request){
		int flag = 0;
		File targetFile =null;
//		if(files.length>1){// 注释掉目的：防止添加一张图片直接跳过，可支持一张图片的添加
			String path = "";
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<files.length;i++){
				String fileName = request.getContextPath()+"/upload/template/" +files[i].getOriginalFilename();
				String realPath = request.getSession().getServletContext().getRealPath("upload")+"/template";
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
			String pictureList = template.getPictureList().toString();
			String headPath = template.getPictureList().toString().substring(2,pictureList.length()-2);
			String picture =headPath +","+ path;
			template.setPicture(picture);
			try {
				flag = commDao.update("addTemplatePicture", template);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(flag>0){
			
			this.findAllTemplate(model);
			return "template/chooseTemplate";
			
		}else{
			model.addAttribute("msg", "图片增加失败");
			model.addAttribute("linked","template/chooseTemplate.jsp");
			return "commons/returnPage";
		}
	}
	
	@RequestMapping("/toDelTemplatePic.do")
	public String toDelTemplatePic(Template template,Model model){
		Template templatePic = null;
		String[] picArra = null;
		try {
			templatePic = (Template)commDao.findOne("findOneTemplate", template);
			picArra = templatePic.getPicture().split(",");
			templatePic.setPictureList(Arrays.asList(picArra));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("templatePic", templatePic);
		return "template/delTemplatePic";
	}
	@RequestMapping("/delTemplatePicture.do")
	public String delTemplatePicture(HttpServletRequest request,HttpServletResponse response,Template template,Model model){
		Template templatePic = null;
		String[] picArra = null;
		List<String> strList = null;
		List<String> delNumerList = null;
		int flag = 0;
		response.setCharacterEncoding("UTF-8");
		String[] delNumer = request.getParameterValues("subBox");
		try {
			templatePic = (Template)commDao.findOne("findOneTemplate", template);
			picArra = templatePic.getPicture().split(",");
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
			templatePic.setPicture(picture);
			
			flag = commDao.update("delTemplatePicture", templatePic);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		if(flag>0){
			this.findAllTemplate(model);
			model.addAttribute("msg", "图片删除成功");
			model.addAttribute("linked","template/chooseTemplate.do");
			
		}else{
			model.addAttribute("msg", "图片删除失败");
			model.addAttribute("linked","template/chooseTemplate.jsp");
			
		}
		return "commons/returnPage";
	}
}
