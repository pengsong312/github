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
		<title>图片素材上传-素材库</title>


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
	
		var picture = $("#file").val();

		var pictureIntf = $("#pictureIntf").val();	
	
		var note = $("#note").val();

		var classification = $("#classification").val();

		var copyrightInformation = $("#copyrightInformation").val();

		var pictureUrl = $("#picture").val();
		
		var urlHead = pictureUrl.substr(0,11);
		
		var urlHttp = pictureUrl.substr(0,7);
		
		var regx = /^[^\u4e00-\u9fa5]*$/;
		
		if(picture==""){
			alert("未选择上传的图片");
			return false;
		}
		if(!regx.test(picture)){
			alert("图片名称含有中文，不合法！");
			return false;
		}
		if(pictureIntf==""){
			alert("编号不能为空！");
			$("#pictureIntf").focus();
			return false;
		}
		if(pictureIntf.length==0||pictureIntf.length>10){
			alert("编号长度不合法！");
			document.getElementById("pictureIntf").focus();
			return false;
		}
		if(note==""){
			alert("备注不能为空！");
			return false;
		}
		if(classification==""){
			alert("分类不能为空！");
			return false;
		}
		if(copyrightInformation==""){
			alert("版权信息不能为空！");
			return false;
		}
		if(pictureUrl==""){
			alert("图片链接不能为空！");
			$("#picture").focus();
			return false;
		}
		if(urlHead!="http://www."&&urlHttp!="http://"){
			alert("链接必须是http://www.或http://格式！");
			$("#picture").focus();
			return false;
		}
		return true;
	}
	
	function checkPictureIntf(){
		$("#spaName").html("");
		var pictureIntf = $("#pictureIntf").val();//id为username的文本框的值
		 $.ajax(  
              {  
                   type:"post",  
                   url:"picture/checkPictureIntf.do",  
                   data:"pictureIntf="+$("#pictureIntf").val(),  
                   dataType:"json",  
                   success:function(data)  
                    {  
                        if(data.result=="0")  
                        { 
                            $("#spaName").html("<font color=green>可以使用</font>");  
                         
                        }else if(data.result=="11"){
                        	$("#spaName").html("<font color=red>不合法长度</font>");  
                            $("#pictureIntf").focus(); 
                           	return false;
                        }else if(data.result=="9")  
                        {                       
                            $("#spaName").html("<font color=red>不能为空</font>");  
                            $("#pictureIntf").focus(); 
                           	return false;
                        }else if(data.result=="3"){
                        	$("#spaName").html("<font color=red>不合法编号</font>");  
                            $("#pictureIntf").focus(); 
                           	return false;
                        }else if(data.result=="8"){
                        	$("#spaName").html("<font color=red>该编号已被占用</font>");  
                            $("#pictureIntf").focus(); 
                           	return false;
                        }else{
                        	 $("#spaName").html("<font color=red>不可使用</font>");  
                            $("#pictureIntf").focus(); 
                           	return false;
                        }  
                    }/*,  
                    error:function()  
                    {  
                        alert("加载失败！");  
                    }  */
                });   
	}
	
	function sub(){
		if(subForm()){
			document.pictureForm.submit();
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
					 图片案例上传
					<a href="picture/findAllPicture.do" class='back pull-right'>返回首页</a>
				</div>
			</div>
			<div class="panel-body">
				<form action="picture/uploadPicture.do" class="form-horizontal" id="pictureForm" name ="pictureForm" enctype="multipart/form-data" method="post">
					<div class="form-group">
					    <button class="btn btn-upload" type="button">上传图片素材</button>
					</div>
					
					<div class="form-group">
					 	<label class="col-lg-1 col-lg-offset-2 control-label">选择图片：</label>
					    <div class=" col-lg-5">
					     	<input type="file" name="file" id="file" class="btn btn-upload"/>
					 	</div>
					 	<div class="col-lg-2 form-label">
					   **只支持一张图片且以英文命名**
						</div>
					</div>
					
					<div class="form-group">
					 	<label class="col-lg-1 col-lg-offset-2 control-label">编号：</label>
					    <div class=" col-lg-5">
					    	<input type="text" class="form-control" placeholder="" name="pictureIntf" id="pictureIntf" onblur="checkPictureIntf()">
					    	<span id="spaName"></span>
					 	</div>
					 	<div class="col-lg-2 form-label">
					    		*长度3-10的字母或数字*
					    </div> 
					</div>
					
					<div class="form-group">
					    <label class="col-lg-1 col-lg-offset-2 control-label">分类：</label>
					    <div class=" col-lg-5">
					      	<select class="form-control" placeholder="" name="classification" id="classification">
					      		<option selected="selected" value="">--请选择--</option>
					      		<option value="极简主义">极简主义</option>
					      		<option value="清新">清新</option>
					      		<option value="扁平化">扁平化</option>
					      		<option value="商城化">商城化</option>
					      		<option value="多图">多图</option>
					      		<option value="内容优先">内容优先</option>
					      		<option value="其他">其他</option>
					      	</select>
					    </div>
					</div>
					
					<div class="form-group">
					    <label class="col-lg-1 col-lg-offset-2 control-label">备注：</label>
					    <div class=" col-lg-5">
					      	<input type="text" class="form-control" placeholder="" id="note" name="note">
					    </div>
					</div>
					
					<div class="form-group">
					    <label class="col-lg-1 col-lg-offset-2 control-label">版权信息：</label>
					    <div class=" col-lg-5">
					      	<input type="text" class="form-control" placeholder="" id="copyrightInformation" name="copyrightInformation">
					    </div>
					</div>
					
					<div class="form-group">
					    <label class="col-lg-1 col-lg-offset-2 control-label">案例链接：</label>
					    <div class=" col-lg-5">
					      	<input type="text" class="form-control" placeholder="" id="picture" name="picture">
					    </div>
					
					</div>
					
					<button type="button" class="btn btn-submit" onclick="sub()">上传</button>
				</form>
				
			</div>
		</div>
	</div>
	
</body>
</html>





