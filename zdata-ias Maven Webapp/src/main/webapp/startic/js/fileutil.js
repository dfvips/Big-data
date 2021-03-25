

function loadUrl() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/uimcardprj
	/*
	 * var projectName = pathName .substring(0, pathName.substr(1).indexOf('/') +
	 * 1);
	 */
	return(localhostPaht);
}

//增加附件
var attachmentNum = 2;

function addAttachment() {
	var rehtml = '<tr><td id="array_'+attachmentNum+'"><span>'+attachmentNum+'</span></td>'+
			'<td><input type="file" onchange="uploadFile('+attachmentNum+')" name="files">'+
			'<input type="hidden" id="fileUrl_'+attachmentNum+'" name="fileUrl_'+attachmentNum+'">'+
			'<input type="hidden" id="fileSize_'+attachmentNum+'" name="fileSize_'+attachmentNum+'">'+
			'<input type="hidden" id="fileSub_'+attachmentNum+'" name="fileSub_'+attachmentNum+'"></td>'+
			'<td><input class="layui-input" id="fileName_'+attachmentNum+'" name="fileName_'+attachmentNum+'"></td>'+
			'<td colspan="2"><input class="layui-input" name="fileDesc_'+attachmentNum+'" id="fileDesc_'+attachmentNum+'"></td></tr>';
	attachmentNum++;
	$("#fileContents").append(rehtml);
}


//删除附件
function delAttachment() {
	var length = $("#fileContents tr").length;
	if(length > 3) {
		$("#fileContents tr").eq(attachmentNum * 1).remove();
		attachmentNum--;
	} else {
		layer.msg("已删除到最后一项!");
	}
}

//上传文件
function uploadFile(num) {
	var formData = new FormData($("#uploadFile")[0]);
	$.ajax({
		type: "post",
		url: basePath + "/IAS4/sysFile/saveFile.do?num=" + num,
		data: formData,
		async: false,
		cache: false,
		dataType: 'json',
		contentType: false,
		processData: false,
		success: function(data) {
			if(data.status == "success"){
				layer.msg("上传成功!");
				$("#fileUrl_" + num).val(data.fileUrl); // 后台返回的base64数据
				$("#fileSize_" + num).val(data.fileSize); // 后台返回的文件大小
				$("#fileSub_" + num).val(data.fileSub); // 文件后缀名
				$("#fileName_" + num).val(data.fileName); // 原文件名
			}else{
				layer.msg("上传失败!");
			}
		},
		error: function(error) {
			layer.msg("系统出错!");
		}
	});
}

/**
 * 加载附件文件
 * @param busId
 * @param busType
 * @param userId
 * @param publicKey
 * @param type:"info":查看状态，"edit":编辑状态
 */
function loadFile(busId, busType, userId, publicKey) {
	layui.use('table', function() {
		var table = layui.table;
		var basePath = loadUrl();
		table.render({
			elem : '#fileInfo',
			url : basePath+'/IAS4/sysFile/find.do',
			request : {
				pageName : 'page',
				limitName : 'rows'
			},
			where : {
				busType : busType,
				busId : busId,
				userId : userId,
				publicKey : publicKey
			},
			toolbar : false,
			title : '文件列表',
			totalRow : true,
			cols : [ [ {
				type : 'numbers',
				title : '序号'
			}, {
				field : 'fileName',
				width: '26%',
				title : '文件名称'
			}, {
				field : 'fileDesc',
				title : '文件说明',
				width: '49%'
			}, {
				field : 'fileSize',
				width: '8%',
				title : '大小'
			}, {
				field: 'id',
				width: '12%',
				align:'center',
				templet: function(d){
					return '<a href="javascript:void(0)" class="layui-btn layui-btn-sm" onclick="downFile(\'' +
						d.fileUrl +
						'\',\'' +
						d.fileName +
						'\')">下载</a>';
			    },
				title : '操作'
			} ] ],
			id: 'logReload',
			page : true,
			response : {
				statusCode : 200//重新规定成功的状态码为 200，table 组件默认为 0
			},
			parseData : function(res) { //将原始数据解析成 table 组件所规定的数据
				if (res.status=="success") {
					return {
						"code" : 200, //解析接口状态
						"msg" : "", //解析提示文本
						"count" : res.total, //解析数据长度
						"data" : res.rows//解析数据列表
					};
				} else {
					
				}
			}
		});
	});
}

//附件下载
function downFile(url, name) {
	name = name.replace(/#/g, "%23");
	name = name.replace(/%/g, "%25");
	window.location.href = basePath + '/IAS4/sysFile/downFile.do?fileUrl=' + url +
		'&fileName=' + name;
}

function deleteFile(id,index) {
	layer.confirm('确认删除吗？', {
		  btn: ['确定', '取消'] //可以无限个按钮
		}, function(index, layero){
			$.ajax({
				type: "post",
				url: basePath + "/IAS4/sysFile/delete.do?id=" + id,
				dataType: "json",
				success: function(data) {
					if(data.status=="success"){
						layer.msg("删除成功");
					}
				},
				error: function(data) {
					layer.msg("系统错误！");
				}
			});
		}, function(index){
		  
		});
}

/**
 * 保存附件文件信息
 * @param busId
 * @param busType
 * @param userId
 * @param publicKey
 */
function saveFile(busId, busType, userId, publicKey) {
	var attachmentInfo = ""
	for(var i = 1; i < attachmentNum; i++) {
		var fileUrl = $("#fileUrl_" + i).val();
		var fileSize = $("#fileSize_" + i).val();
		var fileSub = $("#fileSub_" + i).val();
		var fileName = $("#fileName_" + i).val();
		var fileDesc = $("#fileDesc_" + i).val();
		if(attachmentInfo == "") {
			attachmentInfo += "{'fileName':'" + fileName + "','fileSize':'" + fileSize + "','busId':'" + busId +
				"','busType':'" + busType + "','userId':'" + userId + "','publicKey':'" + publicKey +
				"','fileSub':'" + fileSub + "','fileUrl':'" +
				fileUrl + "','fileDesc':'"+fileDesc+"','fileLive':'0'}";
		} else {
			attachmentInfo += "!{'fileName':'" + fileName + "','fileSize':'" + fileSize + "','busId':'" + busId +
			"','busType':'" + busType + "','userId':'" + userId + "','publicKey':'" + publicKey +
			"','fileSub':'" + fileSub + "','fileUrl':'" +
			fileUrl + "','fileDesc':'"+fileDesc+"','fileLive':'0'}";
		}
	}
	var data = {
		"params": attachmentInfo
	}
	$.ajax({
		type: "post",
		url: basePath + "/IAS4/sysFile/save.do",
		data: data,
		dataType: "json",
		async: false,
	});
}
