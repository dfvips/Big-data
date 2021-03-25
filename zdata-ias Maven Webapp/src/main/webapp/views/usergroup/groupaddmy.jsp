<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
						<table class="table table-bordered">
							<tr>
								<td valign="top" width="35%">
									<div id="orgMyzTree" class="ztree"></div>
								</td>
								<td valign="top" width="65%">
									<table id="myOrgUsersTable"></table>
									<center>
										<form class="form-horizontal" method="post" id="setForm">
											<input type="hidden" id="orgId" name="orgId">
											<div class="form-group">
												<label class="col-sm-4 control-label" style="width: 200px;">将选中的用户分配在:</label>
												<div class="col-sm-4">
													<input type="text" class="form-control" id="orgName"
														name="orgName" value="" readonly>
												</div>
												<div class="col-sm-2">
													<button type="button" class="btn btn-primary btn-xs" onClick="setGroupUsers()">确定</button>
												</div>
											</div>
										</form>
									</center>
								</td>
							</tr>
						</table>
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
            initmyOrgUsersTable(zNodes[0].id);
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
    	$("#orgId").val(treeNode.id);
    	$("#orgName").val(treeNode.name);
    };
    
}

/**
 * 公司用户
 */
function initmyOrgUsersTable(orgId){
	$('#myOrgUsersTable').bootstrapTable({
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
            width:'20px',
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
        	radio: true,
        	align : 'center'
        }]
    });
    
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

/**
 * 设置分组
 */
function setGroupUsers(){
	var orgId = $("#orgId").val();
	if(orgId==null || orgId==""){
		layer.msg("请选择部门");
		return;
	}else{
		var rows = $('#myOrgUsersTable').bootstrapTable('getSelections');
		if(rows.length==0){
			layer.msg("选中用户");
		}else{
			//设置用户Id
			$.ajax({
				url:'${pageContext.request.contextPath}/usergroup/save.do',
				type:'post',
				dataType:'json',
				data:{
					
				},
				success:function(reData){
					if(reData.success){
						layui.msg("操作成功！");
						setTimeout('window.location.reload()',500);
					}else{
						layui.msg(reData.msg);
						setTimeout('window.location.reload()',500);
					}
				}
			})
		}
	}
}
</script>
