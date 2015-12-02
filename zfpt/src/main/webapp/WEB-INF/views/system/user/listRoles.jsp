<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
$(function(){
	loadData();
})
function loadData(){
	 var listHtml=new Array();
	 var orginRoleIds="${userRoles}";
	 var compArray=null;
	 if(orginRoleIds!=null){
		comArray=orginRoleIds.split(",");
	 }
  	 listHtml.push("<tr>");
     listHtml.push("<td><input type=\"checkbox\" id=\"all\" onClick=\"javascript:selectAll();\"  /> </td>");
     listHtml.push("<td>序号</td>");
     listHtml.push("<td>角色标识</td>");
     listHtml.push("<td>角色名称</td>");
     listHtml.push("<td>职责描述</td>");
     listHtml.push("<tr>");
	$.getJSON(
	 "${pageContext.request.contextPath}/system/role/listPage",
	 {'pageNo':1,'pageSize':15},
	  function(datas){  
		if(datas.totalCount>0){
			var listResult=datas.data;  
            for(var i in listResult){
                listHtml.push("<tr>");
                //是否已分配
                var isChecked=false;
                var　roleId=listResult[i].id;
                for(var o in comArray){
          		    if(roleId==comArray[o]){
          			   isChecked=true;break;
          		    }else{
          			   isChecked=false;   
          		    } 
          	   }
               if(isChecked){
            	 listHtml.push("<td><input type=\"checkbox\" id=\"selectOne\" name=\"ids\" value=\""+roleId+"\" checked/> </td>"); 
               }else{
               	 listHtml.push("<td><input type=\"checkbox\" id=\"selectOne\" name=\"ids\" value=\""+roleId+"\" /> </td>");
               }
               listHtml.push("<td>"+(parseInt(i)+1)+"</td>");
               listHtml.push("<td>"+listResult[i].roleCode+"</td>");
               listHtml.push("<td>"+listResult[i].roleName+"</td>");
               listHtml.push("<td>"+listResult[i].description+"</td>");
               listHtml.push("<tr>");
            }
            $("#table-1").empty().append(listHtml.join(""));
		}
	 }
	);
}
function selectAll(object){
	var _this=$("#all"); 
	if(_this.is(":checked")==true){
	   $("input[name='ids']").attr("checked",true);	
	}else{
		$("input[name='ids']").attr("checked",false);	
	} 
}


//提交保存
function toSubmit(){
 var selectObjects=$("input[name='ids']:checked");
 if(selectObjects.length>0){
	 var ids=new Array();
	 $(selectObjects).each(function(){
		ids.push($(this).val()); 
	 });
	  $.ajax({
		    type: 'post',
		    url: '${pageContext.request.contextPath}/system/user/discRole',
		    data: {
		    	"id":"${userid}",
		        "rIds":ids.toString()
		    },
		    dataType:"json",
		    async:false,
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
	    window.returnValue="success";
	    window.close();
 }else{
	 alert("请选择要分配的角色");
	 return false;
 }	 
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
<title>角色列表信息</title>
</head>
<body
	style="font-size: 12px; font-family: Verdana; color: rgb(177, 106, 104);">
	<fieldset>
		<legend>角色列表</legend>
		<form id="form1" name="form1" class="form-horizontal" method="post"
			action="">
			<table id="table-1" width="100%"></table>

			<input type="button" value="确定" onclick="javascript:toSubmit();" />
		</form>
	</fieldset>
</body>
</html>