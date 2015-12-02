//加载组织机构Tree

var zNodes=null;
var checkType="radio";
var inputId;
//接收返回值
var inputName;

//配置属性
var setting = {
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "all"
	},
	view: {
		dblClickExpand: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onClick: onClick,
		onCheck: onCheck
	}
};


//加载数据
 $(function(){  
    //**加载部门列表 **// 
 	loadDepartment(); 
    $.fn.zTree.init($("#deptTree"), setting, zNodes);	  
}) 

//加载部门数据
function loadDepartment(){    
   $.ajax({ 
        url:basePath+"/system/department/referDepartment",
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
}


//点击选择节点
function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("deptTree");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

//选择赋值
function onCheck(e, treeId, treeNode) { 
	var zTree = $.fn.zTree.getZTreeObj("deptTree"),
	nodes = zTree.getCheckedNodes(true),
	value = "";
	key="";
	for (var i=0, l=nodes.length; i<l; i++) {
		value += nodes[i].name + ",";
		key+=nodes[i].id+",";
	}
	if (value.length > 0 ) value = value.substring(0, value.length-1);
	if (key.length > 0 ) key = key.substring(0, key.length-1);
	var inputId=$("#deptId").val(key);  	
	var inputName=$("#deptName").val(value);
}

//加载菜单
function showMenu($inputId,$inputName) {
	var cityObj = $("#deptName");  
	var cityOffset = cityObj.offset();
	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}

//隐藏菜单
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "deptName" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}
