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
		<title>动效修改-素材库</title>


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
	function checkDynamicIntf(str){
		$("#spaName").html("");
		var dynamicIntf = $("#dynamicIntf").val();//id为username的文本框的值
		if(str!=dynamicIntf){
			$.ajax(  
              {  
                   type:"post",  
                   url:"dynamic/checkDynamicIntf.do",  
                   data:"dynamicIntf="+$("#dynamicIntf").val(),  
                   dataType:"json",  
                   success:function(data)  
                    {  
                        if(data.result=="0")  
                        { 
                            $("#spaName").html("<font color=green>可以使用</font>");  
                         
                        }else if(data.result=="11"){
                        	$("#spaName").html("<font color=red>不合法长度</font>");  
                            $("#dynamicIntf").focus(); 
                           	return false;
                        }else if(data.result=="9"){                       
                            $("#spaName").html("<font color=red>不能为空</font>");  
                            $("#dynamicIntf").focus(); 
                           	return false;
                        }else if(data.result=="8"){
                        	$("#spaName").html("<font color=red>该编号已被占用</font>");  
                            $("#dynamicIntf").focus(); 
                           	return false;
                        }else if(data.result=="3"){
                        	$("#spaName").html("<font color=red>不合法编号</font>");  
                            $("#dynamicIntf").focus(); 
                           	return false;
                        }else{
                        	$("#spaName").html("<font color=red>不可使用</font>");  
                            $("#dynamicIntf").focus(); 
                           	return false;
                        }  
                    },  
                    error:function()  
                    {  
                        alert("加载失败！");  
                    }  
                });   	
		}
	}
	function subForm(){
		
		var dynamicIntf = $("#dynamicIntf").val();
		
		var style = $("#style").val();

		var industry = $("#industry").val();

		var color = $("#color").val();

		var designer = $("#designer").val();

		var siteUrl = $("#dynamicUrl").val();

		var urlHead = siteUrl.substr(0,11);
		
		var urlHttp  = siteUrl.substr(0,7);
		
		var reg = /^[A-Za-z ]*$/ ;
		
		var file = $("#file").val()
		
		var regx = /^[^\u4e00-\u9fa5]*$/;
		if(urlHead!="http://www."&&urlHttp!="http://"){
			alert("案例链接必须为http://或http://");
			return false;
		}
      	if(!regx.test(file)){
      		alert("文件名包含中文，不合法！");
			return false;
      	}	
		if(dynamicIntf.length==0||dynamicIntf.length>10){
			alert("编号长度不合法！");
			return false;
		}
		if(style==""){
			alert("风格不能为空！");
			return false;
		}
		if(industry==""){
			alert("行业不能为空！");
			return false;
		}
		if(color==""){
			alert("颜色不能为空！");
			return false;
		}
		else{
			if(!reg.test(color)){
				alert("请使用英语输入！");
				return false;
			}
		}
		if(designer==""){
			alert("设计师不能为空！");
			return false;
		}
		if(siteUrl==""){
			alert("网站案例链接不能为空！");
			return false;
		}
		return true;
	}
	
	function sub(){
		var file = $("#file").val();
		if(file!=''){
			if(confirm("是否替换原图片?")){
				if(subForm()){
					document.dynamicForm.submit();
				}	
			}
		}else{
			if(subForm()){
				document.dynamicForm.submit();
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
	
	function delMaterialType(){
   		var $select = $("#materialType");//designer下拉框Id
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
					 动效修改
					<a href="site/findAllWebCase.do" class='back pull-right'>返回首页</a>
				</div>
			</div>
			
			<div class="panel-body">
				<form action="dynamic/updateDynamic.do" class="form-horizontal" id="dynamicForm" name ="dynamicForm" enctype="multipart/form-data" method="post">
					<div class="form-group">
					    <button class="btn btn-upload" type="button" onclick="window.location=('/spring_mybatis/dynamic/chooseDynamic.do')">返回</button>
					</div> 
					<div class="form-group">
					 	<label class="col-lg-1 col-lg-offset-2 control-label">修改图片：</label>
					    <div class=" col-lg-5">
					     	<input type="file" name="file" id="file" class="btn btn-upload" value=""/>
					 	</div>
					</div>
					<div class="form-group">
					 	<label class="col-lg-1 col-lg-offset-2 control-label">编号：</label>
					    <div class=" col-lg-5">
					    	<input type="text" class="form-control" placeholder="" name="dynamicIntf" id="dynamicIntf" onblur="checkDynamicIntf('${updateDynamic.dynamicIntf}')" value="${updateDynamic.dynamicIntf}">
					    	<span id="spaName"></span>
					 	</div>
					 	<div class="col-lg-2 form-label">
					    		*长度3-10的字母或数字*
					    </div>
					</div>
					
					<input type="hidden" name="dynamicNumber" value="${updateDynamic.dynamicNumber}"/>
					<input type="hidden" name="picture" value="${updateDynamic.picture}"/>
					<div class="form-group">
					    <label class="col-lg-1 col-lg-offset-2 control-label">类型：</label>
					    <div class=" col-lg-5">					      
					      	<select class="form-control" placeholder="" name="materialType" id="materialType" onclick="delMaterialType()">
					      		<option selected="selected" value="${updateDynamic.materialType}">${updateDynamic.materialType}</option>
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
					    <label class="col-lg-1 col-lg-offset-2 control-label">案例链接：</label>
					    <div class=" col-lg-5">
					      	<input type="text" class="form-control" placeholder="" name="dynamicUrl" id="dynamicUrl" value="${updateDynamic.dynamicUrl}">
					    </div>
					
					</div>
					
					<div class="form-group">
					    <label class="col-lg-1 col-lg-offset-2 control-label">描述：</label>
					    <div class=" col-lg-8">
					      	<input type="text" class="form-control" placeholder="" name="describle" id="describle" value="${updateDynamic.describle}">
					    </div>
					</div>

					<button type="button" class="btn btn-submit" onclick="sub()">确定</button>
				</form>
				
			</div>
		</div>
	</div>
	
</body>
</html>





