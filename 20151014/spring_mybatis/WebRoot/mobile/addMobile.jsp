<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.pm.system.entity.User"%>
<%@page import="com.pm.mobile_case.entity.Mobile"%>
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
		<title>网站上传-素材库</title>


		<!-- Bootstrap 调试使用-->
		<!--<link href="css/bootstrap.min.css" rel="stylesheet">-->
		<!-- 新 Bootstrap 核心 CSS 文件 -->
		<link rel="stylesheet" href="material/css/bootstrap.min.css">
		<!-- font-awesome 矢量图标-->
		<link rel="stylesheet" href="material/css/font-awesome.min.css" />

		<!-- user define -->
		<link rel="stylesheet" href="material/css/main.css" />
		<link rel="stylesheet" href="material/css/index.style.css" />
		<link rel="stylesheet" href="material/css/upload.style.css"/>
		<!-- jQuery -->
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="material/js/jquery-1.11.2.min.js"></script>
		<script src="material/js/bootstrap.min.js"></script>
		<script src="material/js/masonry.pkgd.min.js"></script>
		<script src="material/js/imagesloaded.pkgd.min.js"></script>
		<script src="material/js/main.js"></script>
	<!-- this page script-->
	<script type="text/javascript">
	
	function subForm(){

		var file = $("#file").val();
		var files;
		
		var regx = /^[^\u4e00-\u9fa5]*$/;

		//取得FileList取得的file集合 
    	for(var i = 0 ;i<document.getElementById("file").files.length;i++){ 
        	//file对象为用户选择的某一个文件 
       		files=document.getElementById("file").files[i]; 
        	//此时取出这个文件进行处理，这里只是显示文件名 
      		if(!regx.test(files.name)){
      			alert("文件名包含中文，不合法！");
				return false;
				break;
      		}
         
   		 } 
		if(file==""){
			alert("图片不能为空！");
			return false;
		}
		return true;
	}
	
	function sub(){
		if(subForm()){
			document.mobileForm.submit();
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
			window.location="/spring_mybatis/user/findAllUser.do?userId="+userId;
			return true;
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
					<span><img src="material/images/up.png" alt="" /></span>
					 图片增加
					<a href="site/findAllWebCase.do" class='back pull-right'>返回首页</a>
				</div>
			</div>
			
			<div class="panel-body">
				<form action="mobile/addMobilePicture.do" class="form-horizontal" id="mobileForm" name ="mobileForm" enctype="multipart/form-data" method="post">
					<div class="form-group">
					    <button class="btn btn-upload" type="button">案例图片增加</button>
					</div> 
				<div class="detailFrame POS-R">
					<div class="ctr ctr-left"></div>
					<div class="ctr ctr-right"></div>
					<div class="detailWindow POS-R">
					<ul>
						<center><li class="active"><a>
					<%
						Mobile mobilePic = (Mobile)request.getAttribute("mobilePic");
						for(int i=0;i<mobilePic.getPictureList().size();i++){
					%>	
						<img src="<%=mobilePic.getPictureList().get(i) %>" style="width: 80px;height: 80px;"/>
						
						</a>&nbsp;&nbsp;&nbsp;
		
					<%
					}
						
					 %>
					 	</li></center>
					</ul>
				</div>
				</div>
					<input type="hidden" name="mobileIntf" value="${mobilePic.mobileIntf}"/>
					<input type="hidden" name="pictureList" value="${mobilePic.pictureList}"/>
					<br><br>
					<div class="form-group">
					 	<label class="col-lg-1 col-lg-offset-2 control-label">增加图片：</label>
					    <div class=" col-lg-5">
					     	<input type="file" name="file" id="file" class="btn btn-upload" multiple="multiple"/>
					 	</div>
					 	<div class="col-lg-2 form-label">
					    	*可选择多张图片*
					    </div>
					</div>
					<button type="button" class="btn btn-submit" onclick="sub()">增加</button>
				</form>
				
			</div>
		</div>
	</div>
	
</body>
</html>





