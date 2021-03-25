/**
 * 
 */
$.namespace('yundong.js.hotWordsRise.index');

yundong.js.hotWordsRise.index.main = {
	init : function() {
		var main = this;
		main.initCss();
		main.initJs();
		main.initzTree();
		main.initTable(18637);
		main.initHistory(18637);
		main.initCollect();
		main.initYoungTable(18637);
	},
	initYoungTable : function(catId){
		var main = this;
		layui.use([ 'table'], function() {
			var table = layui.table;
			table.render({
				elem : '#youngList',
				url : context + 'youngWord/find?state=1&catId='+catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {

				},
				toolbar : '#youngToolbar',
				height : 'full-200',
				title : '详情列表',
				cols : [ [ {
					type : 'checkbox',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'word',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.word + '" target="_blank">' + d.word + '</a>';
					}
				}, {
					field : 'catName',
					title : '类型'
				}, {
					field : 'youngValue',
					title : '倾斜程度指标',
					width : 200,
					sort : true
				}
				] ],
				page : false,
				response : {
					statusCode : 200
				},
				parseData : function(res) {
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : res.data
					}
				}
			});
			table.on('rowDouble(youngTable)', function (obj) {
				var data = obj.data;
				var url = context + 'youngWord/info?id='+data.id;
				layer.open({
				  title: '详情',
				  type: 2,
				  shade: [0],
				  area: ['1200px', '650px'],
				  content: url
				});
			});
			table.on('toolbar(youngTable)', function(obj){
			    var checkStatus = table.checkStatus(obj.config.id);
			    var data = checkStatus.data;
				if (data.length == 0) {
					layer.msg("请选择一条数据！");
					return;
				} else {
					if(obj.event =="tag"){
						var zTreeObj = $.fn.zTree.getZTreeObj("cateTree");
						var selectedNodes = zTreeObj.getSelectedNodes();
						for(var i=0;i<data.length;i++){
							$.ajax({
								url : context + "youngWord/deleteById",
								type : "POST",
								dataType : "json",
								data : {
									id : data[i].id
								},
								async : false,
								success : function(result) {
									if(result.code==200){
									}
								}
							});
						}
						if(selectedNodes.length==0){
							main.initYoungTable(18637);
						}else{
							main.initYoungTable(selectedNodes[0].catId);
						}
					}
				}
			});
		});
	},
	initTable : function(catId) {
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#hotList',
				url : context + 'hotWordsRise/find',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {
					riseDate : main.getDate(0),
					catId : catId
				},
				toolbar : '#hotToolbar',
				height : 'full-200',
				title : '详情列表',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'word',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.word + '" target="_blank">' + d.word + '</a>';
					}
				}, {
					field : 'catName',
					title : '类目'
				}, {
					field : 'pv',
					title : '搜索热度',
					sort : true
				}, {
					field : 'clickNum',
					title : '点击指数',
					sort : true
				}, {
					field : 'ctr',
					title : '点击率',
					sort : true
				}, {
					field : 'cvr',
					title : '转化率',
					sort : true
				}, {
					field : 'competeValue',
					title : '竞争指数',
					sort : true
				}, {
					field : 'imprAvgBid',
					title : '市场平均出价',
					sort : true
				}, {
					field : 'pvPercent',
					title : '搜索热度占比',
					sort : true
				}, {
					field : 'clickNumPercent',
					title : '点击指数占比',
					sort : true
				}, {
					field : 'riseDate',
					title : '上榜时间',
					sort : true,
					templet : function(d) {
						return d.riseDate.substring(0, 10);
					}
				}, {
					field : 'tag',
					title : '上榜天数',
					width : 140,
					sort : true
				}
				] ],
				id : 'hotReload',
				page : true,
				response : {
					statusCode : 200
				},
				parseData : function(res) {
					let dt = res.data;
					for (var i = 0; i < dt.length; i++) {
						dt[i].ctr = (dt[i].ctr * 100).toFixed(3) + "%";
						dt[i].cvr = (dt[i].cvr * 100).toFixed(3) + "%";
						dt[i].imprAvgBid = (dt[i].imprAvgBid / 1000).toFixed(3);
						dt[i].pvPercent = (dt[i].pvPercent * 100).toFixed(3) + "%";
						dt[i].clickNumPercent = (dt[i].clickNumPercent * 100).toFixed(3) + "%";
					}
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : dt
					}
				}
			});
			var $ = layui.$,
				active = {
					reload : function() {
						var data = $('#searchInfo').serializeObject();
						table.reload('hotReload', {
							page : {
								curr : 1 //重新从第 1 页开始
							},
							where : data
						}, 'data');
					}
				};
			$('.searchInfo .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('rowDouble(hotTable)', function (obj) {
				var data = obj.data;
				var url = context + 'hotWords/info?word='+data.word+'&riseDate='+data.riseDate.substring(0, 10)+'&catId='+data.catId;
				layer.open({
				  title: '详情',
				  type: 2,
				  shadeClose : true,
				  area: ['1200px', '650px'],
				  content: url
				});
			});
			table.on('toolbar(hotTable)', function(obj){
			    var checkStatus = table.checkStatus(obj.config.id);
			    var data = checkStatus.data;
				if (data.length == 0) {
					layer.msg("请选择一条数据！");
					return;
				} else {
					if (obj.event == 'collent') {
						$.ajax({
							url : context + "hotWordsCollect/save",
							type : "POST",
							dataType : "json",
							data : {
								wordRiseId : data[0].id,
								state : 1
							},
							async : false,
							success : function(result) {
								if (result.code == 200) {
									layer.msg("收藏成功！");
									main.initCollect();
								} else if (result.code == 400) {
									layer.msg(result.msg);
								}
							}
						});
					}else if(obj.event=='aliSearch'){
						window.open('https://p4psearch.1688.com/p4p114/p4psearch/offer.htm?keywords='+data[0].word, "_blank");
					}else if(obj.event=='tbSearch'){
						window.open('https://s.taobao.com/search?q='+data[0].word, "_blank");
					}else if(obj.event=='tmSearch'){
						window.open('https://s.taobao.com/search?q='+data[0].word+'&tab=mall', "_blank");
					}else if(obj.event=='pddSearch'){
						window.open('https://pifa.pinduoduo.com/search?word='+data[0].word, "_blank");
					}
				}
			});
		});
	},
	initCss : function() {
		var h = $(window).height() - 110;
		document.getElementById("cateTreeDiv").style.height = h + "px";
		document.getElementById("infoDiv").style.height = (h + 40) + "px";
	},
	initJs : function(){
		layui.use(['laydate','element'], function() {
			var laydate = layui.laydate;
			var element = layui.element;
			laydate.render({
			    elem: '#riseDate',
			    done: function(value, date){
					$('.search .layui-btn').click();
				}
			});
		});
	},
	initCollect : function() {
		var main = this;
		layui.use([ 'table'], function() {
			var table = layui.table;
			table.render({
				elem : '#collectList',
				url : context + 'hotWordsCollect/find?state=1',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize'
				},
				where : {

				},
				height : 'full-200',
				title : '历史收藏',
				toolbar : '#collectToolbar',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号',
					hide : true
				}, {
					field : 'word',
					title : '名称',
					templet : function(d) {
						return d.hotWordsRise.word;
					}
				}, {
					field : 'pv',
					title : '搜索指数',
					templet : function(d) {
						return d.hotWordsRise.pv;
					}
				}, {
					field : 'clickNum',
					title : '点击指数',
					templet : function(d) {
						return d.hotWordsRise.clickNum;
					}
				}, {
					field : 'competeValue',
					title : '竞争指数',
					templet : function(d) {
						return d.hotWordsRise.competeValue;
					}
				}, {
					field : 'collectDateTime',
					title : '收藏时间',
					width : 200,
					sort : true
				} ] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : 'collectReload',
				parseData : function(res) {
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : res.data
					}
				}
			});
			table.on('rowDouble(collectTable)', function(obj) {
				var data = obj.data;
				var url = context + 'wordTrend/info?wordId='+data.wordRiseId;
				layer.open({
					title : '详情',
					type : 2,
					shade : [ 0 ],
					area : [ '1200px', '650px' ],
					content : url
				});
			});
			table.on('toolbar(collectTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				if (data == null) {
					return;
				} else {
					if (obj.event === 'cancel') {
						$.ajax({
							url : context + "hotWordsCollect/cancel?id=" + data[0].id,
							type : "GET",
							dataType : "json",
							async : false,
							success : function(result) {
								if (result.code == 200) {
									layer.msg("取消成功！");
									main.initCollect();
								} else if (result.code == 400) {
									layer.msg(result.msg);
								}
							}
						});
					}
				}
			});
		});
		
	},
	initHistory : function(catId) {
		var main = this;
		var riseDate = $("#riseDate").val();
		if(riseDate==null||riseDate==""){
			riseDate = main.getDate(0);
			$("#riseDate").val(main.getDate(0));
		}
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#tableHistory',
				url : context + 'hotWordsRise/find',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize'
				},
				where : {
					catId : catId,
					riseDate: riseDate
				},
				toolbar : '#historyToolbar',
				height : 'full-200',
				title : '详情列表',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'word',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.word + '" target="_blank">' + d.word + '</a>';
					}
				}, {
					field : 'catName',
					title : '类目'
				}, {
					field : 'pv',
					title : '搜索热度',
					sort : true
				}, {
					field : 'clickNum',
					title : '点击指数',
					sort : true,
				}, {
					field : 'ctr',
					title : '点击率',
					sort : true
				}, {
					field : 'cvr',
					title : '转化率',
					sort : true
				}, {
					field : 'competeValue',
					title : '竞争指数',
					sort : true
				}, {
					field : 'imprAvgBid',
					title : '市场平均出价',
					sort : true
				}, {
					field : 'pvPercent',
					title : '搜索热度占比',
					sort : true
				}, {
					field : 'clickNumPercent',
					title : '点击指数占比',
					sort : true
				}, {
					field : 'riseDate',
					title : '上榜时间',
					sort : true,
					templet : function(d) {
						return d.riseDate.substring(0, 10);
					}
				}, {
					field : 'tag',
					title : '上榜天数',
					sort : true
				}
				] ],
				page : false,
				response : {
					statusCode : 200
				},
				id: 'wordReload',
				parseData : function(res) {
					let dt = res.data;
					for (var i = 0; i < dt.length; i++) {
						dt[i].ctr = (dt[i].ctr * 100).toFixed(3) + "%";
						dt[i].cvr = (dt[i].cvr * 100).toFixed(3) + "%";
						dt[i].imprAvgBid = (dt[i].imprAvgBid / 1000).toFixed(3);
						dt[i].pvPercent = (dt[i].pvPercent * 100).toFixed(3) + "%";
						dt[i].clickNumPercent = (dt[i].clickNumPercent * 100).toFixed(3) + "%";
					}
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : dt
					}
				}
			});
			table.on('rowDouble(hotTableHistory)', function (obj) {
				var data = obj.data;
				var url = context + 'hotWords/info?word='+data.word+'&riseDate='+data.riseDate.substring(0, 10)+'&catId='+data.catId;
				layer.open({
				  title: '详情',
				  type: 2,
				  shadeClose: true,
				  area: ['1200px', '650px'],
				  content: url
				});
			});
			table.on('toolbar(hotTableHistory)', function(obj){
			    var checkStatus = table.checkStatus(obj.config.id);
			    var data = checkStatus.data;
				if (data.length == 0) {
					layer.msg("请选择一条数据！");
					return;
				} else {
					if (obj.event=='collent') {
						$.ajax({
							url : context + "hotWordsCollect/save",
							type : "POST",
							dataType : "json",
							data : {
								wordRiseId : data[0].id,
								state : 1
							},
							async : false,
							success : function(result) {
								if (result.code == 200) {
									layer.msg("收藏成功！");
								} else if (result.code == 400) {
									layer.msg(result.msg);
								}
							}
						});
					}else if(obj.event=='aliSearch'){
						window.open('https://p4psearch.1688.com/p4p114/p4psearch/offer.htm?keywords='+data[0].word, "_blank");
					}else if(obj.event=='tbSearch'){
						window.open('https://s.taobao.com/search?q='+data[0].word, "_blank");
					}else if(obj.event=='tmSearch'){
						window.open('https://s.taobao.com/search?q='+data[0].word+'&tab=mall', "_blank");
					}else if(obj.event=='pddSearch'){
						window.open('https://pifa.pinduoduo.com/search?word='+data[0].word, "_blank");
					}
				}
			});
			var $ = layui.$,
				active = {
					reload : function() {
						var data = $('#search').serializeObject();
						//执行重载
						table.reload('wordReload', {
							page : {
								curr : 1 //重新从第 1 页开始
							},
							where : data
						}, 'data');
					}
				};
			$('.search .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	},
	//初始化树
	initzTree : function() {
		var main = this;
		var setting = {
			check : {
				enable : false
			},
			data : {
				key : {
					name : "catName"
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
			url : context + "goodsCate/findTree",
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
			main.initTable(treeNode.catId);
			main.initHistory(treeNode.catId);
			main.initYoungTable(treeNode.catId);
		}
	},
	getDate : function(num) {
		var date = new Date();
		date.setDate(date.getDate() - num); //获取3天前的日期
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
	}
}

$(document).ready(function() {
	yundong.js.hotWordsRise.index.main.init();
});