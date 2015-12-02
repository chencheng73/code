<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
 //部门id
 var resId="${department.id}";
 
 //级别
 var level="${department.level}";
 
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
   $("#level").val(parseInt(level)+1);
	var resName=$("#name").val();
	if(resName==""||resName==null){
	   alert("部门名称不能为空!");return false;	
	}
    $.ajax({
	    type: 'post',
	    url: '${pageContext.request.contextPath}/system/department/save',
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
	var resName=$("#e_name").val();
	if(resName==""||resName==null){
	   alert("部门名称不能为空!");return false;	
	}
    $.ajax({
	    type: 'post',
	    url: '${pageContext.request.contextPath}/system/department/update',
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
	   if(confirm("您确定要删除该部门吗?")){
		  if(resId=='0'){
			 alert("根节点不允许删除!");
			 return false;
		  }		   
		  $.post(
			"${pageContext.request.contextPath}/system/department/delete",
			    {id:resId},function(result){
			    	var resultObject=eval('('+result+')');
			    	if(resultObject.updateCount>0){
			    	   alert(resultObject.info);	
			    	   reload(1);
			    	}else{
			    	   alert(resultObject.errorMessage);	
			    	}
			}
		  ); 
	   } 	
	}else{
		alert("没有要删除的部门");
		return false
	}
 }
 
 
 //页面刷新
 function reload(rid){
    parent.document.getElementById("leftMain").src="<%=request.getContextPath()%>/system/department/tree";
    parent.document.getElementById("rightMain").src="<%=request.getContextPath()%>/system/department/to_add?deptId="+rid; 
 }
</script>
</head>
<body style="font-size: 12px; font-family: Verdana;">
<span id="showSpan" >
  <fieldset>
   <legend style="font-weight: bold;">查看部门明细</legend>
    <label>部门名ID：</label> ${department.id} <br/> <br/>   
    <label>部门级别：</label> ${department.level}<br/><br/>  
    <label>部门名称：</label> ${department.name}<br/><br/>  
    <label>描述：</label> ${department.description}<br/><br/>  
    <label>创建时间：</label> ${department.createAt}<br/><br/>
  </fieldset>
</span>

<span id="addSpan" style="display: none;" >
  <fieldset >
   <legend style="font-weight: bold;">新增部门信息</legend>
  <form  id="addForm" name="addForm" action="#">
    <input type="hidden" id="level" name="level" />
    <label>上级部门ID：</label><input type="hidden" id="parentId" name="parentId" value="${department.id}">${department.id}  <br/> <br/>   
    <label>上级部门名称：</label> ${department.name}<br/><br/>  
    <label>部门名称：</label> <input type="text" name="name" id="name"><br/><br/>  
    <label>部门标识：</label>  <input type="text" name="code" id="code"><br/><br/>   
    <label>部门描述：</label> <textarea rows="3" cols="17" name="description"></textarea><br/><br/>
    <input type="button" value="保存" onclick="toSave_Submit();">
    <input type="button" value="取消" onclick="to_cancel();">
   </form>  
  </fieldset>
</span>

<span id="editSpan" style="display: none;" >
  <fieldset >
   <legend style="font-weight: bold;">编辑部门信息</legend>
  <form id="editForm" name="editForm" action="#">
    <input type="hidden" id="level" name="level" value="${department.level}"/>
    <label>部门ID：</label><input type="text" id="id" name="id" value="${department.id}" readonly="readonly"><br/> <br/>   
    <label>部门编码：</label>  <input type="text" name="code" id="e_code" value="${department.code}"><br/><br/>   
    <label>部门名称：</label> <input type="text" name="name" id="e_name" value="${department.name}"><br/><br/>  
    <label>部门描述：</label> <textarea rows="3" cols="17" name="description">${department.description}</textarea><br/><br/>
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