/**
 * 
 */
$.namespace('yundong.js.hotTrend.info');

yundong.js.hotTrend.info.main = {
	hotRiseId : getUrlParameter("hotRiseId"),
	init : function() {
		var main = this;
		main.initTable();
	},
	initTable : function() {
		var main = this;
		layui.use(['table'], function() {
			var table = layui.table;
			table.render({
				elem : '#trendList',
				url : context + 'hotTrend/findByHotRiseId?hotRiseId='+main.hotRiseId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {

				},
				toolbar : '#trendToolbar',
				title : '详情列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'updateDate',
					title : '更新时间',
					sort : true,
					templet : function(d) {
						return d.updateDate.substring(0,10);
					}
				}, {
					field : 'groupPrice',
					title : '成团价格',
					width : 140,
					sort : true
				}, {
					field : 'normalPrice',
					title : '正常价格',
					width : 140,
					sort : true
				}, {
					field : 'comment',
					title : '宝贝评论总数',
					width : 140,
					sort : true
				}, {
					field : 'sell',
					title : '销量',
					width : 140,
					sort : true
				}, {
					field : 'sumSell',
					title : '店铺总销量',
					width : 140,
					sort : true
				}, {
					field : 'goodNum',
					title : '店铺宝贝总数',
					width : 140,
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
		});
	},	
	initLine:function(){
		var main = this;
		var res;
		$.ajax({
			url : context + 'hotTrend/findDetail?word='+main.word+'&pageNum=1&pageSize=10',
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
			url : context + 'hotTrend/findDetail?word='+main.word+'&pageNum=1&pageSize=10',
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
			url : context + 'hotTrend/findDetail?word='+main.word+'&pageNum=1&pageSize=10',
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
	yundong.js.hotTrend.info.main.init();
});