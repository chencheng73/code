<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传/下载Demo</title>
<script type="text/javascript">
//文件上传
function testUpload(){
	var file1=document.getElementById("file1").value;
	var file2=document.getElementById("file2").value;
	var file3=document.getElementById("file3").value;
	if(file1==""&&file2==""&&file3==""){
	   alert("请选择要上传的附件");
	   return false;
	}else{
	  document.forms[0].action="<%=request.getContextPath()%>/file/upload";
	  document.forms[0].submit(); 	
	}
}
//文件下载
function testFileDown(){
   document.forms[0].action="<%=request.getContextPath()%>/file/down/07d28c8babb845d683ff4bd89028f4d8";
   document.forms[0].submit();
}
//导入Excel
function testImportExcel(){
   var file1=document.getElementById("file4").value;
   if(file1==""){
	  alert("请选择要导入的Excel");
	  return false;
   }else if (file1.toLowerCase().lastIndexOf(".xlsx")==-1&&file1.toLowerCase().lastIndexOf(".xls")==-1){
	  alert("请选择Excel文件");
	  return false;
   }else{
	  document.forms[1].action="<%=request.getContextPath()%>/file/import";
	  document.forms[1].submit();	
   }
}
//导出Excel
function testExportExcel(){
   document.forms[2].action="<%=request.getContextPath()%>/file/export";
   document.forms[2].submit();
}
</script>
</head>
<body>
<!-- 附件上传 -->
<fieldset>
 <legend>附件上传</legend>
 <form action="" enctype="multipart/form-data" method="POST">
   <input type="file" name="file1" id="file1" /><br/><br/>
   <input type="file" name="file2" id="file2"/><br/><br/>
   <input type="file" name="file3" id="file3"/>&nbsp;&nbsp;<input type="button" value="上传"  onclick="javascript:testUpload();"/><br/>
 </form>
</fieldset>

<!-- 附件下载 -->
<fieldset>
 <legend>附件下载</legend>
  <span><a href="javascript:testFileDown();">监管主体数据模板.xlsx<a></span>
</fieldset>

<!-- excel 导入 -->
<fieldset>
 <legend>导入Excel</legend>
  <form  action="" enctype="multipart/form-data" method="POST">
   <input type="file" name="excelData" id="file4"/>&nbsp;&nbsp;<input type="button" value="上传"  onclick="javascript:testImportExcel();"/><br/>
 </form>
</fieldset>
<!-- excel 导出 -->
<fieldset>
 <legend>导出Excel</legend>
  <form  action=""   method="get"><input type="button" value="导出测试数据"  onclick="javascript:testExportExcel();"/><br/>
 </form>
</fieldset>






</body>
</html>