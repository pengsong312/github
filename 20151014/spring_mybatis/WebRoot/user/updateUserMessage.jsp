<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.pm.system.entity.User"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!--<meta name="viewport" content="width=device-width, initial-scale=1">-->
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<base href="<%=basePath%>">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<title>用户信息修改-素材库</title>


		<!-- Bootstrap 调试使用-->
		<!--<link href="css/bootstrap.min.css" rel="stylesheet">-->
		<!-- 新 Bootstrap 核心 CSS 文件 -->
		<link rel="stylesheet" href="material/css/bootstrap.min.css">
		<!-- font-awesome 矢量图标-->
		<link rel="stylesheet" href="material/css/font-awesome.min.css" />

		<!-- user define -->
		<link rel="stylesheet" href="material/css/main.css" />
		<link rel="stylesheet" href="material/css/index.style.css" />
		<link rel="stylesheet" href="material/css/pic.style.css" />
		<link rel="stylesheet" href="material/css/set.style.css"/>


		<!-- jQuery -->
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="material/js/jquery-1.11.2.min.js"></script>
		<script src="material/js/bootstrap.min.js"></script>
		<script src="material/js/masonry.pkgd.min.js"></script>
		<script src="material/js/imagesloaded.pkgd.min.js"></script>

		<script src="material/js/main.js"></script>
	<!-- this page script-->
	<script type="text/javascript">
		function checkUserName(){
			$("#spaName").html("");
			var userName = $("#userName").val();//id为username的文本框的值
			var userName1 = $("#userName1").val();
			if(userName!=userName1){
				$.ajax(  
	              {  
	                   type:"post",  
	                   url:"user/checkUserName.do",  
	                   data:"userName="+$("#userName").val(),  
	                   dataType:"json",  
	                   success:function(data)  
	                    {  
	                       if(data.result=="0")  
	                        { 
	                            $("#spaName").html("<font color=green>可以使用</font>");  
	                         
	                        }else if(data.result=="11"){
	                        	$("#spaName").html("<font color=red>长度不合法</font>");  
	                           	document.getElementById("userName").focus();
	                           	return false;
	                        }else if(data.result=="9"){                       
	                            $("#spaName").html("<font color=red>不能为空</font>");  
	                           	document.getElementById("userName").focus();
	                           	return false;
	                        }else if(data.result=="8"){
	                        	$("#spaName").html("<font color=red>该用户名已被占用</font>");  
	                           	document.getElementById("userName").focus();
	                           	return false;
	                        }else if(data.result=="3"){
	                        	$("#spaName").html("<font color=red>不合法编号</font>");  
	                           	document.getElementById("userName").focus();
	                           	return false;
	                        }else{
	                        	$("#spaName").html("<font color=red>不可使用</font>");  
	                        	document.getElementById("userName").focus();
	                           	return false;
	                        }  
	                    }
	                });  
			}
		}
		function checkRole(){
			var checkAdmin = $("#checkAdmin").val();
			if(checkAdmin!="管理员"){
				alert("对不起，您无权修改角色！");
				return false;
			}
		}
	    function delMaterialType(){
   	 	var $select = $("#roleIds");
		var $options = $select.find("option");
		for(var i=0,max=$options.length;i<max;i++){
    		var $item1 = $options.eq(i);
    		for(var j=0;j<max;j++){
       		 	if(i==j){
            		continue;
		        }
			var $item2 = $options.eq(j);        
        	if($item1.attr("value") == $item2.attr("value")){
            	i > j ? $item1.remove() : $item2.remove();
        		}        
    		}
		}
	}
	function checkAdmin(){
		var  roleName = $("#roleName").val();
		var  userId = $("#userId").val();	
		if(roleName!='管理员'){
			alert("对不起，您无权操作！");
			return false;
		}
		else{
			window.location="/spring_mybatis/user/findAllUser2.do";
			return true;
		}
	}
	function checkForm(){
		var userName = $("#userName").val();
		var passWord = $("#passWord").val();
		var roleIds = $("#roleIds").val();
		if(userName==''){
			alert("用户名不能为空");
			return false;
		}
		if(passWord==''){
			alert("密码不能为空");
			return false;
		}
		if(roleIds==''){
			alert("角色不能为空");
			return false;
		}
		return true;
	}
	function sub(){
	
		if(checkForm()){
			document.updateUserMessageForm.submit();
			/*document.getElementById("updateUserMessageForm").submit()*/
		}
	}
	</script>
