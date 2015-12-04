<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en" class="app js no-touch no-android chrome no-firefox no-iemobile no-ie no-ie10 no-ie11 no-ios no-ios7 ipad">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Google Chrome Frame也可以让IE用上Chrome的引擎: -->
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<link href="/favicon.ico" type="image/x-icon" rel=icon>
<link href="/favicon.ico" type="image/x-icon" rel="shortcut icon">
<meta name="renderer" content="webkit">
<title>登录－蓝缘管理系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath }/admin_files/min.css">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath }/admin_files/login.css">
<link
	href="${pageContext.servletContext.contextPath }/admin_files/css.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
document.onkeydown=function(e){ var keycode=document.all?event.keyCode:e.which; if(keycode==13)login();}
//提交登录
function login(){
   var loginName=$("#username").val();
   var passwd=$("#password").val();
   if(loginName==""||loginName==null){
	  alert("用户名不能为空!");
	  $("#username").focus();
	  return false; 
   }
   if(passwd==""||passwd==null){
	  alert("登录密码不能为空!");
	  $("#password").focus();
	  return false;  
   } 
    $.ajax({
	    type: 'POST',
	    url: '${pageContext.request.contextPath}/login',
	    data: $("form").serialize(),
	    dataType:"json",
	    success: function(result) {
	    	if(result.status!=0){
	    	   alert(result.info);	
	    	}else{
               window.location.href="${pageContext.request.contextPath}/main.html";	//跳转到主页面    		
	    	}
	    }
	}); 	  
}
</script>	
	
	
</head>
<body onload="javascript:document.getElementById('username').focus();to_top();" 
	style="background-image: url('${pageContext.servletContext.contextPath }/admin_files/9.jpg');margin-top:0px;background-repeat: round;"">
	<div id="loginbox" style="padding-top: 10%;">
		<form id="loginform" name="loginform" class="form-vertical"
			style="background-color: rgba(0, 0, 0, 0.5) !important; background: #000; filter: alpha(opacity = 50); *background: #000; *filter: alpha(opacity = 50); /*黑色透明背景结束*/ color: #FFF; bottom: 0px; right: 0px; border: 1px solid #000;"
			action="${pageContext.servletContext.contextPath }/login.shtml"
			method="post">
			<div class="control-group normal_text">
				<table style="width: 100%">
					<tr>
						<td align="left"><img
							src="${pageContext.servletContext.contextPath }/admin_files/logo_left.png"
							alt="Logo"></td>
							<td align="center" style="font-weight: bold;color: gray;">xxxxx联合执法平台</td>
						<td align="right"><img
							src="${pageContext.servletContext.contextPath }/admin_files/logo_left.png"
							alt="Logo"></td>
					</tr>
				</table>

			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly" style="background: #28b779"><img
							src="${pageContext.servletContext.contextPath }/admin_files/account_1.png"
							alt="请输入账号.."></span><input type="text" placeholder="请输入账号.." name="loginCode" id="username" value=""
							style="height: 32px; margin-bottom: 0px;"/>
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly"><img
							src="${pageContext.servletContext.contextPath }/admin_files/lock_1.png"
							alt="请输入密码.."></span><input type="password" placeholder="请输入密码.." name="userPassword"  id="password" value=""
							style="height: 32px; margin-bottom: 0px;"/>
					</div>
				</div>
			</div>
			<div class="form-actions"> 
					 <span
					class="pull-right"><a type=""
					href="javascript:login();" class="btn btn-success">登&nbsp;&nbsp;录</a></span>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		if ("${error}" != "") {
			alert("${error}");
		};
		function checkUserForm() {
			document.loginform.submit();
		}
		function to_top(){
			if(window != top){
		        top.location.href=location.href;
		    }
		}
	</script>
</body>
</html>