/**
 * 
 */
$.namespace('sycm.js.cate.main');
sycm.js.cate.main.main = {
	init : function() {
		var main = this;
		main.initCss();
		main.initJs();
		main.initzTree();
	},
	initJs : function() {
		var main = this;
		layui.use([ 'element', 'laydate', 'form' ], function() {
			var element = layui.element;
			var form = layui.form;
			var laydate = layui.laydate;
			laydate.render({
				elem : '#updateTime',
				done: function(value, date){
					$('.searchHot .layui-btn').click();
				}
			});
			laydate.render({
				elem : '#updateTimeSoar',
				done: function(value, date){
					$('.searchSoar .layui-btn').click();
				}
			});
			var layid = location.hash.replace(/^#content=/, '');
			if(layid==""||layid==null){
				location.hash = 'content=0';
				main.initHot(121390006);
			}else{
				switch (layid) {
					case "0":
						main.initHot(121390006);
						break;
					case "1":
						main.initSoar(121390006);
						break;
				}
				element.tabChange('content', layid);	
			}
			element.on('tab(content)', function(data){
				var zTreeObj = $.fn.zTree.getZTreeObj("cateTree");
				var selectedNodes = zTreeObj.getSelectedNodes();
				var catId = 121390006;
				if(selectedNodes.length!=0){
					catId = selectedNodes[0].catId;
				}
				switch (data.index) {
					case 0:
						main.initHot(catId);
						break;
					case 1:
						main.initSoar(catId);
						break;
				}
				location.hash = 'content='+ this.getAttribute('lay-id');
			});
		});
	},
	initCss : function() {
		var h = $(window).height() - 108;
		document.getElementById("cateTreeDiv").style.height = h + "px";
	},
	getDate : function(num) {
		var date = new Date();
		date.setDate(date.getDate() - num);
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		if (month < 10) {
			month = '0' + month;
		}
		var day = date.getDate() - 1;
		if (day < 10) {
			day = '0' + day;
		}
		return year + "-" + month + "-" + day;
	},
	initSoar : function(catId){
		var main = this;
		var updateTimeSoar = $("#updateTimeSoar").val();
		if(updateTimeSoar==null||updateTimeSoar==""){
			updateTimeSoar = main.getDate(2);
			$("#updateTimeSoar").val(main.getDate(2));
		}
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#soarList',
				url : context + 'searchWordSoar/findNew',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				limit: 90,
				where : {
					catId : catId,
					updateTime : updateTimeSoar
				},
				toolbar : '#soarToolbar',
				height : 'full-250',
				title : '搜索词热搜榜列表',
				cols : [ [ {
					type : 'radio'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'searchWord',
					title : '关键词',
					templet : function(d){
						return '<a href="https://s.taobao.com/search?q='+d.searchWord+'" target="_blank">' + d.searchWord + '</a>';
					}
				}, {
					field : 'catName',
					title : '类目'
				}, {
					field : 'seIpvUvHits',
					title : '搜索人气',
					templet : function(d){
						return parseInt(d.seIpvUvHits);
					}
				}, {
					field : 'seRiseRate',
					title : '搜索增长幅度'
				}, {
					field : 'clickHits',
					title : '点击人气',
					templet : function(d){
						return parseInt(d.clickHits);
					}
				}, {
					field : 'clickRate',
					title : '点击率'
				}, {
					field : 'rank',
					title : '飙升排名'
				}, {
					field : 'orderNum',
					title : '订单量'
				}, {
					field : 'p4pRefPrice',
					title : '市场参考价'
				}, {
					field : 'payRate',
					title : '支付转化率'
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : 'soarReload',
				parseData : function(res) {
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : res.data
					}
				}
			});
			var $ = layui.$,
			active = {
				reload : function() {
					var data = $('#searchSoar').serializeObject();
					table.reload('soarReload', {
						where : data
					}, 'data');
				}
			};
			$('.searchSoar .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('toolbar(soarTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				if(data.length==0){
					layer.msg("请选择一条数据！");
				}else{
					switch (obj.event) {
						case 'aliSearch':
							window.open('https://s.1688.com/selloffer/offer_search.htm?keywords='+data[0].code, "_blank");
						break;
						case 'ddSearch':
							window.open('https://mobile.yangkeduo.com/search_result.html?search_key='+data[0].searchWord, "_blank");
						break;
						case 'qsSearch':
							copyText(data[0].searchWord, function (){
								window.open('https://mms.pinduoduo.com/exp/tools/dataAnalysis', "_blank");
							});
						break;
					}

				}
			});
		});
	},
	initHot : function(catId){
		var main = this;
		var updateTime = $("#updateTime").val();
		if(updateTime==null||updateTime==""){
			updateTime = main.getDate(2);
			$("#updateTime").val(main.getDate(2));
		}
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#hotList',
				url : context + 'searchWordHot/findNew',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				limit: 90,
				where : {
					catId : catId,
					updateTime : updateTime
				},
				toolbar : '#hotToolbar',
				height : 'full-250',
				title : '搜索词热搜榜列表',
				cols : [ [ {
					type : 'radio'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'searchWord',
					title : '关键词',
					templet : function(d){
						return '<a href="https://s.taobao.com/search?q='+d.searchWord+'" target="_blank">' + d.searchWord + '</a>';
					}
				}, {
					field : 'catName',
					title : '类目'
				}, {
					field : 'seIpvUvHits',
					title : '搜索人气',
					templet : function(d){
						return parseInt(d.seIpvUvHits);
					}
				}, {
					field : 'clickHits',
					title : '点击人气',
					templet : function(d){
						return parseInt(d.clickHits);
					}
				}, {
					field : 'clickRate',
					title : '点击率'
				}, {
					field : 'rank',
					title : '搜索排名'
				}, {
					field : 'orderNum',
					title : '订单量'
				}, {
					field : 'p4pRefPrice',
					title : '市场参考价'
				}, {
					field : 'payRate',
					title : '支付转化率'
				}, {
					field : 'tmClickRate',
					title : '天猫点击率'
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : 'hotReload',
				parseData : function(res) {
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : res.data
					}
				}
			});
			var $ = layui.$,
			active = {
				reload : function() {
					var data = $('#searchHot').serializeObject();
					table.reload('hotReload', {
						where : data
					}, 'data');
				}
			};
			$('.searchHot .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('toolbar(hotTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				if(data.length==0){
					layer.msg("请选择一条数据！");
				}else{
					switch (obj.event) {
						case 'aliSearch':
							window.open('https://s.1688.com/selloffer/offer_search.htm?keywords='+data[0].code, "_blank");
						break;
						case 'ddSearch':
							window.open('https://mobile.yangkeduo.com/search_result.html?search_key='+data[0].searchWord, "_blank");
						break;
						case 'qsSearch':
							copyText(data[0].searchWord, function (){
								window.open('https://mms.pinduoduo.com/exp/tools/dataAnalysis', "_blank");
							});
						break;
					}
				}
				
			
			});
		});
	},
	initzTree : function() {
		var main = this;
		var setting = {
			check : {
				enable : false
			},
			data : {
				key : {
					name : "catName"
				},
				simpleData : {
					enable : true,
					idKey : "catId",
					pIdKey : "catParent"
				}
			},

			edit : {
				drag : {
					isMove : false,
					isCopy : false
				},
				enable : true,
				editNameSelectAll : true,
				showRemoveBtn : false,
				showRenameBtn : false,
			},
			callback : {
				onClick : zTreeOnClick
			},
			view : {
				showIcon : true,
				showLine : true
			}
		};
		$.ajax({
			url : context + "cate/findTree",
			type : "POST",
			dataType : "json",
			data : {

			},
			success : function(result) {
				if (result.code == 200) {
					var zNodes = result.data;
					var zTreeObj = $.fn.zTree.init($("#cateTree"), setting, zNodes);
					zTreeObj.expandAll(true);
				}
			}
		});
		function zTreeOnClick(event, treeId, treeNode) {
			var layid = location.hash.replace(/^#content=/, '');
			debugger;
			switch (layid) {
				case "0":
					main.initHot(treeNode.catId);
					break;
				case "1":
					main.initSoar(treeNode.catId);
					break;
			}
		}
	}
}

$(document).ready(function() {
	sycm.js.cate.main.main.init();
});