<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>头部</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
//注销
function logout(){
  $.get("${pageContext.request.contextPath}/logout.html",function(){
	  window.location.href="${pageContext.request.contextPath}/index.html";
  });	
}
//修改密码
function updatePwd(){
   var rtObj = window.showModalDialog('${pageContext.request.contextPath}/system/user/to_updatePwd','dialogWidth=150px;dialogHeight=600px;status=no;help=no;scrollbars=no');
}
</script>
</head>
<body style="font-size: 12px; font-family: Verdana;">
<fieldset style="height: 100px;">
  <div align="right">
  <span style="margin-right: 100px;"> 
          欢迎您:<label style="color: red;">${userName}</label>,
  	 您有未读信息<a href="javascript:void(0);"><label style="color: red;font-weight: bold;">0</label></a>条
  </span>
  <span style="margin-right: 20px;"><a href="javascript:updatePwd();">修改密码</a></span>
   <span style="margin-right: 1px;"> <a href="javascript:logout();">退出</a></span>
  </div>
</fieldset>
</body>
</html>