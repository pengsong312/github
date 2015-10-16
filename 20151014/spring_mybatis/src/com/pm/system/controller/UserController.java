package com.pm.system.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pm.dynamic_material.entity.Dynamic;
import com.pm.help.dao.CommDao;
import com.pm.help.utils.CheckChineseName;
import com.pm.site_design.entity.WebCase;
import com.pm.system.entity.Permission;
import com.pm.system.entity.Role;
import com.pm.system.entity.User;
import com.pm.template_case.entity.Template;

@Controller
@RequestMapping("/user")
public class UserController {
	/*
	 * @Resource private UserDao userDao;
	 */
	@Resource
	private CommDao dao;
	public void  findUser(User user,Model model) {
		Role role = new Role();
			try {
				user = (User) dao.findOne("findOneUser", user);

				role.setRoleId(user.getRoleIds());
				role = (Role) dao.findOne("findUserRole", role);
				user.setRoles(role);
				model.addAttribute("updateUser", user);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void findAllSite(Model model) {
		List<WebCase> webCaseList = null;
		JSONObject json = null; 
		try {
			webCaseList = dao.findList("findAllWebCase", null);
			json = new JSONObject();
			json.put("list", webCaseList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("json", json);
	}
	public void findAllUser(Model model) {
		User user = new User();
		List<User> userList = new ArrayList<User>();
		String roleId = null;
		Role role = new Role();
		try {
			userList = dao.findList("findAllUser", null);
			if(!userList.isEmpty()){
				for(int i =0;i<userList.size();i++){
					roleId = userList.get(i).getRoleIds();
					role.setRoleId(roleId);
					role = (Role) dao.findOne("findUserRole", role);
					userList.get(i).setRoles(role);
				}
				
			}
			model.addAttribute("allUser", userList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void findAllDesigner(HttpServletRequest request,Model model) {
		List<User> userList1 = new ArrayList<User>();
		List<User> userList = new ArrayList<User>();
		User user = new User();
		String roleId = null;
		Role role = new Role();
		try {
			userList1 = dao.findList("findAllUser", null);
			if(!userList1.isEmpty()){
				for(int i =0;i<userList1.size();i++){
					roleId = userList1.get(i).getRoleIds();
					role.setRoleId(roleId);
					role = (Role) dao.findOne("findUserRole", role);
					userList1.get(i).setRoles(role);
					if(userList1.get(i).getRoles().getRoleName()=="设计师"||"设计师".equals(userList1.get(i).getRoles().getRoleName())){
						userList.add(userList1.get(i));
					}
				}
			}
			request.getSession().setAttribute("userList", userList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/login.do")
	public String login(Model model, HttpServletRequest request,User user, WebCase webCase) {
		Role role = new Role();
		Permission permission = new Permission();
		List<WebCase> webCaseList = null;
		List<Role> roleList = new ArrayList<Role>();
		List<Permission> permissionList = new ArrayList<Permission>();
		User userone = null;
		String roleIds = null;
		String[] roleIdsArra = null;
		String[] permissionIdsArra = null;
		JSONObject json = null;
		String pictureStr  = null;
		if(user.getUserName()!=null&&!"".equals(user.getUserName())){
			if(user.getPassWord()!=null&&!"".equals(user.getPassWord())){
				try {
					/*
					 * 备注： 由于字符编码问题导致查询不成功（无效列：111），修改工程字符编码（utf-8），保证与数据库编码一致。
					 */
					userone = (User) dao.findOne("findLoginUser", user);
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
					json = new JSONObject();
					json.put("list", webCaseList);
					/* 查询用户角色 */
					if (userone != null) {
						roleIds = userone.getRoleIds();
						role.setRoleId(roleIds);
						role = (Role) dao.findOne("findUserRole",role);
						userone.setRoles(role);
//						 查询角色权限 
						String[] permissionIdArra = role.getPermissionId().split(",");
						for(int i=0;i<permissionIdArra.length;i++){
							permission.setPermissionId(permissionIdArra[i]);
							permission = (Permission) dao.findOne("findUserRolePermission",permission);
							permissionList.add(permission);
						}
						userone.setPermissions(permissionList);
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		/**
		 * 向页面传值
		 */
		if (userone != null) {
			request.getSession().setAttribute("user", userone);	
			//request.getSession().setAttribute("webCaseListRequest", webCaseList);
			model.addAttribute("json", json);
			model.addAttribute("webCaseList", webCaseList);
			this.findAllDesigner(request,model);
			return "index";
		} else {
			if(user==null||"".equals(user)){
				model.addAttribute("msg", "违法操作，请登录！");
				model.addAttribute("linked", "http://192.168.0.60:8080/spring_mybatis/");
				return "commons/returnPage";
			}else{
				model.addAttribute("msg", "用户名或密码错误，请重新输入");
				model.addAttribute("linked", "http://192.168.0.60:8080/spring_mybatis/");
				return "commons/returnPage";
			}		
		}
	}
	
	@RequestMapping("/loginError.do")
	public void loginError(HttpServletRequest request,HttpServletResponse response){
		User user = null;
		User user1 = new User();
		String userName = request.getParameter("name");
		String passWord = request.getParameter("psw");
        //新建一个printWriter对象  
        PrintWriter pw = null;  
        //通过response 获取pw  
        try {
			pw=response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        if(userName!=null&&!"".equals(userName)){
        	if(passWord!=null&&!"".equals(passWord)){
        		pw.print("{\"result\":\"1\"}");
        	}
        }
        pw.flush();  
        pw.close();    
    }
	
	@RequestMapping("/logout.do")
	public void logout(HttpServletRequest request,HttpServletResponse response){
		
		request.getSession().invalidate();
		try {
			response.sendRedirect("../login.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/toSet.do")
	public String toSet(){
		
		return "user/set";
		
	}
	@RequestMapping("/findAllUser.do")
	public String findAllUser(Model model, User user) {
		List<User> List;
		List<User> userList = new ArrayList<User>();
//		User userOne = null;
		Role role =  new Role();
		try {
//			userOne = (User) dao.findOne("findOneUser", user);
			List = dao.findList("findAllUser", null);
			if(!List.isEmpty()){
				for(int i = 0;i<List.size();i++){
					role.setRoleId(List.get(i).getRoleIds());
					role = (Role) dao.findOne("findUserRole", role);
					List.get(i).setRoles(role);
					userList.add(List.get(i));
				}
			}
			model.addAttribute("allUser", userList);
//			model.addAttribute("userOne", userOne);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "user/allUser";
	}
	@RequestMapping("/findAllUser2.do")
	public String findAll2(Model model, User user) {
		List<User> List;
		List<User> userList = new ArrayList<User>();
		Role role =  new Role();
		try {
			List = dao.findList("findAllUser", user);
			if(!List.isEmpty()){
				for(int i = 0;i<List.size();i++){
					role.setRoleId(List.get(i).getRoleIds());
					role = (Role) dao.findOne("findUserRole", role);
					List.get(i).setRoles(role);
					userList.add(List.get(i));
				}
			}
			model.addAttribute("allUser", userList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "user/allUser";
	}
	@RequestMapping("/findOneUser.do")
	public String findOneUser(Model model,User user){
		Role role =  new Role();
		List<User> userList = new ArrayList<User>();
		try {
			user = (User) dao.findOne("findAllUser", user);
			if(!user.getUserId().isEmpty()){
				role.setRoleId(user.getRoleIds());
				role = (Role) dao.findOne("findUserRole", role);
				user.setRoles(role);
				userList.add(user);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("allUser", userList);
		return "user/allUser";
		
	}

	/**
	 *	增加新用户
	 * @return
	 */
	@RequestMapping("/toAddUser")
	public String toAddUser(){
		
		return "user/addUser";
		
	}
	@RequestMapping("/addUser")
	public String addUser(Model model,User user,
			HttpServletRequest request,@RequestParam(value="file",required=false)MultipartFile file ){
		
		int flag = 0;
		try {
			if(user.getUserId()==null||"".equals(user.getUserId())){
				user.setUserId(new DecimalFormat("00000").format(Math.random()*1000000));
			}
			String path =request.getContextPath()+"/upload/user/" + file.getOriginalFilename();
			user.setPhoto(path);
			String realPath = request.getSession().getServletContext().getRealPath("upload")+"/user";
			File targetFile = new File(realPath, file.getOriginalFilename());  
			if(targetFile.exists()){  
		        targetFile.delete();  
		    }  
		    if(!targetFile.exists()){  
		        targetFile.mkdirs();  
		    }  
			file.transferTo(targetFile);
			flag = dao.update("addUser", user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (flag > 0) {
			model.addAttribute("msg", "添加成功！");
			this.findAllDesigner(request, model);
			model.addAttribute("linked","user/findAllUser2.do");
		} else {
			model.addAttribute("msg", "添加失败！");
			model.addAttribute("linked","user/addUser.jsp");	
		}
		return "commons/returnPage";
		
	}
	/*
	 *	删除用户 
	 */
	@RequestMapping("/delUser.do")
	public String delUser(Model model,User user,HttpServletRequest request){
		int flag = 0;
		try {
			flag = dao.deleteById("delUser",user );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag>0){
			model.addAttribute("msg", "删除成功！");
			this.findAllDesigner(request, model);
			model.addAttribute("linked", "user/findAllUser2.do");
		}else{
			model.addAttribute("msg", "删除失败！");
			model.addAttribute("linked", "user/findAllUser2.do");
		}
		return "commons/returnPage";
		
	}
	@RequestMapping("/toUpdateSelf.do")
	public String toUpdateSelf(User user,Model model) {
		/*
		 * *后台传值问题*： 表单中使用name标签注入值！！！
		 */
		Role role = new Role();
		try {
			user = (User) dao.findOne("findOneUser", user);

			role.setRoleId(user.getRoleIds());
			role = (Role) dao.findOne("findUserRole", role);
			user.setRoles(role);
			model.addAttribute("updateUser", user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "user/updateSelfMessage";

	}
	@RequestMapping("/toUpdateUser.do")
	public String toUpdateUser(User user,Model model) {
		/*
		 * *后台传值问题*： 表单中使用name标签注入值！！！
		 */
		Role role = new Role();
		try {
			user = (User) dao.findOne("findOneUser", user);

			role.setRoleId(user.getRoleIds());
			role = (Role) dao.findOne("findUserRole", role);
			user.setRoles(role);
			model.addAttribute("updateUser", user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "user/updateUserMessage";

	}
	@RequestMapping("/updateSelfMessage.do")
	public String updateSelfMessage(Model model, User user,
			HttpServletRequest request,@RequestParam(value="file",required=false)MultipartFile file ) {
		int flag = 0;
		String roleIds = user.getRoleIds();
		try {
			String fileName = file.getOriginalFilename();
			if(fileName!=null&&!"".endsWith(fileName)){
				String path =request.getContextPath()+"/upload/user/"+fileName;
				user.setPhoto(path);
				String realPath = request.getSession().getServletContext().getRealPath("upload")+"/user";
				File targetFile = new File(realPath, file.getOriginalFilename());  
				if(targetFile.exists()){  
			        targetFile.delete();  
			    }  
			    if(!targetFile.exists()){  
			        targetFile.mkdirs();  
			    }  
				file.transferTo(targetFile);
			}
			flag = dao.update("updateUserMessage", user);
			this.findAllUser(model);
			this.findOneUser(model, user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		if (flag > 0) {
			model.addAttribute("msg", "修改资料成功,请重新登录！");
			model.addAttribute("linked","http://192.168.0.60:8080/spring_mybatis");	
		} else {
			model.addAttribute("msg", "修改资料失败！");
			model.addAttribute("linked","user/updateSelfMessage.jsp");	
		}
		return "commons/return";	
	}
	@RequestMapping("/updateUserMessage.do")
	public String updateUserMessage(Model model, User user,
			HttpServletRequest request,@RequestParam(value="file",required=false)MultipartFile file ) {
		int flag = 0;
		String roleIds = user.getRoleIds();
		try {
			String fileName = file.getOriginalFilename();
			if(fileName!=null&&!"".endsWith(fileName)){
				String path =request.getContextPath()+"/upload/user/"+fileName;
				user.setPhoto(path);
				String realPath = request.getSession().getServletContext().getRealPath("upload")+"/user";
				File targetFile = new File(realPath, file.getOriginalFilename());  
				if(targetFile.exists()){  
			        targetFile.delete();  
			    }  
			    if(!targetFile.exists()){  
			        targetFile.mkdirs();  
			    }  
				file.transferTo(targetFile);
			}
			flag = dao.update("updateUserMessage", user);
			this.findAllUser(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		if (flag > 0) {
			return "user/allUser";
		} else {
			model.addAttribute("msg", "修改资料失败！");
			model.addAttribute("linked","user/updateUserMessage.jsp");	
			return "commons/returnPage";
		}
		
	}

	@RequestMapping("toUpdateUserPhoto.do")
	public String toUpdateUserPhoto(){
		
		return "user/setPhoto";
		
	}
	@RequestMapping("updateUserPhoto.do")
	public String updateUserPhoto(Model model,User user,
			HttpServletRequest request,@RequestParam(value="file",required=false)MultipartFile file){
		int flag  = 0;
		String path =request.getContextPath()+"/upload/user/" + file.getOriginalFilename();
		user.setPhoto(path);
		String realPath = request.getSession().getServletContext().getRealPath("upload")+"/user";
		File targetFile = new File(realPath, file.getOriginalFilename());  
		if(targetFile.exists()){  
	        targetFile.delete();  
	    }  
	    if(!targetFile.exists()){  
	        targetFile.mkdirs();  
	    }  
	    try {
			file.transferTo(targetFile);
			flag = dao.update("updateUserPhoto", user);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (flag > 0) {
			model.addAttribute("msg", "修改头像成功，请重新登陆！");
			model.addAttribute("linked","login.jsp");
		} else {
			model.addAttribute("msg", "修改失败失败！");
			model.addAttribute("linked","user/toSet.do");	
		}
		return "commons/returnPage";
		
	}
	@RequestMapping("/checkUserName.do")
	public void checkUserName(HttpServletRequest request,HttpServletResponse response){
		User user = null;
		boolean flag  = false;
		User user1 = new User();
		String userName = request.getParameter("userName");
        //新建一个printWriter对象  
        PrintWriter pw = null;  
        //通过response 获取pw  
        try {
			pw=response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
        if(userName!=null&&!"".equals(userName)){
        	user1.setUserName(userName);
        	 try {
        		 user  = (User)dao.findOne("findOneUser", user1);
     			response.setCharacterEncoding("utf-8");  	 
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        }
       flag = new CheckChineseName().checkName(userName);
       if(!flag){
    	   pw.print("{\"result\":\"3\"}");
       }else if(userName==null||"".equals(userName)){     	
         	 pw.print("{\"result\":\"9\"}");  
       }else if(userName.length()<3||userName.length()>10){
    	     pw.print("{\"result\":\"11\"}");
       }else if(user==null){  
            //用pw对象传递json  
            pw.print("{\"result\":\"0\"}");//该用户名可以使用  
       }else if(user!=null){
    	   pw.print("{\"result\":\"8\"}");
       }else{  
            pw.print("{\"result\":\"1\"}");  
        }  
        pw.flush();  
        pw.close();    
    }
}
