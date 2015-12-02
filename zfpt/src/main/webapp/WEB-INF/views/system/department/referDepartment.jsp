<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织参照</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/demo.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
var zNodes;
var checkType="radio";
var inputType="${inputType}";
if(inputType!=""&&inputType=="2"){
   checkType="checkbox";	
}
var setting = {
  check: {enable:true,chkStyle:checkType,radioType: "all"},
  view: {dblClickExpand: false},
  showLine:false,
  data: {simpleData: {enable: true}},
  callback: {  onClick: onClick,onCheck: onCheck}
};

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("deptTree");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("deptTree"),
	nodes = zTree.getCheckedNodes(true),
	v = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	var cityObj = $("#citySel");
	cityObj.attr("value", v);
}

function showMenu() {
	var cityObj = $("#citySel");
	var cityOffset = $("#citySel").offset();
	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

//加载树形参照: type:1,单选；2,多选
$(function(){ 
    $.ajax({ 
        url:"${pageContext.request.contextPath}/system/department/referDepartment",
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
   /**加载部门列表 **/
   $.fn.zTree.init($("#deptTree"), setting, zNodes);	  
})

 


</script>
</head>
<body style="font-size: 12px; font-family: Verdana;">
<fieldset>
<legend style="font-weight: bold;">部门列表</legend>
<!-- <div class="zTreeDemoBackground left" style="z-index: 6000px;">
		<ul id="deptTree" class="ztree"></ul>
	</div> -->
<div class="zTreeDemoBackground left">
		<ul class="list">
			<li class="title">&nbsp;&nbsp;部门名称: <input id="citySel" type="text" readonly value="" style="width:120px;" onclick="showMenu();" />
		&nbsp;<a id="menuBtn" href="#" onclick="showMenu(); return false;">选择部门</a></li>
		</ul>
</div>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="deptTree" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
</div>

</fieldset>
</body>
</html>