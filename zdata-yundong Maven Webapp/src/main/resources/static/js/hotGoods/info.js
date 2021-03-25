/**
 * 
 */
$.namespace('yundong.js.hotGoods.info');

yundong.js.hotGoods.info.main = {
	goodsName : decodeURI(getUrlParameter("goodsName")),
	init : function() {
		var main = this;
		main.initTable();
		main.initJs();
		main.initLine();
		main.initLine2();
		main.initLine3();
	},
	initJs : function(){
		layui.use(['element','laydate'], function() {
			var element = layui.element;
			var laydate = layui.laydate;
			laydate.render({
				elem: '#updateDateGoods',
				done: function(value, date, endDate){
					$('.searchGoods .layui-btn').click();
				}
					
			});
			laydate.render({
				elem: '#updateDateSurge',
				done: function(value, date, endDate){
					$('.searchSurge .layui-btn').click();
				}
			});
			laydate.render({
				elem: '#updateDateRelated',
				done: function(value, date, endDate){
					$('.searchRelated .layui-btn').click();
				}
			});
			laydate.render({
				elem: '#updateDateRelatedGroup',
				done: function(value, date, endDate){
					$('.searchRelatedGroup .layui-btn').click();
				}
			});
		});
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
	},
	initRelatedGroup : function(catId){
		var main = this;
		$("#updateDateRelatedGroup").val(main.getDate(3));
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#relatedGroupList',
				url : context + 'groupSurge/findRelated',
				request : {
					
				},
				totalRow: true,
				where : {
					catId:catId,
					goodsName:main.goodsName,
					updateDate:main.getDate(3)
				},
				toolbar : '#relatedGroupToolbar',
				height : 450,
				title : '详情列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号',
					totalRowText: '合计'
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.goodsName + '" target="_blank">' + d.goodsName + '</a>';
					}
				}, {
					field : 'related',
					title : '相似度',
					width : 120,
					sort : true
				}, {
					field : 'orderNum',
					title : '订单指数',
					width : 150,
					totalRow: true,
					sort : true
				}, {
					field : 'visitorNum',
					title : '访客指数',
					width : 150,
					totalRow: true,
					sort : true
				}, {
					field : 'updateDate',
					title : '更新时间',
					width : 150,
					sort : true,
					templet : function(d) {
						return d.updateDate.substring(0,10);
					}
				}
				] ],
				page : false,
				id : "relatedGroupReload",
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
				},
				done: function(res, curr, count){
					
				}
			});
			var $ = layui.$,
				active = {
					reload : function() {
						var data = $('#searchRelatedGroup').serializeObject();
						//执行重载
						table.reload('relatedGroupReload', {
							where : data
						}, 'data');
					}
				};
			$('.searchRelatedGroup .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	},
	initRelated : function(catId){
		var main = this;
		$("#updateDateRelated").val(main.getDate(3));
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#relatedList',
				url : context + 'hotGoods/findRelated',
				request : {
					
				},
				totalRow: true,
				where : {
					catId:catId,
					updateDate:main.getDate(3),
					goodsName:main.goodsName
				},
				toolbar : '#relatedToolbar',
				height : 450,
				title : '相似度列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号',
					totalRowText: '合计'
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.goodsName + '" target="_blank">' + d.goodsName + '</a>';
					}
				}, {
					field : 'related',
					title : '相似度',
					width : 120,
					sort : true
				}, {
					field : 'rowNum',
					title : '类目排名',
					width : 120,
					sort : true
				}, {
					field : 'orderNum',
					title : '订单指数',
					width : 150,
					totalRow: true,
					sort : true
				}, {
					field : 'visitorNum',
					title : '访客指数',
					width : 150,
					totalRow: true,
					sort : true
				}, {
					field : 'updateDate',
					title : '更新时间',
					width : 150,
					sort : true,
					templet : function(d) {
						return d.updateDate.substring(0,10);
					}
				}
				] ],
				page : false,
				id : "relatedReload",
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
				},
				done: function(res, curr, count){
					
				}
			});
			var $ = layui.$,
				active = {
					reload : function() {
						var data = $('#searchRelated').serializeObject();
						//执行重载
						table.reload('relatedReload', {
							where : data
						}, 'data');
					}
				};
			$('.searchRelated .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	},
	initHotSurge : function(catId){
		var main = this;
		$("#updateDateSurge").val(main.getDate(0));
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#hotSurgeList',
				url : context + 'groupSurge/findListByCatId',
				limit : 50,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				totalRow: true,
				where : {
					catId:catId,
					updateDate:main.getDate(0)
				},
				toolbar : '#hotSurgeToolbar',
				height : 450,
				title : '详情列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号',
					totalRowText: '合计'
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.goodsName + '" target="_blank">' + d.goodsName + '</a>';
					}
				}, {
					field : 'orderNum',
					title : '订单指数',
					width : 150,
					totalRow: true,
					sort : true
				}, {
					field : 'visitorNum',
					title : '访客指数',
					width : 150,
					totalRow: true,
					sort : true
				}, {
					field : 'traning',
					title : '转化率',
					width : 150,
					sort : true,
					templet : function(d) {
						return (d.traning*100).toFixed(2)+'%';
					}
				}, {
					field : 'updateDate',
					title : '更新时间',
					width : 150,
					sort : true,
					templet : function(d) {
						return d.updateDate.substring(0,10);
					}
				}
				] ],
				page : true,
				id : "hotSurgeReload",
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
				},
				done: function(res, curr, count){
					
				}
			});
			var $ = layui.$,
				active = {
					reload : function() {
						var data = $('#searchSurge').serializeObject();
						//执行重载
						table.reload('hotSurgeReload', {
							where : data
						}, 'data');
					}
				};
			$('.searchSurge .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	},
	initHotGoods : function(catId){
		var main = this;
		$("#updateDateGoods").val(main.getDate(0));
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#hotGoodsList',
				url : context + 'hotGoods/findListByCatId',
				limit : 50,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				totalRow: true,
				where : {
					catId:catId,
					updateDate:main.getDate(0)
				},
				toolbar : '#hotGoodsToolbar',
				height : 450,
				title : '详情列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号',
					totalRowText: '合计'
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.goodsName + '" target="_blank">' + d.goodsName + '</a>';
					}
				}, {
					field : 'rowNum',
					title : '类目排名',
					width : 120,
					sort : true
				}, {
					field : 'orderNum',
					title : '订单指数',
					width : 150,
					totalRow: true,
					sort : true
				}, {
					field : 'visitorNum',
					title : '访客指数',
					width : 150,
					totalRow: true,
					sort : true
				}, {
					field : 'traning',
					title : '转化率',
					width : 150,
					sort : true,
					templet : function(d) {
						return (d.traning*100).toFixed(2)+'%';
					}
				}, {
					field : 'updateDate',
					title : '更新时间',
					width : 150,
					sort : true,
					templet : function(d) {
						return d.updateDate.substring(0,10);
					}
				}
				] ],
				page : true,
				id : "hotGoodsReload",
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
				},
				done: function(res, curr, count){
					
				}
			});
			var $ = layui.$,
				active = {
					reload : function() {
						var data = $('#searchGoods').serializeObject();
						//执行重载
						table.reload('hotGoodsReload', {
							where : data
						}, 'data');
					}
				};
			$('.searchGoods .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		})
	},
	initTable : function() {
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#hotList',
				url : context + 'hotGoods/findDetail',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {
					goodsName:main.goodsName
				},
				limit: 50,
				toolbar : '#hotToolbar',
				height : 280,
				title : '详情列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.goodsName + '" target="_blank">' + d.goodsName + '</a>';
					}
				}, {
					field : 'rowNum',
					title : '类目排名',
					width : 120,
					sort : true
				}, {
					field : 'orderNum',
					title : '订单指数',
					width : 150,
					sort : true
				}, {
					field : 'visitorNum',
					title : '访客指数',
					width : 150,
					sort : true
				}, {
					field : 'traning',
					title : '转化率',
					width : 150,
					sort : true,
					templet : function(d) {
						return (d.traning*100).toFixed(2)+'%';
					}
				}, {
					field : 'updateDate',
					title : '更新时间',
					width : 150,
					sort : true,
					templet : function(d) {
						return d.updateDate.substring(0,10);
					}
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				parseData : function(res) {
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : res.data.list
					}
				},
				done: function(res, curr, count){
					if(res.code==200&&res.data.length>0){
						main.initHotGoods(res.data[0].catId);
						main.initHotSurge(res.data[0].catId);
						main.initRelated(res.data[0].catId);
						main.initRelatedGroup(res.data[0].catId);
					}
				}
			});
		});
	},	
	initLine:function(){
		var main = this;
		var res;
		$.ajax({
			url : context + 'hotGoods/findDetail',
			dataType : "json",
			async : false,
			type : "POST",
			data:{
				goodsName:main.goodsName
			},
			success : function(data) {
				if (data.code == 200) {
					res = main.result = data.data.views;	
				}
			}
		});
		var l = res.legend;
    	var a  = [];
    	a[0] = l[0];
    	res.legend=a;
		var series = res.series;
    	var s = [];
    	s[0]=series[0];
    	res.series=s;
    	var arr =  res.xAxis[0].data;
    	for (var i = 0; i < arr.length; i++) {
    		arr[i]=arr[i].replace("2020-", "");
    	}
    	res.xAxis[0].data=arr;
		let myChart = echarts.init(document.getElementById("main"),'macarons');
		let option = {
			    tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'cross',
			            label: {
			                backgroundColor: '#6a7985'
			            }
			        }
			    },
			    legend: {
			        data: res.legend
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis: [
			        {
			            type: 'category',
			            boundaryGap: false,
			            data: res.xAxis[0].data,
			            axisLabel: {
			                show: true,
			                rotate: 30,
			                margin: 8
			            }
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            splitArea : {show : false}
			        }
			    ],
			    series: res.series
			};
			myChart.setOption(option);
	},
	initLine2:function(){
		var main = this;
		var res;
		$.ajax({
			url : context + 'hotGoods/findDetail',
			dataType : "json",
			async : false,
			type : "POST",
			data:{
				goodsName:main.goodsName
			},
			success : function(data) {
				if (data.code == 200) {
					res = main.result = data.data.views;	
				}
			}
		});
		var l = res.legend;
    	var a  = [];
    	a[0] = l[1];
    	res.legend=a;
		var series = res.series;
    	var s = [];
    	s[0]=series[1];
    	res.series=s;
    	var arr =  res.xAxis[0].data;
    	for (var i = 0; i < arr.length; i++) {
    		arr[i]=arr[i].replace("2020-", "");
    	}
    	res.xAxis[0].data=arr;
		let myChart = echarts.init(document.getElementById("main2"),'macarons');
		let option = {
			    tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'cross',
			            label: {
			                backgroundColor: '#6a7985'
			            }
			        }
			    },
			    legend: {
			        data: res.legend
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis: [
			        {
			            type: 'category',
			            boundaryGap: false,
			            data: res.xAxis[0].data,
			            axisLabel: {
			                show: true,
			                rotate: 30,
			                margin: 8
			            }
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            splitArea : {show : false}
			        }
			    ],
			    series: res.series
			};
			myChart.setOption(option);
	},
	initLine3:function(){
		var main = this;
		var res;
		$.ajax({
			url : context + 'hotGoods/findDetail',
			dataType : "json",
			async : false,
			type : "POST",
			data:{
				goodsName:main.goodsName
			},
			success : function(data) {
				if (data.code == 200) {
					res = main.result = data.data.views;	
				}
			}
		});
		var l = res.legend;
    	var a  = [];
    	a[0] = l[2];
    	res.legend=a;
		var series = res.series;
    	var s = [];
    	s[0]=series[2];
    	res.series=s;
    	var arrr = res.series[0].data;
    	for (var i = 0; i < arrr.length; i++) {
    		arrr[i]=(arrr[i]*100).toFixed(2);
    	}
    	res.series[0].data=arrr;
    	var arr =  res.xAxis[0].data;
    	for (var i = 0; i < arr.length; i++) {
    		arr[i]=arr[i].replace("2020-", "");
    	}
    	res.xAxis[0].data=arr;
		let myChart = echarts.init(document.getElementById("main3"),'macarons');
		let option = {
			    tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'cross',
			            label: {
			                backgroundColor: '#6a7985'
			            }
			        }
			    },
			    legend: {
			        data: res.legend
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis: [
			        {
			            type: 'category',
			            boundaryGap: false,
			            data: res.xAxis[0].data,
			            axisLabel: {
			                show: true,
			                rotate: 30,
			                margin: 8
			            }
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            splitArea : {show : false},
			            interval: 0.5,  
			            axisLabel: {  
                            show: true,  
                            formatter: '{value}%'  
                        }
			        
			        }
			    ],
			    series: res.series
			};
			myChart.setOption(option);
	}
}

$(document).ready(function() {
	yundong.js.hotGoods.info.main.init();
});