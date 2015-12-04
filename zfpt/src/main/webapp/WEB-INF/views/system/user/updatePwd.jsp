<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
//提交更新
function toSave_Submit(){ 
	  var orgiPasswd=$("#orgiPasswd").val();  
	  var newPassword=$("#newPassword").val();	
	  var confirmPassword=$("#confirmPassword").val();
	  if(orgiPasswd==null||orgiPasswd==""){
		 alert("原登录密码不能为空!");
		 return false; 
	  }
	  if(newPassword==null||newPassword==""){
		 alert("新登录密码不能为空!");
		 return false; 
	  }
	  if(confirmPassword==null||confirmPassword==""){
		 alert("确认密码不能为空!");
		 return false; 
	  }
	  if(newPassword!=confirmPassword){
		 alert("两次密码输入不一致，请核对"); 
         return false;
	  }
	   $.ajax({
		    type: 'post',
		    url: '${pageContext.request.contextPath}/system/user/updatePwd',
		    data: $("#editForm").serialize(),
		    dataType:"json",
		    success: function(data) {
		    	 if(data.updateCount<0){
		    		alert(data.errorMessage);
		    		return false;
		    	 }else{
		    		alert(data.info);
		    		cancel();
		    	 }
		    }
		});
}

//取消
function cancel(){
	window.close();
}
</script>
</head>
<body  style="font-size: 12px;font-family: Verdana;color: rgb(177, 106, 104);">
 <fieldset>
 <div align="center">
  <legend>修改密码</legend>
  <form id="editForm" name="editForm" class="form-horizontal" method="post" action="">	 
    <input type="hidden" name="id" id="e_ditId">
    <label class="col-sm-3 control-label">原登录密码</label><input type="password" class="form-control checkacc" placeholder="请输入密码" name="orgiPasswd" id="orgiPasswd" value=""/><br/><br/><br/>
    <label class="col-sm-3 control-label">新登录密码</label><input type="password" class="form-control checkacc" placeholder="请输入密码" name="newPassword" id="newPassword" value=""/><br/><br/><br/>
    <label class="col-sm-3 control-label">重复新密码</label><input type="password" class="form-control checkacc" placeholder="请输入密码" name="confirmPassword" id="confirmPassword" value=""/><br/><br/><br/>
    <button type="button" class="btn btn-success btn-s-xs" onclick="javascript:toSave_Submit();">保存</button>
    <button type="button" class="btn btn-success btn-s-xs" onclick="javascript:cancel();">取消</button>
 </form>
 </div>
</fieldset>
</body>
</html>