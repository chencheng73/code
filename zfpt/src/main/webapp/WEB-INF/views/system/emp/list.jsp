<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>测试员工信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function(){
		loadData();
	});
	
	function loadData(){
	var listHtml=new Array();
	  listHtml.push("<tr>");
      listHtml.push("<td>序号</td>");
      listHtml.push("<td>员工工号</td>");
      listHtml.push("<td>姓名</td>");
      listHtml.push("<td>性别</td>");
      listHtml.push("<td>电话</td>");
      listHtml.push("<td>电子邮件</td>");
      listHtml.push("<td>地址</td>");
      listHtml.push("<td>出生日期</td>");
      listHtml.push("<td>雇佣日期</td>");
      listHtml.push("<td>离职日期</td>");
      listHtml.push("<td>是否有效</td>");
      listHtml.push("<td>操作</td>");
      listHtml.push("<tr>");
	$.getJSON(
	 "${pageContext.request.contextPath}/system/emp/listPage",
	 {'pageNo':1,'pageSize':15},
	  function(datas){  
		if(datas.totalCount){
			var listResult=datas.data;
            for(var i in listResult){
               listHtml.push("<tr>");
               listHtml.push("<td>"+(parseInt(i)+1)+"</td>");
               listHtml.push("<td>"+listResult[i].empployeeId+"</td>");
               listHtml.push("<td>"+listResult[i].name+"</td>");
               listHtml.push("<td>"+listResult[i].gender+"</td>");
               listHtml.push("<td>"+listResult[i].tele+"</td>");
               listHtml.push("<td>"+listResult[i].email+"</td>");
               listHtml.push("<td>"+listResult[i].address+"</td>");
               listHtml.push("<td>"+listResult[i].birthday+"</td>");
               listHtml.push("<td>"+listResult[i].hireDate+"</td>");
               listHtml.push("<td>"+listResult[i].leavingDate+"</td>");
               listHtml.push("<td>"+listResult[i].isDelete+"</td>");
               listHtml.push("<td><a href=\"#\">操作</a></td>");
               listHtml.push("<tr>");
            }
            $("#table-1").empty().append(listHtml.join(""));
		}
	 }
	);
	/* <span style="line-height:12px; text-align:center;"> 
	<a href="javascript:void(0)" class="xiu" onclick="showMsg('是否删除该项数据？',${uuid})">删除</a>
	</span>
	function showMsg(msg,uuid){
		//top.document.getElementById("context-msg").style.display = "block";
		top.$('context-msg').style.display = "block";
		top.$('context-msg-text').innerHTML=msg;
		top.$('hid-action').value="emp_delete.action?em.uuid="+uuid;
		top.lock.show();
	} */
}
	
	//提交保存
	function toSubmit(){
	  $.ajax({
		    type: 'post',
		    url: '${pageContext.request.contextPath}/system/emp/save',
		    data: $("form").serialize(),
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
</head>
<body style="font-size: 12px;font-family: Verdana;color: rgb(177, 106, 104);">
		<legend>新增员工</legend>
		 <form id="form" name="form" class="form-horizontal" method="post" action="">	 
		   <label class="control-label">员工姓名</label><input type="text" class="form-control"  placeholder="请输入姓名" name="name" id="name"/>
		   <label class="col-sm-3 control-label">性别</label><input type="text" class="form-control checkacc" placeholder="请输入性别" name="gender" id="gender">
		    <label class="col-sm-3 control-label">电话</label><input type="text" class="form-control checkacc" placeholder="请输入电话" name="tele" id="tele">
		   <label class="col-sm-3 control-label">邮件</label><input type="text" class="form-control checkacc" placeholder="请输入邮箱" name="email" id="email">
		   <label class="col-sm-3 control-label">电话</label><input type="text" class="form-control checkacc" placeholder="电话" name="mobile" id="mobile">	
		   <label class="col-sm-3 control-label">地址</label><input type="text" class="form-control checkacc" placeholder="地址" name="address" id="address">	
		   <label class="col-sm-3 control-label">是否有效</label>	<input type="radio" name="isDelete" value='0' checked="checked">是 <input type="radio" name="isDelete" value='1'>否
		    <button type="button" class="btn btn-success btn-s-xs" onclick="javascript:toSubmit();">提交</button>
		 </form>
	</fieldset>
	
	<fieldset>
		<legend>员工列表</legend>
		 <form id="form1" name="form1" class="form-horizontal" method="post" action="">	 
		    <table id="table-1" width="70%">
		     <div id="listDiv">
		     </div>
		    </table>
		 </form>	
	</fieldset>
	
</body>
</html>
