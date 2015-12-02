<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
 //资源id
 var resId="${resource.id}";
 
 //级别
 var level="${resource.level}";
 
 //显示新增
 function toAdd(){
	 $("#addSpan").show();
     $("#showSpan").hide();
     $("#editSpan").hide();
 }
 
 //显示修改
 function toEdit(){
	 $("#editSpan").show();
	 $("#addSpan").hide();
     $("#showSpan").hide();
 }
 
 //取消操作
 function to_cancel(){  
	$("#showSpan").show(); 
	$("#addSpan").hide();
    $("#editSpan").hide();
 }
 
 
 //提交新增
 function toSave_Submit(){
	if(parseInt(level)>2){
	   alert("该节点上不能挂载菜单");
	   return false;
	}else{
	   $("#level").val(parseInt(level)+1);
	} 
	var resName=$("#resName").val();
	if(resName==""||resName==null){
	   alert("资源名称不能为空!");return false;	
	}
    $.ajax({
	    type: 'post',
	    url: '${pageContext.request.contextPath}/system/resource/save',
	    data: $("#addForm").serialize(),
	    dataType:"json",
	    success: function(result) {
	    	 if(result.updateCount>0){  
	    		alert(result.info);
	    		reload(resId);
	    	 }else{
	    		alert(result.errorMessage);
	    		return false;
	    	 }
	    }
	}); 
 }
 
 //提交修改
 function toEdit_Submit(){
	var rid=$("#id").val();
	var resName=$("#e_resName").val();
	if(resName==""||resName==null){
	   alert("资源名称不能为空!");return false;	
	}
    $.ajax({
	    type: 'post',
	    url: '${pageContext.request.contextPath}/system/resource/update',
	    data: $("#editForm").serialize(),
	    dataType:"json",
	    success: function(result) {
	    	 if(result.updateCount>0){  
	    		alert(result.info);
	    		reload(rid);
	    	 }else{
	    		alert(result.errorMessage);
	    		return false;
	    	 }
	    }
	}); 
  }
 
 //删除
 function toDelete(){
	if(resId!=null&&resId!=''){
	   if(confirm("您确定要删除该资源吗?")){
		  if(resId=='0'){
			 alert("根节点不允许删除!");
			 return false;
		  }		   
		  $.post(
			"${pageContext.request.contextPath}/system/resource/delete",
			    {id:resId},function(result){
			    	var resultObject=eval('('+result+')');
			    	if(resultObject.updateCount>0){
			    	   alert(resultObject.info);	
			    	   reload(0);
			    	}else{
			    	   alert(resultObject.errorMessage);	
			    	}
			}
		  ); 
	   } 	
	}else{
		alert("没有要删除的资源");
		return false
	}
 }
 
 
 //页面刷新
 function reload(rid){
    parent.document.getElementById("leftMain").src="<%=request.getContextPath()%>/system/resource/tree";
    parent.document.getElementById("rightMain").src="<%=request.getContextPath()%>/system/resource/to_add?resId="+rid; 
 }
</script>
</head>
<body style="font-size: 12px; font-family: Verdana;">
<span id="showSpan" >
  <fieldset>
   <legend style="font-weight: bold;">查看菜单明细</legend>
    <label>资源名ID：</label> ${resource.id} <br/> <br/>   
    <label>资源级别：</label> ${resource.level}<br/><br/>  
    <label>资源名称：</label> ${resource.resName}<br/><br/>  
    <label>访问URL：</label> ${resource.resUrl}<br/><br/>   
    <label>描述：</label> ${resource.description}<br/><br/>  
    <label>创建时间：</label> ${resource.createAt}<br/><br/>
  </fieldset>
</span>

<span id="addSpan" style="display: none;" >
  <fieldset >
   <legend style="font-weight: bold;">新增菜单信息</legend>
  <form  id="addForm" name="addForm" action="#">
    <input type="hidden" id="level" name="level" />
    <label>上级资源ID：</label><input type="hidden" id="parentId" name="parentId" value="${resource.id}">${resource.id}  <br/> <br/>   
    <label>上级资源名称：</label> ${resource.resName}<br/><br/>  
    <label>资源名称：</label> <input type="text" name="resName" id="resName"><br/><br/>  
    <label>资源标识：</label>  <input type="text" name="resKey"><br/><br/>   
    <label>访问URL：</label>  <input type="text" name="resUrl"><br/><br/>  
    <label>资源描述：</label> <textarea rows="3" cols="17" name="description"></textarea><br/><br/>
    <input type="button" value="保存" onclick="toSave_Submit();">
    <input type="button" value="取消" onclick="to_cancel();">
   </form>  
  </fieldset>
</span>

<span id="editSpan" style="display: none;" >
  <fieldset >
   <legend style="font-weight: bold;">编辑菜单信息</legend>
  <form id="editForm" name="editForm" action="#">
    <input type="hidden" id="level" name="level" value="${resource.level}"/>
    <label>资源ID：</label><input type="text" id="id" name="id" value="${resource.id}" readonly="readonly"><br/> <br/>   
    <label>资源标识：</label>  <input type="text" name="resKey" value="${resource.resKey}"><br/><br/>   
    <label>资源名称：</label> <input type="text" name="resName" id="e_resName" value="${resource.resName}"><br/><br/>  
    <label>访问URL：</label>  <input type="text" name="resUrl" value="${resource.resUrl}"><br/><br/>  
    <label>资源描述：</label> <textarea rows="3" cols="17" name="description">${resource.description}</textarea><br/><br/>
     <input type="button" value="保存" onclick="toEdit_Submit();">
     <input type="button" value="取消" onclick="to_cancel();">
   </form>  
  </fieldset>
 

</span>
  <input type="button" value="新增" onclick="toAdd();">
  <input type="button" value="修改" onclick="toEdit();">
  <input type="button" value="删除" onclick="toDelete();">
</body>
</html>