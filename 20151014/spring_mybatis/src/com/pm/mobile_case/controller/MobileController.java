package com.pm.mobile_case.controller;

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
import org.apache.poi.hssf.util.HSSFColor.TURQUOISE;
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
import com.pm.site_design.entity.WebCase;
import com.pm.template_case.entity.Template;

@Controller
@RequestMapping("/mobile")
public class MobileController {
	@Resource
	private CommDao commDao;
	List<Mobile> mobileList = null;
	List<App> appList = null;
	String pictureStr = null;
	StringBuffer sb = null;
	boolean flag = false;
	public void findOneMobile(Mobile mobile,Model model) {
		try {
			mobile = (Mobile) commDao.findOne("findOneMobile", mobile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("updateMobile", mobile);
	}
	/*
	 * 手机端
	 */
	public void findAllMobile(Model model) {
		try {
			mobileList = commDao.findList("findAllMobile", null);
			for(int i = 0;i<mobileList.size();i++){
				pictureStr = mobileList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					mobileList.get(i).setPicture(mobileList.get(i).getPicture());
				}else{
					mobileList.get(i).setPicture(pictureStr.substring(0,mobileList.get(i).getPicture().indexOf(",")));
				}	
				mobileList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
			JSONObject json = new JSONObject();
			json.put("list", mobileList);
			model.addAttribute("json", json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addMobileLook(Mobile mobile) {
		mobile.setLook(mobile.getLook()+1);
		try {
			commDao.update("addMobileLook", mobile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("添加手机站浏览与点赞失败");
		}
	}
	
	@RequestMapping("/checkMobileIntf.do")
	public void checkMobileIntf(HttpServletRequest request,HttpServletResponse response){
		Mobile mobile = null;
		Mobile mobile1 = new Mobile();
		String mobileIntf = request.getParameter("mobileIntf");
        //新建一个printWriter对象  
        PrintWriter pw = null;  
        //通过response 获取pw  
        try {
			pw=response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        if(mobileIntf!=null&&!"".equals(mobileIntf)){
        	mobile1.setMobileIntf(mobileIntf);
        	 try {
        		 mobile  = (Mobile)commDao.findOne("findOneMobile", mobile1);
     			 response.setCharacterEncoding("utf-8");  	 
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        }
        flag = new CheckChineseName().checkName(mobileIntf);
        if(!flag){
        	pw.print("{\"result\":\"3\"}");//不合法编号
        }else if(mobileIntf==null||"".equals(mobileIntf)){
        	pw.print("{\"result\":\"9\"}");  //不能为空 
        }else if(mobileIntf.length()<3||mobileIntf.length()>10){
        	pw.print("{\"result\":\"11\"}");
        }else if(mobile==null){  
            //用pw对象传递json  
            pw.print("{\"result\":\"0\"}");//该用户名可以使用  
        }else if(mobile!=null){
        	pw.print("{\"result\":\"8\"}");      	
        }else{  
            pw.print("{\"result\":\"1\"}");  
        }  
        pw.flush();  
        pw.close();    
    }
	
	@RequestMapping("/checkAppIntf.do")
	public void checkAppIntf(HttpServletRequest request,HttpServletResponse response){
		App app = null;
		App app1 = new App();
		String appIntf = request.getParameter("appIntf");
        //新建一个printWriter对象  
        PrintWriter pw = null;  
        //通过response 获取pw  
        try {
			pw=response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        if(appIntf!=null&&!"".equals(appIntf)){
        	app1.setAppIntf(appIntf);
        	 try {
        		 app  = (App)commDao.findOne("findOneApp", app1);
     			response.setCharacterEncoding("utf-8");  	 
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        } 
        flag = new CheckChineseName().checkName(appIntf);
        if(!flag){
        	pw.print("{\"result\":\"3\"}");//该用户名可以使用  
        }else if(appIntf==null||"".equals(appIntf)){
        	pw.print("{\"result\":\"9\"}");  
        }else if(appIntf.length()<3||appIntf.length()>10){
        	pw.print("{\"result\":\"11\"}"); 
        }else if(app==null){  
            //用pw对象传递json  
            pw.print("{\"result\":\"0\"}");//该用户名可以使用  
        }else if(app!=null){
        	pw.print("{\"result\":\"8\"}");//已经占用         	
        }else{  
            pw.print("{\"result\":\"1\"}");  
        }  
        pw.flush();  
        pw.close();    
    }
	
	@RequestMapping("/findAllMobile.do")
	public String findAllMobile(Model model,Mobile mobile){
		/**
		 * 实现Mybatis数据库分页操作
		 */
		int offset = 0;
		int limit = 8;
		RowBounds rb =new RowBounds(offset, limit);
		try {
			mobileList = commDao.findList("findAllMobile", mobile);
			for(int i = 0;i<mobileList.size();i++){
				pictureStr = mobileList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					mobileList.get(i).setPicture(mobileList.get(i).getPicture());
				}else{
					mobileList.get(i).setPicture(pictureStr.substring(0,mobileList.get(i).getPicture().indexOf(",")));
				}	
				mobileList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
			JSONObject json = new JSONObject();
			json.put("list", mobileList);
			model.addAttribute("json", json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询移动案例失败");
		}
		return "mobile/mobile";
	}
	@RequestMapping("findMoreMobile.do")
	public String findMoreMoblie(Model model,Mobile mobile){
		try {
			mobileList = commDao.findList("findAllMobile", mobile);
			for(int i = 0;i<mobileList.size();i++){
				pictureStr = mobileList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					mobileList.get(i).setPicture(mobileList.get(i).getPicture());
				}else{
					mobileList.get(i).setPicture(pictureStr.substring(0,mobileList.get(i).getPicture().indexOf(",")));
				}	
				mobileList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询移动案例失败");
		}
		model.addAttribute("mobileList", mobileList);
		return "mobile/mobile";
	}
	
	@RequestMapping("/findOneMobileByIntf.do")
	public String findOneMobileByIntf(Model model,Mobile mobile,HttpServletRequest request){
		String pictureStr  = null;
		Mobile mobileDetail = null;
		Mobile mobileDetail2 = null;
		String mobileIntf = (String) request.getParameter("mobileIntf");
		mobile.setMobileIntf(mobileIntf);
		try {
			mobileDetail = (Mobile)commDao.findOne("findAllMobile", mobile);
			this.addMobileLook(mobileDetail);
			mobileDetail2 = (Mobile)commDao.findOne("findAllMobile", mobile);
			pictureStr = mobileDetail2.getPicture();
			if(pictureStr.indexOf(",")<0){
				mobileDetail2.setPicture(mobileDetail2.getPicture());
			}else{
				mobileDetail2.setPicture(pictureStr.substring(0,mobileDetail2.getPicture().indexOf(",")));
			}	
			mobileDetail2.setPictureList(Arrays.asList(pictureStr.split(",")));
			model.addAttribute("mobileDetail", mobileDetail2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "mobile/detailMobile";
	}
	public void findOneMobileByIntf(Model model,Mobile mobile){
		String pictureStr  = null;
		Mobile mobileDetail = null;
		Mobile mobileDetail2 = null;
		try {
			mobileDetail = (Mobile)commDao.findOne("findAllMobile", mobile);
			mobileDetail2 = (Mobile)commDao.findOne("findAllMobile", mobile);
			pictureStr = mobileDetail2.getPicture();
			if(pictureStr.indexOf(",")<0){
				mobileDetail2.setPicture(mobileDetail2.getPicture());
			}else{
				mobileDetail2.setPicture(pictureStr.substring(0,mobileDetail2.getPicture().indexOf(",")));
			}	
			mobileDetail2.setPictureList(Arrays.asList(pictureStr.split(",")));
			model.addAttribute("mobileDetail", mobileDetail2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addAppLook(App app) {
		app.setLook(app.getLook()+1);
		try {
			commDao.update("addAppLook", app);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("添加App浏览与点赞失败");
		}
	}
	@RequestMapping("/findOneAppByIntf.do")
	public String findOneAppByIntf(Model model,App app,HttpServletRequest request){
		String pictureStr  = null;
		App AppDetail = null;
		App AppDetail2 = null;
		String appIntf = (String) request.getParameter("appIntf");
		app.setAppIntf(appIntf);
		try {
			AppDetail = (App)commDao.findOne("findAllApp", app);
			this.addAppLook(AppDetail);
			AppDetail2 = (App)commDao.findOne("findAllApp", app);
			pictureStr = AppDetail2.getPicture();
			if(pictureStr.indexOf(",")<0){
				AppDetail2.setPicture(AppDetail2.getPicture());
			}else{
				AppDetail2.setPicture(pictureStr.substring(0,AppDetail2.getPicture().indexOf(",")));
			}	
			AppDetail2.setPictureList(Arrays.asList(pictureStr.split(",")));
			model.addAttribute("appDetail", AppDetail2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "mobile/detailApp";
	}
	
	public void findOneAppByIntf(Model model,App app){
		String pictureStr  = null;
		App AppDetail = null;
		App AppDetail2 = null;
		try {
			AppDetail = (App)commDao.findOne("findAllApp", app);
			AppDetail2 = (App)commDao.findOne("findAllApp", app);
			pictureStr = AppDetail2.getPicture();
			if(pictureStr.indexOf(",")<0){
				AppDetail2.setPicture(AppDetail2.getPicture());
			}else{
				AppDetail2.setPicture(pictureStr.substring(0,AppDetail2.getPicture().indexOf(",")));
			}	
			AppDetail2.setPictureList(Arrays.asList(pictureStr.split(",")));
			model.addAttribute("appDetail", AppDetail2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 上传手机站案例
	 *
	 */
	@RequestMapping("/toUploadMobile.do")
	public String toUploadMobile(){
		return "mobile/uploadMobile";
	}
	@RequestMapping("/uploadMobile.do")
	public String uploadMobile(Model model,Mobile mobile,@RequestParam(value="file")CommonsMultipartFile[] files,HttpServletRequest request){
		int flag = 0;
		String path = "";
		sb = new StringBuffer();
		
		mobile.setMobileNumber(new DecimalFormat("00000").format(Math.random()*1000000));
		
		for(int i=0;i<files.length;i++){
			String fileName = request.getContextPath()+"/upload/mobile/" +files[i].getOriginalFilename();
			sb.append(fileName);
			sb.append(",");
		}
		int lastIndex = sb.toString().lastIndexOf(",");
		path = sb.toString().substring(0, lastIndex);
		mobile.setPicture(path);
		String realPath = request.getSession().getServletContext().getRealPath("upload")+"/mobile";
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
				flag = commDao.update("uploadMobile", mobile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		if(flag>0){
			model.addAttribute("msg", "添加移动案例成功！");
			model.addAttribute("linked", "mobile/findAllMobile.do");
			return "commons/returnPage";
		}else{
			model.addAttribute("msg", "添加移动案例失败");
			return "mobile/addMobile";
		}
	}
	/*
	 *	增加查看次数 
	 */
	@RequestMapping("/addMobileLook.do")
	public String addMobileLook(Mobile mobile,Model model){
		mobile.setLook(mobile.getLook()+1);
		try {
			commDao.update("addMobileLook", mobile);
			this.findAllMobile(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "mobile/mobile";	
	}
	/*
	 *	增加查看次数 
	 */
	@RequestMapping("/addMobileThumb.do")
	public void addMobileThumb(Mobile mobile,HttpServletRequest request,HttpServletResponse response){
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
			mobile =(Mobile) commDao.findOne("findMobileByNumber", mobile);
			mobile.setThumb(mobile.getThumb()+1);
			flag = commDao.update("addMobileThumb", mobile);
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
	/*
	 * APP端
	 *
	 */
	public void  findOneApp(App app,Model model) {
		 
		try {
			app = (App) commDao.findOne("findOneApp", app);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("updateApp", app);
	}
	public void findAllApp(Model model) {
		
		try {
			appList = commDao.findList("findAllApp", null);
			for(int i = 0;i<appList.size();i++){
				pictureStr = appList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					appList.get(i).setPicture(appList.get(i).getPicture());
				}else{
					appList.get(i).setPicture(pictureStr.substring(0,appList.get(i).getPicture().indexOf(",")));
				}	
				appList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
			JSONObject json = new JSONObject();
			json.put("list", appList);
			model.addAttribute("json", json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/findAllApp.do")
	public String findAllApp(Model model,App app){
		/**
		 * 实现Mybatis数据库分页操作
		 */
		int offset = 0;
		int limit = 8;
		RowBounds rb =new RowBounds(offset, limit);
		try {
			/*mobileList = commDao.findList("findAllMobile", mobile,rb);*/
			appList = commDao.findList("findAllApp", app);
			for(int i = 0;i<appList.size();i++){
				pictureStr = appList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					appList.get(i).setPicture(appList.get(i).getPicture());
				}else{
					appList.get(i).setPicture(pictureStr.substring(0,appList.get(i).getPicture().indexOf(",")));
				}	
				appList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
			JSONObject json = new JSONObject();
			json.put("list", appList);
			model.addAttribute("json", json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询移动终端失败");
		}
		return "mobile/app";
	}
	
	@RequestMapping("findMoreApp.do")
	public String findMoreApp(Model model,App app){
		try {
			appList = commDao.findList("findAllApp", app);
			for(int i = 0;i<appList.size();i++){
				pictureStr = appList.get(i).getPicture();
				if(pictureStr.indexOf(",")<0){
					appList.get(i).setPicture(appList.get(i).getPicture());
				}else{
					appList.get(i).setPicture(pictureStr.substring(0,appList.get(i).getPicture().indexOf(",")));
				}	
				appList.get(i).setPictureList(Arrays.asList(pictureStr.split(",")));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询移动终端失败");
		}
		model.addAttribute("appList", appList);
		return "mobile/app";
	}
	
	/*
	 * 上传App案例
	 *
	 */
	
	@RequestMapping("/toUploadApp.do")
	public String toUploadApp(){
		return "mobile/uploadApp";
	}
	@RequestMapping("/uploadApp.do")
	public String uploadApp(Model model,App app,@RequestParam(value="file")CommonsMultipartFile[] files,HttpServletRequest request){
		int flag = 0;
		String path ="";
		sb = new StringBuffer();
		
		app.setAppNumber(new DecimalFormat("00000").format(Math.random()*1000000));
		
		for(int i=0;i<files.length;i++){
			String fileName = request.getContextPath()+"/upload/app/" +files[i].getOriginalFilename();
			sb.append(fileName);
			sb.append(",");
		}
		int lastIndex = sb.toString().lastIndexOf(",");
		path = sb.toString().substring(0, lastIndex);
		app.setPicture(path);
		String realPath = request.getSession().getServletContext().getRealPath("upload")+"/app";
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
			flag = commDao.update("uploadApp", app);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag>0){
			model.addAttribute("msg", "添加App成功！");
			model.addAttribute("linked", "mobile/findAllApp.do");
			return "commons/returnPage";
		}else{
			model.addAttribute("msg", "添加App失败");
			return "mobile/uploadApp";
		}
	}
	/*
	 *	增加App查看次数 
	 */
	@RequestMapping("/addAppLook.do")
	public String addAppLook(App app,Model model){
		app.setLook(app.getLook()+1);
		try {
			commDao.update("addAppLook", app);
			this.findAllApp(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "mobile/app";	
	}
	/*
	 *	增加App查看次数 
	 */
	@RequestMapping("/addAppThumb.do")
	public void addAppThumb(App app,HttpServletRequest request,HttpServletResponse response){
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
			app =(App) commDao.findOne("findAppByNumber", app);
			app.setThumb(app.getThumb()+1);
			flag = commDao.update("addAppThumb", app);
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
	/**
	 * 
	 * @param 更改手机站信息
	 * @return
	 */
	@RequestMapping("/chooseMobile.do")
	public String chooseMobile(Model model){
		this.findAllMobile(model);
		return "mobile/chooseMobile";
		
	}
	@RequestMapping("/toUpdateMobile.do")
	public String toUpdateMobile(Mobile mobile,Model model){
		this.findOneMobile(mobile, model);
		return "mobile/updateMobile";
		
	}
	@RequestMapping("/updateMobile.do")
	public String updateMobile(Mobile mobile,@RequestParam(value="file")CommonsMultipartFile[] files,Model model,HttpServletRequest request){
		Mobile updateMobile = null;
		int flag = 0;
		File targetFile =null;
		sb = new StringBuffer();
		if(files.length>1){
			String path = "";
			for(int i=0;i<files.length;i++){
				String fileName = request.getContextPath()+"/upload/mobile/" +files[i].getOriginalFilename();
				String realPath = request.getSession().getServletContext().getRealPath("upload")+"/mobile";
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
			mobile.setPicture(path);
		}
		try {
			flag = commDao.update("updateMobile", mobile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.findAllMobile(model);
		if(flag>0){
			return "mobile/chooseMobile";
		}else{
			model.addAttribute("msg", "修改手机站失败");
			model.addAttribute("linked","mobile/chooseMobile.jsp");
			return "commons/returnPage";
		}
	}
	@RequestMapping("/chooseApp.do")
	public String chooseApp(Model model){
		this.findAllApp(model);
		return "mobile/chooseApp";
		
	}
	@RequestMapping("/toUpdateApp.do")
	public String toUpdateApp(Model model,App app){
		this.findOneApp(app, model);
		return "mobile/updateApp";	
	}
	@RequestMapping("/updateApp.do")
	public String toUpdateApp(Model model,App app,@RequestParam(value="file")CommonsMultipartFile[] files,HttpServletRequest request){
		App updateApp = null;
		int flag = 0;
		File targetFile =null;
		sb = new StringBuffer();
		if(files.length>1){
			String path = "";
			for(int i=0;i<files.length;i++){
				String fileName = request.getContextPath()+"/upload/app/" +files[i].getOriginalFilename();
				String realPath = request.getSession().getServletContext().getRealPath("upload")+"/app";
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
			app.setPicture(path);
		}
		try {
			flag = commDao.update("updateApp", app);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.findAllApp(model);
		if(flag>0){
			return "mobile/chooseApp";
		}else{
			model.addAttribute("msg", "修改App失败");
			model.addAttribute("linked","mobile/chooseApp.jsp");
			return "commons/returnPage";
		}
		
	}
	@RequestMapping("/delMobile.do")
	public String delMobile(Mobile mobile,Model model){
		int flag = 0;
		try {
			flag = commDao.update("delMobile", mobile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.findAllMobile(model);
		if(flag>0){
			return "mobile/chooseMobile";
		}else{
			model.addAttribute("msg", "修改App失败");
			model.addAttribute("linked","mobile/chooseMobile.jsp");
			return "commons/returnPage";
		}
	}
	@RequestMapping("delApp.do")
	public String delApp(App app,Model model){
		int flag = 0;
		try {
			flag = commDao.update("delApp", app);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.findAllApp(model);
		if(flag>0){
			return "mobile/chooseApp";
		}else{
			model.addAttribute("msg", "修改App失败");
			model.addAttribute("linked","mobile/chooseApp.jsp");
			return "commons/returnPage";
		}
	}
	@RequestMapping("/toAddMobile.do")
	public String toAddMobile(Model model,Mobile mobile){
		Mobile mobilePic = null;
		String[] picArra = null;
		try {
			mobilePic = (Mobile)commDao.findOne("findOneMobile", mobile);
			picArra = mobilePic.getPicture().split(",");
			mobilePic.setPictureList(Arrays.asList(picArra));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("mobilePic", mobilePic);
		return "mobile/addMobile";	
	}
	
	@RequestMapping("/addMobilePicture.do")
	public String addMobilePicture(Mobile mobile,@RequestParam(value="file")CommonsMultipartFile[] files,
			Model model,HttpServletRequest request){
		int flag = 0;
		File targetFile =null;
//		if(files.length>1){// 注释掉目的：防止添加一张图片直接跳过，可支持一张图片的添加
			String path = "";
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<files.length;i++){
				String fileName = request.getContextPath()+"/upload/mobile/" +files[i].getOriginalFilename();
				String realPath = request.getSession().getServletContext().getRealPath("upload")+"/mobile";
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
			String pictureList = mobile.getPictureList().toString();
			String headPath = mobile.getPictureList().toString().substring(2,pictureList.length()-2);
			String picture =headPath +","+ path;
			mobile.setPicture(picture);
			try {
				flag = commDao.update("addMobilePicture", mobile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(flag>0){
			
			this.findAllMobile(model);
			return "mobile/chooseMobile";
			
		}else{
			model.addAttribute("msg", "图片增加失败");
			model.addAttribute("linked","mobile/chooseMobile.jsp");
			return "commons/returnPage";
		}
	}
	
	@RequestMapping("/toAddApp.do")
	public String toAddApp(Model model,App app){
		App appPic = null;
		String[] picArra = null;
		try {
			appPic = (App)commDao.findOne("findOneApp", app);
			picArra = appPic.getPicture().split(",");
			appPic.setPictureList(Arrays.asList(picArra));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("appPic", appPic);
		return "mobile/addApp";	
	}
	
	@RequestMapping("/addAppPicture.do")
	public String addAppPicture(App app,@RequestParam(value="file")CommonsMultipartFile[] files,
			Model model,HttpServletRequest request){
		int flag = 0;
		File targetFile =null;
//		if(files.length>1){// 注释掉目的：防止添加一张图片直接跳过，可支持一张图片的添加
			String path = "";
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<files.length;i++){
				String fileName = request.getContextPath()+"/upload/app/" +files[i].getOriginalFilename();
				String realPath = request.getSession().getServletContext().getRealPath("upload")+"/app";
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
			String pictureList = app.getPictureList().toString();
			String headPath = app.getPictureList().toString().substring(2,pictureList.length()-2);
			String picture =headPath +","+ path;
			app.setPicture(picture);
			try {
				flag = commDao.update("addAppPicture", app);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(flag>0){
			
			this.findAllApp(model);
			return "mobile/chooseApp";
			
		}else{
			model.addAttribute("msg", "图片增加失败");
			model.addAttribute("linked","mobile/chooseApp.jsp");
			return "commons/returnPage";
		}
	}
	@RequestMapping("/toDelMobilePic.do")
	public String toDelPic(Mobile mobile,Model model){
		Mobile mobilePic = null;
		String[] picArra = null;
		try {
			mobilePic = (Mobile)commDao.findOne("findOneMobile", mobile);
			picArra = mobilePic.getPicture().split(",");
			mobilePic.setPictureList(Arrays.asList(picArra));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("mobilePic", mobilePic);
		return "mobile/delMobilePic";
	}
	@RequestMapping("delMobilePicture.do")
	public String delSitePicture(HttpServletRequest request,HttpServletResponse response,Mobile mobile,Model model){
		Mobile mobilePic = null;
		String[] picArra = null;
		List<String> strList = null;
		List<String> delNumerList = null;
		int flag = 0;
		response.setCharacterEncoding("UTF-8");
		String[] delNumer = request.getParameterValues("subBox");
		try {
			mobilePic = (Mobile)commDao.findOne("findOneMobile", mobile);
			picArra = mobilePic.getPicture().split(",");
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
			mobilePic.setPicture(picture);
			
			flag = commDao.update("delMobilePicture", mobilePic);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		if(flag>0){
			this.findAllMobile(model);
			model.addAttribute("msg", "图片删除成功");
			model.addAttribute("linked","mobile/chooseMobile.do");
			
		}else{
			model.addAttribute("msg", "图片删除失败");
			model.addAttribute("linked","mobile/chooseMobile.jsp");
			
		}
		return "commons/returnPage";
	}
	
	@RequestMapping("/toDelAppPic.do")
	public String toDelAppPic(App app,Model model){
		App appPic = null;
		String[] picArra = null;
		try {
			appPic = (App)commDao.findOne("findOneApp", app);
			picArra = appPic.getPicture().split(",");
			appPic.setPictureList(Arrays.asList(picArra));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("appPic", appPic);
		return "mobile/delAppPic";
	}
	@RequestMapping("delAppPicture.do")
	public String delAppPicture(HttpServletRequest request,HttpServletResponse response,App app,Model model){
		App appPic = null;
		String[] picArra = null;
		List<String> strList = null;
		List<String> delNumerList = null;
		int flag = 0;
		response.setCharacterEncoding("UTF-8");
		String[] delNumer = request.getParameterValues("subBox");
		try {
			appPic = (App)commDao.findOne("findAllApp", app);
			picArra = appPic.getPicture().split(",");
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
			appPic.setPicture(picture);
			
			flag = commDao.update("delAppPicture", appPic);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		if(flag>0){
			this.findAllMobile(model);
			model.addAttribute("msg", "图片删除成功");
			model.addAttribute("linked","mobile/chooseApp.do");
			
		}else{
			model.addAttribute("msg", "图片删除失败");
			model.addAttribute("linked","mobile/chooseApp.jsp");
			
		}
		return "commons/returnPage";
	}
	public static void main(String[] args) {
		String str  = "'white'";
		System.out.println(str);
	}
}
