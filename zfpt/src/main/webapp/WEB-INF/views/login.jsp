<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统登录</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
document.onkeydown=function(e){ var keycode=document.all?event.keyCode:e.which; if(keycode==13)login();}
//提交登录
function login(){
   var loginName=$("#username").val();
   var passwd=$("#password").val();
   if(loginName==""||loginName==null){
	  alert("用户名不能为空!");return false; 
   }
   if(passwd==""||passwd==null){
	  alert("登录密码不能为空!");return false;  
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
<body style="font-size: 12px; font-family: Verdana;" onload="javascript:document.getElementById('username').focus();">
<fieldset style="width: 80%;">
 <legend>系统安全登录</legend>
 <div align="center">
	 <form action="" method="post">
	   <label>用户名:</label><span><input type="text" name="loginCode" id="username" /></span><br/><br/>
	   <label>密&nbsp;&nbsp;&nbsp;码:</label><span><input  type="password" name="userPassword" id="password"  /></span><br/><br/>
	   <input type="button" value="登录" onclick="javascript:login();">
	   <input type="button" value="重置" onclick="javascript:reset();">
	 </form>
 </div>
</fieldset>
</body>
</html>