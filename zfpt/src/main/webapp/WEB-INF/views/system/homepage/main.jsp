<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理平台</title>
<style type="text/css">

body{
margin: 0;
padding: 0;
border: 0;
overflow: hidden;
height: 100%; 
max-height: 100%; 
}

#framecontentLeft, #framecontentRight{
position: absolute; 
top: 0; 
left: 0; 
width: 200px; /*Width of left frame div*/
height: 100%;
overflow: hidden; /*Disable scrollbars. Set to "scroll" to enable*/
background-color: navy;
color: white;
}

#framecontentRight{
left: auto;
right: 0; 
width: 150px; /*Width of right frame div*/
overflow: hidden; /*Disable scrollbars. Set to "scroll" to enable*/
background-color: navy;
color: white;
}

#framecontentTop, #framecontentBottom{
position: absolute;
top: 0; 
left: 200px; /*Set left value to WidthOfLeftFrameDiv*/
right: 150px; /*Set right value to WidthOfRightFrameDiv*/
width: auto;
height: 120px; /*Height of top frame div*/
overflow: hidden; /*Disable scrollbars. Set to "scroll" to enable*/
background-color: navy;
color: white;
}

#framecontentBottom{
top: auto;
height: 100px; /*Height of bottom frame div*/
bottom: 0;
}

#maincontent{
position: fixed; 
top: 120px; /*Set top value to HeightOfTopFrameDiv*/
bottom: 100px; /*Set bottom value to HeightOfBottomFrameDiv*/
left: 200px; /*Set left value to WidthOfLeftFrameDiv*/
right: 150px; /*Set right value to WidthOfRightFrameDiv*/
overflow: auto; 
background: #fff;
}

.innertube{
margin: 15px; /*Margins for inner DIV inside each DIV (to provide padding)*/
}

* html body{ /*IE6 hack*/
padding: 120px 150px 100px 200px; /*Set value to (HeightOfTopFrameDiv WidthOfRightFrameDiv HeightOfBottomFrameDiv WidthOfLeftFrameDiv)*/
}

* html #maincontent{ /*IE6 hack*/
height: 100%; 
width: 100%; 
}

* html #framecontentTop, * html #framecontentBottom{ /*IE6 hack*/
width: 100%;
}
</style>
<script type="text/javascript">
/*** Temporary text filler function. Remove when deploying template. ***/
var gibberish=["This is just some filler text", "Welcome to Dynamic Drive CSS Library", "Demo content nothing to read here"]
function filltext(words){
	for (var i=0; i<words; i++)
		document.write(gibberish[Math.floor(Math.random()*3)]+" ")
}
</script>
</head>
<body>
<!-- 二级菜单 -->
<div id="framecontentLeft">
    <div class="innertube">
	    <h3>加载二级菜单</h3>
    </div>
</div>

<!-- 右侧内容页面 -->
<div id="framecontentRight">
<div class="innertube">
<h3>top.jsp</h3>
</div>
</div>

<!-- top部分 -->
<div id="framecontentTop">
 <div class="innertube">
   <h3>登录信息</h3>
 </div>
</div>

<div id="maincontent">
<div class="innertube">
<h3>加载数据</h3>
</div>
</div>


</body>
</html>