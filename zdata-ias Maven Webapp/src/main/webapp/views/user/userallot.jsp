<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
						<table class="table table-bordered">
							<tr>
								<td valign="top" width="35%">
									<div id="orgTree" class="ztree"></div>
								</td>
								<td valign="top" width="65%">
									<table class="table" id="userTable">
									</table>
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
													<button type="button" class="btn btn-primary btn-xs" onClick="setOrgUsers()">确定</button>
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
    initzTree();
    initTable();
})();
//初始化部门树
function initzTree(){
	var setting = {
		    check: {
		        enable: false//设置 zTree 的节点上是否显示 checkbox / radio 默认值: false
		    },
		    edit: {
		        enable: false
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
	    for (var i=0, l=nodes.length; i<l; i++) {
	    	nodes[i].name = nodes[i].name.replace(/\.n/g, '.');
	    }
	    return nodes;
	}
	
	function zTreeOnClick(event, treeId, treeNode){
		$("#orgId").val(treeNode.id);
		$("#orgName").val(treeNode.name);
	}
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
            	var zTreeObj = $.fn.zTree.init($("#orgTree"), setting, zNodes);
                var nodes = zTreeObj.getNodes();
                for (var i = 0; i < nodes.length; i++) { //设置节点展开
                    zTreeObj.expandNode(nodes[i], false, true, true);
                }
            }
        }
    });
}

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
        showRefresh: false,
        showToggle: false,
        showColumns: false,
        //显示隐藏列  
        //'outline',
        clickToSelect: true,
        queryParamsType: 'not-limit',
        buttonsAlign: "left",
        toolbarAlign: "right",
        searchAlign: "left",
        queryParams :queryParams,
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
        },
        {
        	checkbox: true,
        	align : 'center',
        	formatter: function (value, row, index) {            // 每次加载 checkbox 时判断当前 row 的 id 是否已经存在全局 Set() 里
        		if($.inArray(row.rowId,Array.from(overAllIds))!=-1){    // 因为 Set是集合,需要先转换成数组  
            		return {
            			disabled : true,
            			checked : true 
            		}
                };
            }
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
    
    $('#userTable').on('uncheck.bs.table check.bs.table check-all.bs.table uncheck-all.bs.table',function(e,rows){
        var datas = $.isArray(rows) ? rows : [rows];        // 点击时获取选中的行或取消选中的行
        examine(e.type,datas);                                  // 保存到全局 Set() 里
   });
}

var overAllIds = new Set();
function examine(type,datas){
	if(type.indexOf('uncheck')==-1){    
        for(item in datas){
        	datas[item].rowId;
        	overAllIds.add(datas[item].rowId);//添加选中
        }
    }else{
    	for(item in datas){
    		overAllIds.delete(datas[item].rowId);
    	}
    }
}

//提交保存
function setOrgUsers(){
	var orgId = $("#orgId").val();//组织ID
	if( orgId==null || orgId == ""){
		layer.msg("请选择部门！");
		return;
	}
	var size = overAllIds.size;//大小
	if(size>0){
		var userIds = "";
		var i = 0;
		overAllIds.forEach(function (element, index, array) {
		    // element: 指向当前元素的值
		    // index: 指向当前索引
		    // array: 指向Array对象本身
			if(i+1==size){
				userIds += element ;
			}else{
				userIds += element + ",";
			}
			i++;
		});
		$.ajax({
			url:'${pageContext.request.contextPath}/user/setGroupId.do',
			type:'post',
			data: {
				userIds: userIds,
				orgId: orgId
			},
			dataType:'json',
			success:function(reData){
				if(reData.success){
					layer.msg("操作成功！");
					setTimeout('window.location.reload()',500);
				}else{
					layer.msg(reData.msg);
					setTimeout('window.location.reload()',500);
				}
			}
		}) 
	}else{
		layer.msg("请选择用户！");
		return;
	}
	
	
}
</script>