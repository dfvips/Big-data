/**
 * 
 */
$.namespace('alibaba.js.cate.index');

alibaba.js.cate.index.main = {
	init : function() {
		var main = this;
		main.initzTree();
		main.initCss();
		main.initJs();
	},
	initJs : function(){
		layui.use(['form','laydate','element'], function() {
			var form = layui.form;
			var element = layui.element;
			var laydate = layui.laydate;
			laydate.render({
			    elem: '#crawlTime'
			});
			laydate.render({
			    elem: '#riseDate'
			});
			laydate.render({
			    elem: '#newsCrawlTime'
			});
		});
	},
	initCss : function(){
		var h = $(window).height() - 110;
		document.getElementById("cateTreeDiv").style.height = h + "px";
	},
	initRise : function(catId){
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#hotRiseList',
				url : context + 'hotRise/find?catId='+catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {

				},
				toolbar : '#hotRiseToolbar',
				height : 'full-230',
				title : '详情列表',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'imgUrl',
					width : 60,
					templet : function(d) {
						return '<img src="'+d.hotRank.imgUrl+'" style="width:30px;heigth:30px;"></img>';
					}
				}, {
					field : 'title',
					title : '标题',
					templet : function(d) {
						return '<a href="' + d.hotRank.sameGoodUrl + '" target="_blank">'+d.hotRank.title+'</a>';
					}
				}, {
					field : 'comName',
					title : '商家',
					templet : function(d) {
						return '<a href="' + d.hotRank.comUrl + '" target="_blank">' + d.hotRank.comName + '</a>';
					}
				}, {
					field : 'crawlTime',
					title : '时间',
					templet : function(d) {
						return d.hotRank.crawlTime;
					}
				}, {
					field : 'period',
					title : '统计周期',
					templet : function(d) {
						if(d.hotRank.period=="week"){
							return "最近7天";
						}else if(d.hotRank.period=="month"){
							return "最近30天";
						}
					}
				}, {
					field : 'count',
					title : '评价数',
					templet : function(d) {
						return d.hotRank.count==null?"":d.hotRank.count;
					}
				}, {
					field : 'price',
					title : '价格',
					templet : function(d) {
						return d.hotRank.price;
					}
				}, {
					field : 'trade',
					title : '交易指数',
					templet : function(d) {
						return d.hotRank.trade;
					}
				}, {
					field : 'flow',
					title : '流量指数',
					templet : function(d) {
						return d.hotRank.flow;
					}
				}, {
					field : 'offerId',
					title : '宝贝Id',
					hide : true,
					templet : function(d) {
						return d.hotRank.offerId;
					}
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : "hotRiseReload",
				parseData : function(res) {
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : res.data
					}
				}
			});
			table.on('rowDouble(hotRiseTable)', function (obj) {
				var data = obj.data.hotRank;
				var url = context + 'hotRise/info?offerId='+data.offerId+'&catId='+data.catId;
				layer.open({
				  title: '详情',
				  type: 2,
				  shade: [0],
				  area: ['1200px', '720px'],
				  content: url
				});
			});
			var $ = layui.$,
				active = {
					reload : function() {
						var data = $('#hotRiseForm').serializeObject();
						//执行重载
						table.reload('hotRiseReload', {
							page : {
								curr : 1 //重新从第 1 页开始
							},
							where : data
						}, 'data');
					}
				};
			$('.searchHotRise .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	},
	initHotTable : function(catId){
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#hotWordsList',
				url : context + 'hotWords/find?catId='+catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {

				},
				toolbar : '#hotWordsToolbar',
				height : 'full-230',
				title : '详情列表',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号',
					width : 60
				}, {
					field : 'ind',
					title : '搜索指数',
					sort : true
				}, {
					field : 'total',
					title : '全站商品数',
					sort : true
				}, {
					field : 'keyword',
					title : '关键词',
					templet : function(d) {
						return '<a href="' + d.url + '" target="_blank">'+d.keyword+'</a>';
					}
				}, {
					field : 'period',
					title : '统计周期',
					templet : function(d) {
						if(d.period=="week"){
							return "最近7天";
						}else if(d.period=="month"){
							return "最近30天";
						}
					}
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : "hotWordsReload",
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
						var data = $('#hotWordsForm').serializeObject();
						//执行重载
						table.reload('hotWordsReload', {
							page : {
								curr : 1 //重新从第 1 页开始
							},
							where : data
						}, 'data');
					}
				};
			$('.searchHotWords .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	},
	initNewTable : function(catId){
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#newWordsList',
				url : context + 'newWords/find?catId='+catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {

				},
				toolbar : '#newWordsToolbar',
				height : 'full-230',
				title : '详情列表',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号',
					width : 60
				}, {
					field : 'ind',
					title : '搜索指数',
					sort : true
				}, {
					field : 'total',
					title : '全站商品数',
					sort : true
				}, {
					field : 'keyword',
					title : '关键词',
					templet : function(d) {
						return '<a href="' + d.url + '" target="_blank">'+d.keyword+'</a>';
					}
				}, {
					field : 'period',
					title : '统计周期',
					templet : function(d) {
						if(d.period=="week"){
							return "最近7天";
						}else if(d.period=="month"){
							return "最近30天";
						}
					}
				}, {
					field : 'crawlTime',
					title : '时间'
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : "newWordsReload",
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
						var data = $('#newWordsForm').serializeObject();
						//执行重载
						table.reload('newWordsReload', {
							page : {
								curr : 1 //重新从第 1 页开始
							},
							where : data
						}, 'data');
					}
				};
			$('.searchNewWords .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	},
	initTable : function(catId){
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#hotRankList',
				url : context + 'hotRank/find?catId='+catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {

				},
				toolbar : '#hotRankToolbar',
				height : 'full-230',
				title : '详情列表',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'imgUrl',
					width : 60,
					templet : function(d) {
						return '<img src="'+d.imgUrl+'" style="width:30px;heigth:30px;"></img>';
					}
				}, {
					field : 'title',
					title : '标题',
					templet : function(d) {
						return '<a href="' + d.sameGoodUrl + '" target="_blank">'+d.title+'</a>';
					}
				}, {
					field : 'comName',
					title : '商家',
					templet : function(d) {
						return '<a href="' + d.comUrl + '" target="_blank">' + d.comName + '</a>';
					}
				}, {
					field : 'crawlTime',
					title : '时间'
				}, {
					field : 'period',
					title : '统计周期',
					templet : function(d) {
						if(d.period=="week"){
							return "最近7天";
						}else if(d.period=="month"){
							return "最近30天";
						}
					}
				}, {
					field : 'count',
					title : '评价数'
				}, {
					field : 'price',
					title : '价格'
				}, {
					field : 'trade',
					title : '交易指数'
				}, {
					field : 'flow',
					title : '流量指数'
				}, {
					field : 'offerId',
					title : '宝贝Id',
					hide : true
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : "hotRankReload",
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
						var data = $('#hotRankForm').serializeObject();
						//执行重载
						table.reload('hotRankReload', {
							page : {
								curr : 1 //重新从第 1 页开始
							},
							where : data
						}, 'data');
					}
				};
			$('.searchHotRank .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
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
				//onRightClick : onRightClick
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
			main.initTable(treeNode.catId);
			main.initRise(treeNode.catId);
			main.initNewTable(treeNode.catId);
			main.initHotTable(treeNode.catId);
			main.initRateTable(treeNode.catId);
		}
	},
	initRateTable : function(catId){
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#rateWordsList',
				url : context + 'rateWords/find?catId='+catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {

				},
				toolbar : '#rateWordsToolbar',
				height : 'full-230',
				title : '详情列表',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号',
					width : 60
				}, {
					field : 'rate',
					title : '搜索转化率',
					sort : true
				}, {
					field : 'total',
					title : '全站商品数',
					sort : true
				}, {
					field : 'keyword',
					title : '关键词',
					templet : function(d) {
						return '<a href="' + d.url + '" target="_blank">'+d.keyword+'</a>';
					}
				}, {
					field : 'period',
					title : '统计周期',
					templet : function(d) {
						if(d.period=="week"){
							return "最近7天";
						}else if(d.period=="month"){
							return "最近30天";
						}
					}
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : "rateWordsReload",
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
						var data = $('#rateWordsForm').serializeObject();
						//执行重载
						table.reload('rateWordsReload', {
							page : {
								curr : 1 //重新从第 1 页开始
							},
							where : data
						}, 'data');
					}
				};
			$('.searchRateWords .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	}
}

$(document).ready(function() {
	alibaba.js.cate.index.main.init();
});