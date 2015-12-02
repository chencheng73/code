<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
$(function(){
	loadData();
})

//加载角色列表数据
function loadData(){
	  var listHtml=new Array();
	  listHtml.push("<tr>");
      listHtml.push("<td>序号</td>");
      listHtml.push("<td>角色id</td>");
      listHtml.push("<td>角色标识</td>");
      listHtml.push("<td>角色名称</td>");
      listHtml.push("<td>角色描述</td>");
      listHtml.push("<td>状态</td>");
      listHtml.push("<td>操作</td>");
      listHtml.push("<tr>");
	$.getJSON(
	 "${pageContext.request.contextPath}/system/role/listPage",
	 {'pageNo':1,'pageSize':15},
	  function(datas){  
		if(datas.totalCount){
			var listResult=datas.data;
            for(var i in listResult){
               listHtml.push("<tr>");
               listHtml.push("<td>"+(parseInt(i)+1)+"</td>");
               listHtml.push("<td>"+listResult[i].id+"</td>");
               listHtml.push("<td>"+listResult[i].roleCode+"</td>");
               listHtml.push("<td>"+listResult[i].roleName+"</td>");
               listHtml.push("<td>"+listResult[i].description+"</td>");
               listHtml.push("<td>"+listResult[i].status+"</td>");
               listHtml.push("<td>[<a href=\"javascript:editRole('"+listResult[i].id+"')\">编辑</a>][<a href=\"javascript:deleteRole('"+listResult[i].id+"')\">删除</a>][<a href=\"javascript:discResource('"+listResult[i].id+"')\">分配资源</a>]</td>");
               listHtml.push("<tr>");
            }
            $("#table-1").empty().append(listHtml.join(""));
		}
	 }
	);
}
 
//加载明细,进入编辑
function editRole(roleId){
	$("#addSpan").hide();
	$("#editSpan").show();
    $.getJSON(
      "${pageContext.request.contextPath}/system/role/get/"+roleId,
      function(result){
    	 var resultObject=result.data; 
    	  if(resultObject!=null){
    		 $("#editId").val(roleId);  	
             $("#editRoleCode").val(resultObject.roleCode);  		  
             $("#editRoleName").val(resultObject.roleName);  	
             $("#editDescription").val(resultObject.description);  	
    	  }
      }
    );	
}

//提交保存
function toSave_Submit(){
   var roleName=$("#roleName").val();
   if(roleName==""||roleName==null){
	  alert("角色名称不能为空");
	  return false;
   }
   $.ajax({
	    type: 'post',
	    url: '${pageContext.request.contextPath}/system/role/save',
	    data: $("#addForm").serialize(),
	    dataType:"json",
	    success: function(data) {
	    	 if(data.updateCount<0){
	    		alert(data.errorMessage);
	    		return false;
	    	 }else{
	    		alert(data.info);
	    		loadData();
	    	 }
	    }
	});
}

//编辑提交
function toEdit_Submit(){
   var roleName=$("#editRoleName").val();
   if(roleName==""||roleName==null){
	  alert("角色名称不能为空");
	  return false;
   }
   $.ajax({
	    type: 'post',
	    url: '${pageContext.request.contextPath}/system/role/update',
	    data: $("#editForm").serialize(),
	    dataType:"json",
	    success: function(data) {
	    	 if(data.updateCount<0){
	    		alert(data.errorMessage);
	    		return false;
	    	 }else{
	    		alert(data.info);
	    		cancel();
	    		loadData();
	    	 }
	    }
	});
}

//删除角色信息
function deleteRole(roleId){
   if(confirm("您确定要删除该角色吗?")){
	  $.post(
		"${pageContext.request.contextPath}/system/role/delete",
		    {id:roleId},function(result){
		    	var resultObject=eval('('+result+')');
		    	if(resultObject.updateCount>0){
		    	   alert(resultObject.info);	
		    	   cancel();
		    	   loadData();
		    	}else{
		    	   alert(resultObject.errorMessage);	
		    	}
		}
	  ); 
   } 	
}


//授权角色
function discResource(roleId){
    var rtObj = window.showModalDialog('${pageContext.request.contextPath}/system/role/to_ResourceList?roleid='+roleId,'dialogWidth=150px;dialogHeight=600px;status=no;help=no;scrollbars=no');
    if(rtObj!=null){
    	loadData(); 
    }
 }

//取消编辑操作/隐藏编辑面板，显示新增面板
function cancel(){
   $("#addSpan").show();
   $("#editSpan").hide();
}
</script>
<style>
 #table-1 thead, #table-1 tr {
border-top-width: 1px;
border-top-style: solid;
border-top-color: rgb(230, 189, 189);
}
#table-1 {
border-bottom-width: 1px;
border-bottom-style: solid;
border-bottom-color: rgb(230, 189, 189);
}

/* Padding and font style */
#table-1 td, #table-1 th {
padding: 5px 10px;
font-size: 12px;
font-family: Verdana;
color: rgb(177, 106, 104);
}

/* Alternating background colors */
#table-1 tr:nth-child(even) {
background: rgb(238, 211, 210)
}
#table-1 tr:nth-child(odd) {
background: #FFF
}

</style>
<title>测试角色信息</title>
</head>
<body style="font-size: 12px;font-family: Verdana;color: rgb(177, 106, 104);">
<span id="addSpan">
<fieldset>
 <legend>新增角色</legend>
 <form id="addForm" name="addForm" class="form-horizontal" method="post" action="">	 
   <label class="control-label">角色标识</label><input type="text" class="form-control"  placeholder="角色标识" name="roleCode" id="roleCode"/>
   <label class="col-sm-3 control-label">角色名称</label><input type="text" class="form-control checkacc" placeholder="角色名称" name="roleName" id="roleName">
    <label class="col-sm-3 control-label">角色描述</label><input type="text" class="form-control checkacc" placeholder="角色描述" name="description" id="description">
   <label class="col-sm-3 control-label">是否启用</label>	<input type="radio" name="status" value='0' checked="checked">是 <input type="radio" name="status" value='1'>否
    <button type="button" class="btn btn-success btn-s-xs" onclick="javascript:toSave_Submit();">保存</button>
 </form>
</fieldset>
</span>
<span id="editSpan" style="display:none;">
 <fieldset>
   <legend>修改角色</legend>
  <form id="editForm" name="editForm" class="form-horizontal" method="post" action="">	 
   <input type="hidden" name="id" id="editId">
   <label class="control-label">角色标识</label><input type="text" class="form-control"  placeholder="角色标识" name="roleCode"   id="editRoleCode"/>
   <label class="col-sm-3 control-label">角色名称</label><input type="text" class="form-control checkacc" placeholder="角色名称" name="roleName" id="editRoleName">
    <label class="col-sm-3 control-label">角色描述</label><input type="text" class="form-control checkacc" placeholder="角色描述" name="description" id="editDescription">
    <button type="button" class="btn btn-success btn-s-xs" onclick="javascript:toEdit_Submit();">保存</button>
    <button type="button" class="btn btn-success btn-s-xs" onclick="javascript:canel();">取消</button>
  </form>
 </fieldset>
</span>

<fieldset>
 <legend>用户列表</legend>
 <form id="form1" name="form1" class="form-horizontal" method="post" action="">	 
    <table id="table-1" width="70%">
     <tr>
       <td>序号</td>
       <td>角色id</td>
       <td>角色标识</td>
       <td>角色名称</td>
       <td>角色描述</td>
       <td>状态</td>
     </tr>
    </table>
 </form>
</fieldset>
</body>
</html>