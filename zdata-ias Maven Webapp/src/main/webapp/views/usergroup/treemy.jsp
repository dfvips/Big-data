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
							<div class="layui-col-xs4">
								<div id="orgMyzTree" class="ztree"></div>
							</div>
							<div class="layui-col-xs8">
								<div class="panel">
									<div class="panel-heading">
										<h4 class="panel-title" id="orgName"></h4>
									</div>
									<div class="panel-body">
										<table id="myGroupUsers"></table>
									</div>
								</div>
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
    initMyzTree();
})();

//初始化树
function initMyzTree() {
    $.ajax({
        url: "${pageContext.request.contextPath}/org/getRootMy.do",
        type: "POST",
        dataType: "json",
        data: {
            softId: '${IASSoftID }',
            userId: '${IASloginUser}'
        },
        success: function(result) {
            var zNodes = result.orgList;
            if (result) {
                var zTreeObj = $.fn.zTree.init($("#orgMyzTree"), setting, zNodes);
                var nodes = zTreeObj.getNodes();
                for (var i = 0; i < nodes.length; i++) { //设置节点展开
                    zTreeObj.expandNode(nodes[i], false, true, true);
                }
            }
            document.getElementById("orgName").innerHTML = zNodes[0].name;
            
        }
    });
    var setting = {
        edit: {
            enable: true,
            editNameSelectAll: true,
            showRemoveBtn: false,
            removeTitle: "删除部门",
            showRenameBtn: false,
            renameTitle: "修改部门"
        },
        callback: {
            onClick: zTreeOnClick
        },
        view: {
            showIcon: true,
            showLine: true
        },
        async: {
            enable: true,
            url: "${pageContext.request.contextPath}/org/getzTree.do",
            type: "post",
            autoParam: ["id"],
            dataFilter: filter
        }
    };
    //转化
    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        var nodes = childNodes.orgList;
        return nodes;
    }
    
    /**
     * 鼠标点击事件
     */
    function zTreeOnClick(event, treeId, treeNode) {
    	document.getElementById("orgName").innerHTML = treeNode.name;
    	$("#myGroupUsers").bootstrapTable('destroy');
    	initTable(treeNode.id);
    };
    
}

function initTable(orgId){
	$('#myGroupUsers').bootstrapTable({
        url: "${pageContext.request.contextPath}/usergroup/queryByOrgId.do",
        pagination: true,
        // 是否分页
        pageNumber: 1,
        pageSize: 10,
        pageList: [5, 10, 25, 50],
        sidePagination: 'server',
        search: false,
        trimOnSearch: false,
        searchPlaceholder: "搜索",
        showRefresh: false,
        showToggle: false,
        showColumns: false,
        //显示隐藏列  
        iconSize: 'bg',
        //'outline',
        clickToSelect: true,
        queryParamsType: 'not-limit',
        //toolbar: '#Toolbar',
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
            title: '用户Id'
        },
        {
            field: 'sysUserName',
            title: '用户名称'
        },
        {
            field: 'sysUserGroupName',
            title: '部门名称'
        },
        {
            field: 'sysUserPosition',
            title: '职务'
        }]
    });
    
    //附加参数
    function queryParams(params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
           // limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            pageSize:this.pageSize,
            pageNumber:this.pageNumber,
            orgId: orgId
        };
        return temp;
    }
}
</script>
