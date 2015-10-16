<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.pm.system.entity.User"%>
<%@page import="com.pm.template_case.entity.Template" %>
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
		<title>素材删除-素材库</title>


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
		$(document).ready(function() {// 页面加载前准别
/*		
		  $("#checkAll").click(function() {//全选input.id
		    var all=this; //#checkAll
		    $('input[name="subBox"]').each(function(){//input.name ='subBox'
		        var sub=this;  //input[name="subBox"]      
		        sub.checked=all.checked;//全选点击subBox全选       
		     });        
          });*/
          //全勾选subBox实现全选，否则实现不全选
          var $subBox = $("input[name='subBox']");
          $subBox.click(function(){             
              if($subBox.length == $("input[name='subBox']:checked").length){
                    /*$("#checkAll")[0].checked=true;*/
                    alert("图片不能全删除！");
                    return false;
              }else{
                	/*$("#checkAll")[0].checked=false;*/
                	return true;
              }
               //$("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
               // 实现不了，原因：Jquery版本不支持                        
            });		
    })

	function callBack(){
		window.location="/spring_mybatis/template/chooseTemplate.do";		
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
	function delTemplate(){
		var objs=document.getElementsByName('subBox');
		var isSel=false;//判断是否有选中项，默认为无
		for(var i=0;i<objs.length;i++)
		{
		  if(objs[i].checked==true)
		   {
		    isSel=true;
		    break;
		   }
		}
		if(isSel==false)
		{
			alert("请选择要删除的图片！"); 
			return false;
		}else
		{
			return true;
		}
	}
	function sub(){
		if(confirm("是否删除图片 ?")){
			if(delTemplate()){
				window.templateForm.submit();
			}
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
					 图片删除
					<a href="site/findAllWebCase.do" class='back pull-right'>返回首页</a>
				</div>
			</div>
			
			<div class="panel-body">
				<form action="template/delTemplatePicture.do" class="form-horizontal" id="templateForm" name ="templateForm" method="post">
					<div class="form-group">
					    <button class="btn btn-upload" type="button" onclick="callBack()">返回</button>
					</div> 
				<div class="detailFrame POS-R">
					<div class="ctr ctr-left"></div>
					<div class="ctr ctr-right"></div>
					<div class="detailWindow POS-R">
					<input type="hidden" name="templateIntf" id="templateIntf" value="${templatePic.templateIntf}"/>
					<ul>
					<% 
						Template templatePic = (Template)request.getAttribute("templatePic");
					%>
					<!-- <input type="checkbox" id="delAll" value="<%= templatePic.getPictureList().size()%>"/>全选<br/>  -->
						<center><li class="active"><a>
					<%
						for(int i=0;i<templatePic.getPictureList().size();i++){
					%>	
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="subBox" id="delTemplate" value="<%= i%>"/>&nbsp;&nbsp;
						<img src="<%=templatePic.getPictureList().get(i) %>" style="width:80px;height:80px;border:10px;padding:10px;color:#000000;"/>
						
						</a>&nbsp;&nbsp;&nbsp;
		
					<%
					}
						
					 %>
					 	</li></center>
					</ul>
				</div>
				</div>
					</div>
					<button type="button" class="btn btn-submit" onclick="sub()">删除</button>
				</form>
				
			</div>
		</div>
	</div>
	
</body>
</html>





