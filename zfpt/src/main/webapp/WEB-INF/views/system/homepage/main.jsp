<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>执法平台</title>
</head>
   <frameset rows="20%,*" cols="*" frameborder="yes" border="1" framespacing="1">
     <frame  id="topFrame" src="<%=request.getContextPath()%>/top.html"/>
     <frameset cols="15%,70%" frameborder="yes" framespacing="1">
     <frame id="menuFrame"  src="<%=request.getContextPath()%>/leftMenu.html">
     <frame id="mainFrame"  src="<%=request.getContextPath()%>/homepage.html"></frame>
<body>
</body>
</html>