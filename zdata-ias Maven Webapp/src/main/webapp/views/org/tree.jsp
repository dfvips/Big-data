<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/zTree_v3/css/metroStyle/metroStyle.css">
<script
	src="${pageContext.request.contextPath}/zTree_v3/js/jquery.ztree.all.js"></script>
<div class="main-content">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">
						<h3 class="panel-title">${modeName }</h3>
					</div>
					<div class="panel-body">
						<div class="layui-row">
							<div class="layui-col-md3">
								<div id="testTree" class="ztree"></div>
							</div>
							<div class="layui-col-md9">
								<table class="table" id="userTable">
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
(function() {
    initzTree();
    initTable();
})();
var setting = {
    check: {
        enable: false
    },
    edit: {
        drag: {
            isMove: false,
            isCopy: false
        },
        enable: true,
        editNameSelectAll: true,
        showRemoveBtn: false,
        //显示删除按钮
        removeTitle: "删除",
        showRenameBtn: false,
        //显示修改按钮
        renameTitle: "修改"
    },
    callback: {
        beforeRemove: beforeRemove,
        //点击删除时触发，用来提示用户是否确定删除（可以根据返回值 true|false 确定是否可以删除）
        //onRightClick: onRightClick,
        beforeRename: beforeRename,
        onClick: zTreeOnClick
        //onDblClick: zTreeOnDblClick
        //onAsyncSuccess : ztreeOnAsyncSuccess
    },
    view: {
    	//addHoverDom: addHoverDom, //当鼠标移动到节点上时，显示用户自定义控件
        //selectedMulti: false,
        showIcon: true,
        //设置是否显示节点图标
        showLine: true
        //是否显示节点之间的连线
    },
    async: {
        enable: true,
        url: "${pageContext.request.contextPath}/org/getOnzTree.do",
        type: "post",
        autoParam: ["id"],
        dataFilter: filter
    }
};

//转化
function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    var nodes = childNodes.orgList;
   /*  for (var i=0, l=nodes.length; i<l; i++) {
    	nodes[i].name = nodes[i].name.replace(/\.n/g, '.');
    } */
    return nodes;
}

//初始化树
function initzTree() {
    $.ajax({
        url: "${pageContext.request.contextPath}/org/getOnzTree.do",
        type: "POST",
        dataType: "json",
        data: {
        	id: '${IASSoftID }'
        },
        success: function(result) {
        	var zNodes = result.orgList;
            if (result) {
            	var zTreeObj = $.fn.zTree.init($("#testTree"), setting, zNodes);
                var nodes = zTreeObj.getNodes();
                for (var i = 0; i < nodes.length; i++) { //设置节点展开
                    zTreeObj.expandNode(nodes[i], false, true, true);
                }
            }
        }
    });
}
/**添加**/
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span"); //获取节点信息
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加' onfocus='this.blur();'></span>"; //定义添加按钮
    sObj.after(addStr); //加载添加按钮
    var btn = $("#addBtn_" + treeNode.tId);
    //绑定添加事件，并定义添加操作
    if (btn) btn.bind("click",
    function() {
    	//var zTree = $.fn.zTree.getZTreeObj("zTreeObj");
        //将新节点添加到数据库中
        $.post('#(PATH)/manage/td/tree/add?pid=' + treeNode.id ,
        function(reData) {
        	if (reData && reData.success) {
        		//sObj.after("");
        		initzTree();
        	}
        });
       //     var newID = data; //获取新添加的节点Id
        //    zTree.addNodes(treeNode, {
        //        id: newID,
       //         pId: treeNode.id,
       //         name: name
        //    }); //页面上添加节点
        //    var node = zTree.getNodeByParam("id", newID, null); //根据新的id找到新添加的节点
        //    zTree.selectNode(node); //让新添加的节点处于选中状态
      //  });
    });
};

