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
		<title>模板修改-素材库</title>


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
	function checkTemplateIntf(str){
		$("#spaName").html("");
		var templateIntf = $("#templateIntf").val();//id为username的文本框的值
		if(str!=templateIntf){
			 $.ajax(  
              {  
                   type:"post",  
                   url:"template/checkTemplateIntf.do",  
                   data:"templateIntf="+$("#templateIntf").val(),  
                   dataType:"json",  
                   success:function(data)  
                    {  
                        if(data.result=="0"){ 
                            $("#spaName").html("<font color=green>可以使用</font>");  
                        }else if(data.result=="11"){
                        	$("#spaName").html("<font color=red>长度不合法</font>");  
                            $("#templateIntf").focus(); 
                           	return false;
                        }else if(data.result=="9"){                       
                            $("#spaName").html("<font color=red>不能为空</font>");  
                            $("#templateIntf").focus(); 
                           	return false;
                        }else if(data.result=="3"){
                         	$("#spaName").html("<font color=red>不合法编号</font>");  
                            $("#templateIntf").focus(); 
                           	return false;
                        }else if(data.result=="8"){
                         	$("#spaName").html("<font color=red>该编号已被占用</font>");  
                            $("#templateIntf").focus(); 
                           	return false;
                        }else{
                        	$("#spaName").html("<font color=red>不可使用</font>");  
                            $("#templateIntf").focus(); 
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

		var templateIntf=$("#templateIntf").val()
		
		var style = $("#style").val();

		var industry = $("#industry").val();

		var color = $("#color").val();

		var designer = $("#designer").val();

		var siteUrl = $("#templateUrl").val();
		
		var urlHead = siteUrl.substr(0,11);
		
		var urlHttp  = siteUrl.substr(0,7);

		var reg = /^[A-Za-z ]*$/ ;
		
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
		if(urlHead!="http://www."&&urlHttp!="http://"){
			alert("案例链接必须是http://www.或http://格式");
			return false;
		}
		if(templateIntf==""){
			alert("编号不能为空！");
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
			if(confirm("即将覆盖所有的图片，是否继续？")){
				if(subForm()){
					document.templateForm.submit();
				}
			}
		}else{
			if(subForm()){
					document.templateForm.submit();
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
	
	function delStyle(){
   		var $select = $("#style");//designer下拉框Id
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
	
	function delColor(){
   		var $select = $("#color");//designer下拉框Id
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
	function delDesigner(){
   		var $select = $("#designer");//designer下拉框Id
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
	
	function delIndustry(){
   		var $select = $("#designer");//designer下拉框Id
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
					 模板修改
					<a href="site/findAllTemplate.do" class='back pull-right'>返回首页</a>
				</div>
			</div>
			
			<div class="panel-body">
				<form action="template/updateTemplate.do" class="form-horizontal" id="templateForm" name ="templateForm" enctype="multipart/form-data" method="post">
					<div class="form-group">
					    <button class="btn btn-upload" type="button" onclick="window.location=('/spring_mybatis/template/findUpdateTemplate.do')">返回</button>
					</div> 
					
					<input type="hidden" name="templateNumber" value="${updateTemplate.templateNumber}"/>
					<input type="hidden" name="picture" value="${updateTemplate.picture}"/>
					
					<div class="form-group">
					 	<label class="col-lg-1 col-lg-offset-2 control-label">修改图片：</label>
					    <div class=" col-lg-5">
					     	<input type="file" name="file" id="file" class="btn btn-upload" multiple="multiple"/>
					 	</div>
					 	<div class="col-lg-2 form-label">
					    	*可选择多张图片*
					    </div>
					</div>
					<div class="form-group">
					 	<label class="col-lg-1 col-lg-offset-2 control-label">编号：</label>
					    <div class=" col-lg-5">
					    	<input type="text" class="form-control" placeholder="" name="templateIntf" id="templateIntf"  onblur="checkTemplateIntf('${updateTemplate.templateIntf}')" value="${updateTemplate.templateIntf}">
					    	<span id="spaName"></span>
					 	</div>
					 	<div class="col-lg-2 form-label">
					    		*长度3-10的字母或数字*
					    </div> 
					</div>
					<div class="form-group">
					    <label class="col-lg-1 col-lg-offset-2 control-label">风格：</label>
					    <div class=" col-lg-5">
					      	<!--<input type="text" class="form-control" placeholder="" name="style" id="style"> -->
					      	<select class="form-control" placeholder="" name="style" id="style" onclick="delStyle()">
					      		<option selected="selected" value="${updateTemplate.style}">${updateTemplate.style}</option>
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
					    <label class="col-lg-1 col-lg-offset-2 control-label">行业：</label>
					    <div class=" col-lg-5">
					    <!--<input type="text" class="form-control" placeholder="" name="industry" id="industry">-->
					      	<select class="form-control" placeholder="" name="industry" id="industry" onclick="delIndustry()">
					      		<option selected="selected" value="${updateTemplate.industry}">${updateTemplate.industry}</option>
					      		<option value=" 金融"> 金融</option>
					      		<option value="工程制造">工程制造</option>
					      		<option value="政府协会">政府协会</option>
					      		<option value="IT科技">IT科技</option>
					      		<option value="科教文娱">科教文娱</option>
					      		<option value="其他">其他</option>
					      	</select>
					    </div>
					</div>
					<div class="form-group">
					    <label class="col-lg-1 col-lg-offset-2 control-label">颜色：</label>
					    <div class=" col-lg-5">
					    	<select class="form-control" placeholder="" name="color" id="color" onclick="delColor()">
					      	<c:choose>
					    		<c:when test="${updateTemplate.color=='white'}">
					    			<option value="${updateTemplate.color}" selected="selected">白</option>
					    		</c:when>
					    		<c:when test="${updateTemplate.color=='black'}">
					    			<option value="${updateTemplate.color}" selected="selected">黑</option>
					    		</c:when>
					    		<c:when test="${updateTemplate.color=='green'}">
					    			<option value="${updateTemplate.color}" selected="selected">绿</option>
					    		</c:when>
					    		<c:when test="${updateTemplate.color=='red'}">
					    			<option value="${updateTemplate.color}" selected="selected">红</option>
					    		</c:when>
					    		<c:when test="${updateTemplate.color=='blue'}">
					    			<option value="${updateTemplate.color}" selected="selected">蓝</option>
					    		</c:when>
					    		<c:when test="${updateTemplate.color=='orange'}">
					    			<option value="${updateTemplate.color}" selected="selected">橙</option>
					    		</c:when>
					    		<c:when test="${updateTemplate.color=='purple'}">
					    			<option value="${updateTemplate.color}" selected="selected">紫</option>
					    		</c:when>					    		
					    		<c:when test="${updateTemplate.color=='yellow'}">
					    			<option value="${updateTemplate.color}" selected="selected">黄</option>
					    		</c:when>					    		
					    		<c:when test="${updateTemplate.color=='gray'}">
					    			<option value="${updateTemplate.color}" selected="selected">其他</option>
					    		</c:when>
					    	</c:choose>	
					      		<option value="white"> 白</option>
					      		<option value="black">黑</option>
					      		<option value="green">绿</option>
					      		<option value="red">红</option>
					      		<option value="blue">蓝</option>
					      		<option value="orange">橙</option>
					      		<option value="purple ">紫</option>
					      		<option value="yellow">黄</option>
					      		<option value="gray">其他</option>
					      	</select>
					      	<!--<input type="text" class="form-control" placeholder="" name="color" id="color">-->
					     </div>
					    <!--<div class="col-lg-2 form-label">
					    	*英文书写*
					    </div>-->
					</div>

					<div class="form-group">
					    <label class="col-lg-1 col-lg-offset-2 control-label">设计师：</label>
					    <div class=" col-lg-5">
					      	<!--<input type="text" class="form-control" placeholder="" name="designer" id="designer" value="${user.realName}">-->
					      	<select class="form-control" placeholder="" name="designer" id="designer" onclick="delDesigner()">
					      		<option selected="selected" value="${updateTemplate.designer}">${updateTemplate.designer}</option>
					      		<c:forEach items="${userList}" var="userList">
					      		<option value="${userList.realName}">${userList.realName}</option>
					      		</c:forEach>
					      	</select>
					    </div>
					</div>
					
					<div class="form-group">
					    <label class="col-lg-1 col-lg-offset-2 control-label">案例链接：</label>
					    <div class=" col-lg-5">
					      	<input type="text" class="form-control" placeholder="" name="templateUrl" id="templateUrl" value="${updateTemplate.templateUrl}">
					    </div>
					
					</div>
					
					<div class="form-group">
					    <label class="col-lg-1 col-lg-offset-2 control-label">描述：</label>
					    <div class=" col-lg-8">
					      	<input type="text" class="form-control" placeholder="" name="describe" id="describe" value="${updateTemplate.describe}">
					    </div>
					</div>
					

					
					<button type="button" class="btn btn-submit" onclick="sub()">确定</button>
				</form>
				
			</div>
		</div>
	</div>
	
</body>
</html>





