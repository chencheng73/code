<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
var zNodes;
var setting = {
  check: {enable: false},
  showLine:false,
  data: {simpleData: {enable: true}},
  callback: { 
        onClick: onClick,
  },
  async: {
		enable: false,
  }
};
function onClick(e,treeId, treeNode) { 
   zTree = $.fn.zTree.getZTreeObj("menuTree");
   zTree.expandNode(treeNode);
   var resId=treeNode.id;
   //打开子页面，添加或查看菜单明细
   parent.document.getElementById("rightMain").src="<%=request.getContextPath()%>/system/resource/to_add?resId="+resId;
}
$(function(){ 
    $.ajax({ 
        url:"${pageContext.request.contextPath}/system/resource/queryResources/0",
        async : false,
        type: 'POST', 
        dataType : "json",  
        contentType:"application/json;charset=utf-8",
        error: function (e) { 
            alert('请求失败'+e); 
        }, 
        success:function(data){  
            zNodes = data; 
        } 
    });
   /**加载菜单列表 **/
   $.fn.zTree.init($("#menuTree"), setting, zNodes);
})
</script>
</head>
<body style="font-size: 12px; font-family: Verdana;">
<fieldset>
<legend style="font-weight: bold;">菜单列表</legend>
<div class="zTreeDemoBackground left">
		<ul id="menuTree" class="ztree"></ul>
	</div>
</fieldset>
</body>
</html>