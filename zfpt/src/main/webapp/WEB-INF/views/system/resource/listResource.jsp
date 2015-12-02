<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试菜单信息</title>
</head>
<body>
<iframe name="left" id="leftMain"  src="<%=request.getContextPath()%>/system/resource/tree" frameborder="false" scrolling="auto" border=1 width="20%" height="600"  ></iframe>
<iframe name="right" id="rightMain" src="<%=request.getContextPath()%>/system/resource/to_add?resId=0" frameborder="false" scrolling="auto" style="border:none;" width="65%" height="600" ></iframe>
</body>
</html>