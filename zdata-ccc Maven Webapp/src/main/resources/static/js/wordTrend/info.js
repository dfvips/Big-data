/**
 * 
 */
$.namespace('ccc.js.wordTrend.info');

ccc.js.wordTrend.info.main = {
	wordId : getUrlParameter("wordId"),
	flag0:0,
	flag1:0,
	init : function() {
		var main = this;
		main.initTable();
		main.initLine();
	},
	initTable : function() {
		var main = this;
		layui.use([ 'table','element' ], function() {
			var table = layui.table;
			var form = layui.form;
			var element = layui.element;
			table.render({
				elem : '#table',
				url : context + 'wordTrend/findDetail?wordId='+main.wordId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {

				},
				toolbar : '#Toolbar',
				height : 240,
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
					width : 140,
					sort : true
				}, {
					field : 'clickNum',
					title : '点击指数',
					width : 140,
					sort : true,
				}, {
					field : 'ctr',
					title : '点击率',
					width : 140,
					sort : true
				}, {
					field : 'cvr',
					title : '转化率',
					width : 140,
					sort : true
				}, {
					field : 'competeValue',
					title : '竞争指数',
					width : 140,
					sort : true,
				}, {
					field : 'imprAvgBid',
					title : '市场平均出价',
					width : 140,
					sort : true,
					hide : true
				},{
					field : 'updateDate',
					title : '更新时间',
					width : 140,
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
				parseData : function(res) {
					let dt = res.data.list;
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
						'data' : res.data.list
					}
				}
			});
			element.on('tab(content)', function(data){
			    if(data.index==1){
			    	if(main.flag0==0){
				    	main.initLine2();
				    	main.flag0=1;
			    	}
			    }
			    if(data.index==2){
			    	if(main.flag1==0){
						main.initLine3();
				    	main.flag1=1;
			    	}
			    }
			});
		});
	},	
	initLine:function(){
		var main = this;
		var res;
		$.ajax({
			url : context + 'wordTrend/findDetail?wordId='+main.wordId,
			dataType : "json",
			async : false,
			type : "GET",
			success : function(data) {
				if (data.code == 200) {
					res = data.data.views;	
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
			url : context + 'wordTrend/findDetail?wordId='+main.wordId,
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
			url : context + 'wordTrend/findDetail?wordId='+main.wordId,
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
	ccc.js.wordTrend.info.main.init();
});