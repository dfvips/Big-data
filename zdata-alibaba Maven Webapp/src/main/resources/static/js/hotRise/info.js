/**
 * 
 */
$.namespace('alibaba.js.hotRise.info');

alibaba.js.hotRise.info.main = {
	id : getUrlParameter("offerId"),
	catId : getUrlParameter("catId"),
	init : function() {
		var main = this;
		main.initTable();
		main.initLine();
		main.initLine2();
	},
	initTable : function() {
		var main = this;
		layui.use([ 'table' ], function() {
			var table = layui.table;
			var form = layui.form;
			table.render({
				elem : '#table',
				url : context + 'hotRank/findByOfferId?offerId='+main.id+'&catId='+main.catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {

				},
				toolbar : '#Toolbar',
				height : 280,
				title : '详情列表',
				cols : [ [ {
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
					title : '时间',
					templet : function(d) {
						return d.crawlTime;
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
					field : 'count',
					title : '评价数',
					templet : function(d) {
						return d.count==null?"":d.count;
					}
				}, {
					field : 'price',
					title : '价格',
					templet : function(d) {
						return d.price;
					}
				}, {
					field : 'trade',
					title : '交易指数',
					templet : function(d) {
						return d.trade;
					}
				}, {
					field : 'flow',
					title : '流量指数',
					templet : function(d) {
						return d.flow;
					}
				}, {
					field : 'offerId',
					title : '宝贝Id',
					hide : true,
					templet : function(d) {
						return d.offerId;
					}
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
						'data' : res.data.list
					}
				}
			});
		});
	},	
	initLine:function(){
		var main = this;
		var res;
		$.ajax({
			url : context + 'hotRank/findByOfferId?offerId='+main.id+'&catId='+main.catId,
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
			url : context + 'hotRank/findByOfferId?offerId='+main.id+'&catId='+main.catId,
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
	}
}

$(document).ready(function() {
	alibaba.js.hotRise.info.main.init();
});