/**删除**/
function beforeRemove(treeId, treeNode) {
	if(treeNode.pid==0){
		layer.msg("无法删除根节点!");
		return false;
	}
    if (treeNode.isParent) { //判断是否为父节点
        layer.msg("请先删除子项");
        return false;
    } else {
        //return false;
        var flag = false;
        layer.confirm('确定删除？', {
            btn: ['确定', '取消']
            //按钮
        },
        function() {
            $.post("#(PATH)/manage/td/tree/delete", {
                id: treeNode.id
            },
            function(reData) {
                if (reData && reData.success) {
                    //$("#caseListTable").bootstrapTable('refresh');
                    layer.msg("删除成功!");
                    initzTree();
                    flag = true;
                } else {
                    layer.msg("系统错误,请稍后再试!");
                    flag = false;
                }
            });
        },
        function() {
            flag = false;
        });
        return flag;
    }

}
/** 菜单右键事件*/
function onRightClick(event, treeId, treeNode) {
    // 判断点击了tree的“空白”部分，即没有点击到tree节点上
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
    	zTreeObj.cancelSelectedNode();
        // 只显示添加菜单项，这个只是外观上的控制，不能控制到点击事件！菜单项的点击事件还要额外判断！
        $('#modifyNode').attr('disabled', true);
        $('#delNode').attr('disabled', true);
    } else if (treeNode && !treeNode.noR) { // 判断点击的是tree节点
    	zTreeObj.selectNode(treeNode); // 选中tree节点
        $('#modifyNode').attr('disabled', false);
        $('#delNode').attr('disabled', false);
        if (treeNode.children && treeNode.children.length > 0) { // 这里父节点不能直接删除，也可以在菜单项的click事件中根据当前节点另作判断
            $('#delNode').attr('disabled', true);
        }
    }
    // 在ztree右击事件中注册easyui菜单的显示和点击事件，让这两个框架实现共用event，这个是整合的关键点
    $('#mm').menu({
        onClick: function(item) {
            if (item.name == 'new') {
                alert("新增节点");
            } else if (item.name == 'modify' && !$('#modifyNode').attr('disabled')) {
                alert("修改节点");
            } else if (item.name == 'del' && !$('#delNode').attr('disabled')) {
                /*
                 if (treeNode.children && treeNode.children.length > 0) {
                  alert("该节点是父节点，还要继续删除么？");
                 }*/
                alert("删除节点");
            }
        }
    });
    $('#mm').menu('show', {
        left: event.pageX,
        top: event.pageY
    });
}
/**
 * 修改
 */
function beforeRename(treeId,treeNode,newName){
	//var zTree = $.fn.zTree.getZTreeObj("zTreeObj");
	if(newName.length == 0){
		setTimeout(function(){
			// zTree.cancelEditName();
			zTreeObj.cancelEditName();
			layer.msg("节点名称不能为空");
		},0);
		return false;
	} else {
		var flag;
		$.ajax({
			url : "#(PATH)/manage/td/tree/save",
			type : "post",
			//async : true,
			data : {
				id : treeNode.id,
				name : newName
			},
			success: function (reData){
				if (reData && reData.success) {
                    //$("#caseListTable").bootstrapTable('refresh');
                    layer.msg("修改成功!");
                    //initzTree();
                    flag = true;
                } else {
                    layer.msg("系统错误,请稍后再试!");
                    flag = false;
                }
			}
		});
		return flag;
	}
}
/**
 * 鼠标点击事件
 */
function zTreeOnClick(event, treeId, treeNode) {
	
	$("#userTable").bootstrapTable('destroy');
	initTable(treeNode.id);
	//$("#userTable").bootstrapTable('refresh');
};

/**
 * 初始化表格数据
 */
function initTable(orgId) {
	if(orgId==null){
		orgId = '${IASSoftID }';
	}
	//
    $('#userTable').bootstrapTable({
        url: "${pageContext.request.contextPath}/user/queryByOrgId.do",
        pagination: true,
        // 是否分页
        pageNumber: 1,
        pageSize: 10,
        pageList: [5, 10, 25, 50],
        sidePagination: 'server',
        search: true,
        trimOnSearch: true,
        searchPlaceholder: "搜索",
        showRefresh: true,
        showToggle: true,
        showColumns: false,
        //显示隐藏列  
        iconSize: 'bg',
        //'outline',
        clickToSelect: true,
        queryParamsType: 'not-limit',
        toolbar: '#Toolbar',
        buttonsAlign: "right",
        toolbarAlign: "left",
        searchAlign: "right",
        queryParams :queryParams,
        icons: {
            refresh: 'glyphicon-repeat',
            toggle: 'glyphicon-list-alt'
        },
        columns: [{
            title: '序号',
            align: 'center',
            valign: 'bottom',
            formatter: function(value, row, index) {
                return index + 1;
            }
        },
        {
            field: 'sysUserId',
            title: '用户ID'
        },
        {
            field: 'sysUserName',
            title: '用户名称'
        },
        {
            field: 'email',
            title: '邮箱'
        },
        {
            field: 'mobtele',
            title: '手机'
        },
        {
            field: 'state',
            title: '状态',
            formatter: actionFormatter
        }]
    });
    //操作栏的格式化
    function actionFormatter(value, row, index) {
    	if(value==0){
    		return '<span class="label label-default">无效</span>';
    	}else if (value==1){
    		return '<span class="label label-success">有效</span>';
    	}
    }
    
    //附加参数
    function queryParams(params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
           // limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            pageSize:this.pageSize,
            pageNumber:this.pageNumber,
            searchText:this.searchText,
            orgId: orgId
        };
        return temp;
    }
}
</script>