</head>
<body>
				<%
			User user = (User)request.getSession().getAttribute("user");
		%>	
	<input type="hidden" id="roleName" value="<%=user.getRoles().getRoleName() %>"/>
	<input type="hidden" id="userId" value="<%=user.getRoleIds() %>"/>
	<!--header-->
	<header id='header'>
		<div class="container">
			<div class="logo pull-left"><a href="site/findAllWebCase.do"><img src="material/images/logo.png"/></a></div>			
			<div class="user pull-right dropdown">
		
				<div class="userPic pull-left"><img src="<%=user.getPhoto()%>"/></div>
				<a href="#" class="name pull-left FONT14 COLOR-W dropdown-toggle" data-toggle='dropdown' id='dropD'><%=user.getRealName()%><span class="fa fa-angle-down COLOR-W btn-down"></span></a>
				<ul class="dropdown-menu" aria-labelledby="dropD">					
	                <li><a href="user/toSet.do">设置</a></li>
	                <li><a onclick='checkAdmin()'>管理用户</a></li>
	                <li>
	                	<a>上传<span class="caret"></span></a>
	                	<ul>
	                		<li><a href="site/toUploadSite.do">网站案例</a></li>
	                		<li>
	                			<a>移动案例<span class="caret"></span></a>
	                			<ul>
	                				<li><a href="mobile/toUploadMobile.do">手机站</a></li>
	                				<li><a href="mobile/toUploadApp.do">APP</a></li>
	                			</ul>
	                		</li>
	                		<li><a href="dynamic/toUploadDynamic.do">动效素材</a></li>
	                		<li><a href="template/toUploadTemplate.do">模板素材</a></li>
	                		<li><a href="picture/toUploadPicture.do">图片素材</a></li>
	                	</ul>
	                </li>
	                <li>
	                	<a>修改<span class="caret"></span></a>
	                	<ul>
	                		<li><a href="site/chooseSite.do">网站案例</a></li>
	                		<li>
	                			<a>移动案例<span class="caret"></span></a>
	                			<ul>
	                				<li><a href="mobile/chooseMobile.do">手机站</a></li>
	                				<li><a href="mobile/chooseApp.do">APP</a></li>
	                			</ul>
	                		</li>
	                		<li><a href="dynamic/chooseDynamic.do">动效素材</a></li>
	                		<li><a href="template/chooseTemplate.do">模板素材</a></li>
	                		<li><a href="picture/choosePicture.do">图片素材</a></li>
	                	</ul>
	                </li>
	                <li role="separator" class="divider"></li>
	                <li><a href="user/logout.do">注销</a></li>
             	 </ul>
			</div>
		</div>
	</header>
	
	<div class="container " id="panel-wrapper">
		<div class="panel panel-default">
			<div class="panel-heading">
				<div>
					<span><img src="material/images/set.png" alt="" /></span>
					 设置
					<a href="site/findAllWebCase.do" class='back pull-right'>返回首页</a>
				</div>
			</div>
			<form action="user/updateUserMessage.do" method="post" name="updateUserMessageForm" id="updateUserMessageForm" enctype="multipart/form-data" >
			<div class="panel-body">
				<div class="row">
					<div class="col-lg-3 col-lg-offset-2">
						<input type="hidden" name="userId" value="${updateUser.userId}"/>
						<p>修改头像：	<br><input type="file" name="file" id="file"/></p>
						<input type="hidden" name="photo" value="${updateUser.photo}"/>
						<input type="hidden" name="userName1" id="userName1" value="${updateUser.userName}"/>
						<p>用户名：<input type="text" name="userName" id="userName" value="${updateUser.userName}" onblur="checkUserName()"/>
						<div id="spaName"></div>
						</p>
						<p>密码：    <input type="text" name="passWord" id="passWord" value="${updateUser.passWord}"/></p>
						<p>
						<input type="hidden" id="checkAdmin" value="<%=user.getRoles().getRoleName() %>"/>
							角色：
							<select name="roleIds" id="roleIds" onclick="delMaterialType()" onchange="checkRole()">
								<option selected="selected" value="${updateUser.roleIds}">${updateUser.roles.roleName}</option>
								<option value="101">管理员</option>
								<option value="102">设计师</option>
								<option value="103">普通用户</option>
							</select>
						</p>
						<!-- href="javascript:document:updateUserMessageForm.submit()" -->
						<a  class="btn btn-edit" onclick="sub()">确定</a>
					</div>
					
					<div class="col-lg-3 col-lg-push-3">
						<div class="headPic">
							<img src="${updateUser.photo}"/>
						</div><br>
						<!--<a href="user/toUpdateUserPhoto.do" class="btn btn-modify">修改头像</a>-->
					</div>
				</div>
			</div>
			</form>
		</div>
	</div>
	
</body>
</html>





