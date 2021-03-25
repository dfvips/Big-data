/**
 * 
 */
$.namespace('yundong.js.hotWords.info');

yundong.js.hotWords.info.main = {
	word :  decodeURI(getUrlParameter("word")),
	riseDate :  decodeURI(getUrlParameter("riseDate")),
	catId : decodeURI(getUrlParameter("catId")),
	init : function() {
		var main = this;
		main.initJs();
		main.initTable();
		main.initSameTable();
		main.initLine();
		main.initLine2();
		main.initLine3();
	},
	initJs : function(){
		var main = this;
		layui.use(['element','laydate'], function() {
			var element = layui.element;
			var laydate = layui.laydate;
			laydate.render({
			    elem: '#updateDate',
			    done: function(value, date){
					$('.search .layui-btn').click();
				}
			});
		});
		$("#updateDate").val(main.riseDate);
	},
	initTable : function() {
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#table',
				url : context + 'hotWords/findInfo',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {
					word : main.word
				},
				toolbar : '#Toolbar',
				height : 280,
				title : '详情列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'word',
					title : '名称'
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
				},{
					field : 'pvPercent',
					title : '搜索热度占比',
					sort : true
				},{
					field : 'clickPercent',
					title : '点击指数占比',
					sort : true
				},{
					field : 'updateDate',
					title : '更新时间',
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
					let dt = res.data;
					for (var i = 0; i < dt.length; i++) {
						dt[i].ctr = (dt[i].ctr*100).toFixed(3)+"%";
						dt[i].cvr = (dt[i].cvr*100).toFixed(3)+"%";
						dt[i].pvPercent = (dt[i].pvPercent*100).toFixed(3)+"%";
						dt[i].clickPercent = (dt[i].clickPercent*100).toFixed(3)+"%";
						dt[i].imprAvgBid = (dt[i].imprAvgBid/1000).toFixed(3);
					}
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : res.data
					}
				}
			});
		});
	},	
	initSameTable : function() {
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#sameList',
				url : context + 'hotWords/findSame',
				request : {
					
				},
				where : {
					word : main.word,
					catId : main.catId,
					updateDate : main.riseDate
				},
				toolbar : '#sameToolbar',
				height : 'full-200',
				title : '相似度词列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'word',
					title : '名称'
				}, {
					field : 'same',
					title : '相似度',
					sort : true
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
				},{
					field : 'pvPercent',
					title : '搜索热度占比',
					sort : true
				},{
					field : 'clickPercent',
					title : '点击指数占比',
					sort : true
				},{
					field : 'updateDate',
					title : '更新时间',
					sort : true,
					templet : function(d) {
						return d.updateDate.substring(0,10);
					}
				}
				] ],
				page : false,
				response : {
					statusCode : 200
				},
				id : 'sameReload',
				parseData : function(res) {
					let dt = res.data;
					for (var i = 0; i < dt.length; i++) {
						dt[i].ctr = (dt[i].ctr*100).toFixed(3)+"%";
						dt[i].cvr = (dt[i].cvr*100).toFixed(3)+"%";
						dt[i].pvPercent = (dt[i].pvPercent*100).toFixed(3)+"%";
						dt[i].clickPercent = (dt[i].clickPercent*100).toFixed(3)+"%";
						dt[i].imprAvgBid = (dt[i].imprAvgBid/1000).toFixed(3);
					}
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
						var data = $('#search').serializeObject();
						//执行重载
						table.reload('sameReload', {
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
	initLine:function(){
		var main = this;
		var res;
		$.ajax({
			url : context + 'hotWords/findDetail?word='+main.word+'&pageNum=1&pageSize=10',
			dataType : "json",
			async : false,
			type : "GET",
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
			url : context + 'hotWords/findDetail?word='+main.word+'&pageNum=1&pageSize=10',
			dataType : "json",
			async : false,
			type : "GET",
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
			url : context + 'hotWords/findDetail?word='+main.word+'&pageNum=1&pageSize=10',
			dataType : "json",
			async : false,
			type : "GET",
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
			            axisLabel: {  
                            show: true,  
                            formatter: '{value}'  
                        }
			        
			        }
			    ],
			    series: res.series
			};
			myChart.setOption(option);
	}
}

$(document).ready(function() {
	yundong.js.hotWords.info.main.init();
});