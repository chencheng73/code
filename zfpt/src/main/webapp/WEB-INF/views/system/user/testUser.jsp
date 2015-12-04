<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript">var basePath="${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.excheck-3.5.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/loadDeptTree.js"></script>
<script type="text/javascript">
$(function(){
	loadData();
})
function loadData(){
	var listHtml=new Array();
	  listHtml.push("<tr>");
      listHtml.push("<td>序号</td>");
      listHtml.push("<td>用户id</td>");
      listHtml.push("<td>登录账号</td>");
      listHtml.push("<td>用户姓名</td>");
      listHtml.push("<td>电子邮箱</td>");
      listHtml.push("<td>手机号码</td>");
      listHtml.push("<td>联系电话</td>");
      listHtml.push("<td>状态</td>");
      listHtml.push("<td>操作</td>");
      listHtml.push("<tr>");
	$.getJSON(
	 "${pageContext.request.contextPath}/system/user/listPage",
	 {'pageNo':1,'pageSize':15,sort:'asc','orderBy':'loginCode'},
	  function(datas){  
		if(datas.totalCount){
			var listResult=datas.data;
            for(var i in listResult){
               listHtml.push("<tr>");
               listHtml.push("<td>"+(parseInt(i)+1)+"</td>");
               listHtml.push("<td>"+listResult[i].id+"</td>");
               listHtml.push("<td>"+listResult[i].loginCode+"</td>");
               listHtml.push("<td>"+listResult[i].userName+"</td>");
               listHtml.push("<td>"+listResult[i].email+"</td>");
               listHtml.push("<td>"+listResult[i].mobile+"</td>");
               listHtml.push("<td>"+listResult[i].phone+"</td>");
               listHtml.push("<td>"+listResult[i].status+"</td>");
               listHtml.push("<td>[<a href=\"javascript:editUser('"+listResult[i].id+"')\">编辑</a>][<a href=\"javascript:deleteUser('"+listResult[i].id+"')\">删除</a>][<a href=\"javascript:discRole('"+listResult[i].id+"')\">分配角色</a>]</td>");
               listHtml.push("<tr>");
            }
            $("#table-1").empty().append(listHtml.join(""));
		}
	 }
	);
}


//加载明细,进入编辑
function editUser(userId){
	$("#addSpan").hide();
	$("#editSpan").show();
    $.getJSON(
      "${pageContext.request.contextPath}/system/user/get/"+userId,
      function(result){
    	 var resultObject=result.data; 
    	  if(resultObject!=null){
    		 $("#e_ditId").val(userId);  	
             $("#e_loginCode").val(resultObject.loginCode);  		  
             $("#e_userName").val(resultObject.userName);  
             $("#e_userPassword").val(resultObject.userPassword);  	
             $("#e_email").val(resultObject.email);
             $("#e_mobile").val(resultObject.mobile); 
    	  }
      }
    );	
}
 

//提交保存
function toSave_Submit(){
  var userName=$("#userName").val();
  var loginCode=$("#loginCode").val();	
  var userPassword=$("#userPassword").val();
  if(userName==null||userName==""){
	 alert("用户名不能为空!");
	 return false; 
  }
  if(loginCode==null||loginCode==""){
	 alert("登录账号不能为空!");
	 return false; 
  }
  if(userPassword==null||userPassword==""){
	 alert("登录密码不能为空!");
	 return false; 
  }
   $.ajax({
	    type: 'post',
	    url: '${pageContext.request.contextPath}/system/user/save',
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

//提交修改
function toEdit_Submit(){
   var userName=$("#e_userName").val();
   var loginCode=$("#e_loginCode").val();	
  if(userName==null||userName==""){
	 alert("用户名不能为空!");
	 return false; 
  }
  if(loginCode==null||loginCode==""){
	 alert("登录账号不能为空!");
	 return false; 
  }
   $.ajax({
	    type: 'post',
	    url: '${pageContext.request.contextPath}/system/user/update',
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

//删除用户信息
function deleteUser(userId){
   if(confirm("您确定要删除该用户吗?")){
	  $.post(
		"${pageContext.request.contextPath}/system/user/delete",
		    {id:userId},function(result){
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
function discRole(userid){
   var rtObj = window.showModalDialog('${pageContext.request.contextPath}/system/user/to_roleList?userid='+userid,'dialogWidth=400px;dialogHeight=400px;status=no;help=no;scrollbars=no');
   if(rtObj!=null){
      loadData(); 
   }
}

//取消编辑操作/隐藏编辑面板，显示新增面板
function cancel(){
   $("#addSpan").show();
   $("#editSpan").hide();
   $(".input").val("");
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
<title>测试用户信息</title>
</head>
<body style="font-size: 12px;font-family: Verdana;color: rgb(177, 106, 104);">

<span id="addSpan">
 <fieldset>
  <legend>新增用户</legend>
  <form id="addForm" name="addForm" class="form-horizontal" method="post" action="">	 
    <label class="control-label">用户名</label><input type="text" class="form-control"  placeholder="请输入用户名" name="userName" id="userName"/>
    <label class="col-sm-3 control-label">登录账号</label><input type="text" class="form-control checkacc" placeholder="请输入账号" name="loginCode" id="loginCode">
    <label class="col-sm-3 control-label">登录密码</label><input type="password" class="form-control checkacc" placeholder="请输入密码" name="userPassword" id="userPassword"><br/><br/>
    <label class="col-sm-3 control-label">邮&nbsp;&nbsp; 箱</label><input type="text" class="form-control checkacc" placeholder="请输入邮箱" name="email" id="email">
    <label class="col-sm-3 control-label">手机号码</label><input type="text" class="form-control checkacc" placeholder="手机号码" name="mobile" id="mobile">	
    <label class="col-sm-3 control-label">是否启用</label>	<input type="radio" name="status" value='0' checked="checked">是 <input type="radio" name="status" value='1'>否<br/><br/>
    <label class="col-sm-3 control-label">部 门名称</label> 
    <input id="deptId" name="deptmentId"  type="hidden"    readonly/>
    <input id="deptName"  type="text"   style="width:160px;" onclick="showMenu('deptId','deptName');" readonly/>
<!--  <a id="menuBtn" href="#" onclick="showMenu(); return false;">选择部门</a> -->
    <button type="button" class="btn btn-success btn-s-xs" onclick="javascript:toSave_Submit();">提交</button>
 </form>
</fieldset>
</span>

<span id="editSpan" style="display: none;">
 <fieldset>
  <legend>编辑用户</legend>
  <form id="editForm" name="editForm" class="form-horizontal" method="post" action="">	 
    <input type="hidden" name="id" id="e_ditId">
    <label class="control-label">用户名</label><input type="text" class="form-control"  placeholder="请输入用户名" name="userName" id="e_userName"/>
    <label class="col-sm-3 control-label">登录账号</label><input type="text" class="form-control checkacc" placeholder="请输入账号" name="loginCode" id="e_loginCode">
    <label class="col-sm-3 control-label">登录密码</label><input type="password" class="form-control checkacc" placeholder="请输入密码" name="userPassword" id="e_userPassword" value="123456"/>
    <label class="col-sm-3 control-label">邮箱</label><input type="text" class="form-control checkacc" placeholder="请输入邮箱" name="email" id="e_email">
    <label class="col-sm-3 control-label">手机号码</label><input type="text" class="form-control checkacc" placeholder="手机号码" name="mobile" id="e_mobile">	
    <button type="button" class="btn btn-success btn-s-xs" onclick="javascript:toEdit_Submit();">保存</button>
    <button type="button" class="btn btn-success btn-s-xs" onclick="javascript:cancel();">取消</button>
 </form>
</fieldset>
</span>
<fieldset>
 <legend>用户列表</legend>
 <form id="form1" name="form1" class="form-horizontal" method="post" action="">	 
    <table id="table-1" width="70%">
     <tr>
       <td>序号</td>
       <td>用户id</td>
       <td>登录账号</td>
       <td>用户姓名</td>
       <td>电子邮箱</td>
       <td>手机号码</td>
       <td>联系电话</td>
       <td>状态</td>
     </tr>
     <div id="listDiv">
     </div>
    </table>
 </form>
</fieldset>

<!-- 显示组织tree -->
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="deptTree" class="ztree" style="margin-top: 10px;border: 1px solid #617775;background: #f0f6e4; overflow-y:scroll;overflow-x:auto;margin-top:0; width:180px; height: 300px;"></ul>
</div>
</body>
</html>