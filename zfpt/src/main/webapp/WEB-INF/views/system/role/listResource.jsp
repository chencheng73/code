<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
var zNodes;
var roleId="${roleid}";
var setting = {
  check: {enable: true},
  showLine:false,
  data: {simpleData: {enable: true}},
  async: {
		enable: false,
  }
};

//提交保存
function toSubmit(){
	var selectObject=$(":checked");
	var treeObj = $.fn.zTree.getZTreeObj("menuTree");
	var nodes = treeObj.getCheckedNodes(true);
	var nodeArray=new Array();
	if(nodes.length<1){
	   alert("请选择要分配的资源");
	   return false;
	}else{
	   for(var i in nodes){
		   nodeArray.push(nodes[i].id); 
	   }	
	   //提交授权
	   $.ajax({
		    type: 'post',
		    url: '${pageContext.request.contextPath}/system/role/discResource',
		    data: {
		    	"id":"${roleid}",
		        "resIds":nodeArray.toString()
		    },
		    dataType:"json",
		    async:false,
		    success: function(data) {
		    	 if(data.updateCount<0){
		    		alert(data.errorMessage);
		    		return false;
		    	 }else{
		    		alert(data.info);
		    	 }
		    }
		}); 
	    window.returnValue="success";
	    window.close();
	}
}

function cannel(){
	window.close();
}

$(function(){ 
    $.ajax({ 
        url:"${pageContext.request.contextPath}/system/role/queryResources/"+roleId,
        async : false, 
        type: 'POST', 
        dataType : "json",  
        contentType:"application/json;charset=utf-8",
        error: function () { 
            alert('请求失败'); 
        }, 
        success:function(data){  
            zNodes = data; 
        } 
    });
   /**加载菜单列表 **/
   $.fn.zTree.init($("#menuTree"), setting, zNodes);
})
</script>
<title>资源列表信息</title>
</head>
<body style="font-size: 12px; font-family: Verdana; color: rgb(177, 106, 104);">
<div>
  <input type="button" name="saveBtn" value="确定" onclick="javascript:toSubmit();" />
  <input type="button" name="cannelBtn" value="取消" onclick="javascript:cannel();" />
</div>

 	<div class="zTreeDemoBackground left">
		<ul id="menuTree" class="ztree"></ul>
	</div>
</body>
</html>