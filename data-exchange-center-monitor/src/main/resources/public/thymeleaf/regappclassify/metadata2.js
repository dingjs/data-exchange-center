function showMenu() {
	var cityObj = $("#citySel");
	var cityOffset = $("#citySel").offset();
	$("#menuContent").css({}).slideDown("fast");
};

// 隐藏树
function hideMenu() {
	$("body").unbind("mousedown", onBodyDown);
};
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
			event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}
};
var setting = {
		edit : {
			enable : true,
			editNameSelectAll : true,
			showRemoveBtn : showRemoveBtn,
			showRenameBtn : showRenameBtn
		},

		check : {
			enable : false
		},
		callback : {
			beforeDrag : beforeDrag,
			beforeEditName : beforeEditName,
			beforeRemove : beforeRemove,
			beforeRename : beforeRename,
			onRemove : onRemove,
			onRename : onRename
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		simpleData : {
			enable : false,
			idKey : "id",
			pidKey : "pId",
			rootPid : null
		}
};

var zNodes = [];

var code;

function setCheck() {
	var zTree = $.fn.zTree.getZTreeObj("baseTree1"), py = $("#py").attr(
			"checked") ? "p" : "", sy = $("#sy").attr("checked") ? "s" : "", pn = $(
			"#pn").attr("checked") ? "p" : "", sn = $("#sn").attr("checked") ? "s"
			: "", type = {
		"Y" : "ps",
		"N" : "ps"
	};
	zTree.setting.check.chkboxType = type;
	showCode('setting.check.chkboxType = { "Y" :  "ps", "N" : "ps"};');
};

function AutoMatch(txtObj) {
	if (txtObj.value.length > 0) {
		$.fn.zTree.init($("#baseTree1"), setting, zNodes);
		var zTree = $.fn.zTree.getZTreeObj("baseTree1");
		var nodeList = zTree.getNodesByParamFuzzy("name", txtObj.value);
		// 将找到的nodelist节点更新至Ztree内
		$.fn.zTree.init($("#baseTree1"), setting, nodeList);
		showMenu();
	} else {
		// 隐藏树
		hideMenu();
		$.fn.zTree.init($("#baseTree1"), setting, zNodes);
	}
}
function findSelect() {
	var treeObj = $.fn.zTree.getZTreeObj("baseTree1");
	var nodes = treeObj.getSelectedNodes();
	for (var i = 0; i < nodes.length; i++) {
		var nodeId = nodes[i].id;
		var nodeName = nodes[i].name;
		alert("树节点编码：" + nodeId + "\n" + "树节点名称：" + nodeName);
	}
};
function showCode(str) {
	if (!code)
		code = $("#code");
	code.empty();
	code.append("<li>" + str + "</li>");
};

function getTree() {

	/*
	 * var zNodes =[ { id:1, pId:0, name:"父节点 1", open:true}, { id:11, pId:1,
	 * name:"叶子节点 1-1"}, { id:12, pId:1, name:"叶子节点 1-2"}, { id:13, pId:1,
	 * name:"叶子节点 1-3"}, { id:2, pId:0, name:"父节点 2", open:true}, { id:21,
	 * pId:2, name:"叶子节点 2-1"}, { id:22, pId:2, name:"叶子节点 2-2"}, { id:23,
	 * pId:2, name:"叶子节点 2-3"}, { id:3, pId:0, name:"父节点 3", open:true}, {
	 * id:31, pId:3, name:"叶子节点 3-1"}, { id:32, pId:3, name:"叶子节点 3-2"}, {
	 * id:33, pId:3, name:"叶子节点 3-3"} ];
	 */
	var arr = [];
	$.ajax({
		type : "get",
		async : false, // 同步执行
		url : "/metadata/getMetadata/0",
		data : [],
		dataType : "json", // 返回数据形式为json
		success : function(result) {
			if (result) {
				zNodes = result;
			}
		},
	})
};
var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}
function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; "
			+ treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj("baseTree1");
	zTree.selectNode(treeNode);
	setTimeout(function() {
		if (confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？")) {
			setTimeout(function() {
				zTree.editName(treeNode);
			}, 0);
		}
	}, 0);
	return false;
}
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
			+ treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj("baseTree1");
	zTree.selectNode(treeNode);
	var a = confirm("确认删除 节点 -- " + treeNode.name + " 吗？")
	return a;
}
function onRemove(e, treeId, treeNode) {
	showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
			+ treeNode.name);
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "" : "dark");
	showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime()
			+ " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
			+ (isCancel ? "</span>" : ""));
	if (newName.length == 0) {
		setTimeout(function() {
			var zTree = $.fn.zTree.getZTreeObj("baseTree1");
			zTree.cancelEditName();
			alert("节点名称不能为空.");
		}, 0);
		return false;
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
	showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime()
			+ " onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
			+ (isCancel ? "</span>" : ""));
}
function showRemoveBtn(treeId, treeNode) {

	return false;
	// return !treeNode.isFirstNode;
}
function showRenameBtn(treeId, treeNode) {
	return false;
	// return !treeNode.isLastNode;
}
function showLog(str) {
	if (!log)
		log = $("#log");
	log.append("<li class='" + className + "'>" + str + "</li>");
	if (log.children("li").length > 8) {
		log.get(0).removeChild(log.children("li")[0]);
	}
}
function getTime() {
	var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
			.getSeconds(), ms = now.getMilliseconds();
	return (h + ":" + m + ":" + s + " " + ms);
}
var userTable;
var winId = "userWin";
var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn)
		btn.bind("click", function() {
			var zTree = $.fn.zTree.getZTreeObj("baseTree1");
			zTree.addNodes(treeNode, {
				id : (100 + newCount),
				pId : treeNode.id,
				name : "new node" + (newCount++)
			});
			return false;
		});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};
function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("baseTree1");
	zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}

$(document).ready(function() {
	$.fn.zTree.init($("#baseTree1"), setting, zNodes);
	$("#selectAll").bind("click", selectAll);
});
$(document).ready(function() {
	getTree()
	$.fn.zTree.init($("#baseTree1"), setting, zNodes);
	setCheck();
	$("#py").bind("change", setCheck);
	$("#sy").bind("change", setCheck);
	$("#pn").bind("change", setCheck);
	$("#sn").bind("change", setCheck);
});
