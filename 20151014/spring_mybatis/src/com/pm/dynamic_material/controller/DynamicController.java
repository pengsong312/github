package com.pm.dynamic_material.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pm.dynamic_material.entity.Dynamic;
import com.pm.help.dao.CommDao;
import com.pm.help.utils.CheckChineseName;
import com.pm.mobile_case.entity.App;
import com.pm.template_case.entity.Template;

@Controller
@RequestMapping("/dynamic")
public class DynamicController {
	@Resource
	private CommDao commDao;
	List<Dynamic> dynamicList = null;
	/*
	 * 单纯查询所有的动效案例列表用户展示 
	 */
	public  void  findOneDynamic(Model model,Dynamic dynamic) {
		try {
			dynamic = (Dynamic) commDao.findOne("findOneDynamic", dynamic);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("updateDynamic", dynamic);
	}
	public void findAllDynamic(Model model) {
		
		try {
			dynamicList = commDao.findList("findAllDynamic", null);
			JSONObject json  = new JSONObject();
			json.put("list", dynamicList);			
			model.addAttribute("json", json);
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/checkDynamicIntf.do")
	public void checkDynamicIntf(HttpServletRequest request,HttpServletResponse response){
		Dynamic dynamic = null;
		boolean flag  = false;
		Dynamic dynamic1 = new Dynamic();
		String dynamicIntf = request.getParameter("dynamicIntf");
        //新建一个printWriter对象  
        PrintWriter pw = null;  
        //通过response 获取pw  
        try {
			pw=response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        if(dynamicIntf!=null&&!"".equals(dynamicIntf)){
        	dynamic1.setDynamicIntf(dynamicIntf);
        	 try {
        		dynamic  = (Dynamic)commDao.findOne("findDynamicIntf", dynamic1);
     			response.setCharacterEncoding("utf-8");  	 
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        }
       flag = new CheckChineseName().checkName(dynamicIntf);
       if(!flag){
    	   pw.print("{\"result\":\"3\"}");
       }else if(dynamicIntf==null||"".equals(dynamicIntf)){     	
         	 pw.print("{\"result\":\"9\"}");  
       }else if(dynamicIntf.length()<3||dynamicIntf.length()>10){
    	     pw.print("{\"result\":\"11\"}");
       }else if(dynamic==null){  
            //用pw对象传递json  
            pw.print("{\"result\":\"0\"}");//该用户名可以使用  
       }else if(dynamic!=null){
    	   pw.print("{\"result\":\"8\"}");
       }else{  
            pw.print("{\"result\":\"1\"}");  
        }  
        pw.flush();  
        pw.close();    
    }
	
	@RequestMapping("/findAllDynamic.do")
	public String findAllDynamic(Model model,Dynamic dynamic,HttpServletRequest request){
		int offset = 0;
		int limit = 8;
		RowBounds rb =new RowBounds(offset, limit);
		try {
			if(dynamic.getMaterialType()!=null&&!"".equals(dynamic.getMaterialType())){
				
				dynamic.setMaterialType(new String(dynamic.getMaterialType().getBytes("ISO8859-1"),"UTF-8"));
			}
			dynamicList = commDao.findList("findAllDynamic", dynamic);
			JSONObject json  = new JSONObject();
			json.put("list", dynamicList);
			model.addAttribute("json", json);
			request.getSession().setAttribute("dynamicList", dynamicList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return "dynamic/dynamic";
	}
	
	@RequestMapping("/findAllUpdateDynamic.do")
	public String findAllUpdateDynamic(Model model,Dynamic dynamic,HttpServletRequest request){
		int offset = 0;
		int limit = 8;
		RowBounds rb =new RowBounds(offset, limit);
		try {
			if(dynamic.getMaterialType()!=null&&!"".equals(dynamic.getMaterialType())){
					
				dynamic.setMaterialType(new String(dynamic.getMaterialType().getBytes("ISO8859-1"),"UTF-8"));
			}
			dynamicList = commDao.findList("findAllDynamic", dynamic);
			JSONObject json  = new JSONObject();
			json.put("list", dynamicList);			
			model.addAttribute("json", json);
			request.getSession().setAttribute("dynamicList", dynamicList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return "dynamic/chooseDynamic";
	}	
	@RequestMapping("findMoreDynamic.do")
	public String findMoreDynamic(Model model,Dynamic dynamic){
		try {
			dynamicList = commDao.findList("findAllDynamic", dynamic);
			model.addAttribute("dynamicList", dynamicList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return "dynamic/dynamic";
	}
	@RequestMapping("/toUploadDynamic.do")
	public String toAddDynamic(){
		return "dynamic/uploadDynamic";
	}
	@RequestMapping("/uploadDynamic.do")
	public String uploadDynamic(Model model,Dynamic dynamic,
			@RequestParam(value="file",required=false)MultipartFile file,HttpServletRequest request){
		int flag = 0;
		
		dynamic.setDynamicNumber(new DecimalFormat("00000").format(Math.random()*1000000));
		
		String path =request.getContextPath()+"/upload/dynamic/" + file.getOriginalFilename();
		dynamic.setPicture(path);
		String realPath = request.getSession().getServletContext().getRealPath("upload")+"/dynamic";
		File targetFile = new File(realPath, file.getOriginalFilename());  
		if(targetFile.exists()){  
            targetFile.delete();  
        }  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }
        try {
			file.transferTo(targetFile);
			flag = commDao.update("uploadDynamic", dynamic);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(flag>0){
			model.addAttribute("msg", "添加动效案例成功！");
			model.addAttribute("linked", "dynamic/findAllDynamic.do");
			return "commons/returnPage";
		}else{
			model.addAttribute("msg", "添加动效案例失败");
			return "mobile/uploadDynamic";
		}
	}
	
	
	/*
	 *  1.增加动效的访问量 
	 *  2.增加动效的点赞量
	 */
	@RequestMapping("/addDynamicLook.do")
	public String addDynamicLook(Dynamic dynamic,HttpServletRequest request,HttpServletResponse response,Model model){
		int flag = 0;
/*		PrintWriter pw = null;  
	    //通过response 获取pw  
	        
		try {
			pw=response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			} */
		try {
			dynamic =(Dynamic) commDao.findOne("findDynamicByNumber", dynamic);
			dynamic.setLook(dynamic.getLook()+1);
			flag = commDao.update("addDynamicLook", dynamic);
			/*response.setCharacterEncoding("utf-8");*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("添加网站点赞失败");
		}
		if(flag>0){
			this.findAllDynamic(model);
       	 /*	pw.print("{\"result\":\"0\"}");*/
       }/*else{  
           pw.print("{\"result\":\"1\"}");  
       }  
       pw.flush();  
       pw.close();  */  
       return "dynamic/dynamic";
	}
	@RequestMapping("/addDynamicThumb.do")
	public String addDynamicThumb(Dynamic dynamic,Model model){
		dynamic.setThumb(dynamic.getThumb()+1);
		try {
			commDao.update("addDynamicThumb", dynamic);
			this.findAllDynamic(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "dynamic/dynamic";
		
	}
	@RequestMapping("/chooseDynamic.do")
	public String chooseDynamic(Model model){
		this.findAllDynamic(model);
		return "dynamic/chooseDynamic";
		
	}
	@RequestMapping("/toUpdateDynamic.do")
	public String toUpdateDynamic(Model model,Dynamic dynamic){
		this.findOneDynamic(model, dynamic);
		return "dynamic/updateDynamic";
		
	}
	@RequestMapping("/updateDynamic.do")
	public String updateDynamic(Model model,Dynamic dynamic,@RequestParam(value="file",required=false)MultipartFile file,HttpServletRequest request){
		int flag = 0;
		String fileName = file.getOriginalFilename();
		if(!fileName.isEmpty()){
			String path =request.getContextPath()+"/upload/dynamic/" + fileName;
			dynamic.setPicture(path);
			String realPath = request.getSession().getServletContext().getRealPath("upload")+"/dynamic";
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
			flag = commDao.update("updateDynamic", dynamic);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.findAllDynamic(model);
		if(flag>0){
			return "dynamic/chooseDynamic";
		}else{
			model.addAttribute("msg", "修改App失败");
			model.addAttribute("linked","mobile/chooseDynamic.jsp");
			return "commons/returnPage";
		}	
	}
	@RequestMapping("/delDynamic.do")
	public String delDynamic(Dynamic dynamic,Model model){
		int flag = 0;
		try {
			flag = commDao.update("delDynamic", dynamic);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.findAllDynamic(model);
		if(flag>0){
			return "dynamic/chooseDynamic";
		}else{
			model.addAttribute("msg", "删除失败");
			model.addAttribute("linked","mobile/chooseDynamic.jsp");
			return "commons/returnPage";
		}
	}
}
