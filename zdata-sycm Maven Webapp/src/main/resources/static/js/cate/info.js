/**
 * 
 */
$.namespace('sycm.js.cate.info');
sycm.js.cate.info.main = {
	catId : getUrlParameter("catId"),
	itemId : getUrlParameter("itemId"),
	init: function(){
		var main = this;
		main.initJs();
		main.initSaleTable();
		main.initSaleLine();
	},
	initCss : function(){
		
	},
	initJs : function() {
		var main = this;
		layui.use([ 'element', 'laydate', 'form' ], function() {
			var element = layui.element;
			var form = layui.form;
			var laydate = layui.laydate;
			element.on('tab(content)', function(data){
				switch (data.index) {
					case 0:
						main.initSaleTable();
						main.initSaleLine();
						break;
					case 1:
						main.initSearchTable();
						main.initSearchLine();
						break;
					case 2:
						main.initPurposeTable();
						main.initPurposeLine();
						break;
				}
			});
		});
	},
	initPurposeLine : function(){
		var main = this;
		$.ajax({
			url : context + 'hotPurpose/findLine',
			dataType : "json",
			async : false,
			type : "POST",
			data : {
				itemId : main.itemId,
				catId : main.catId
			},
			success : function(result) {
				if (result.code == 200) {
					$('#Purposeline').html("");
					var legend = result.data.legend;
					var xAxis = result.data.xAxis;
					var series = result.data.series;
					for (var i = 0; i < legend.length; i++) {
						$("#Purposeline").append('<div class="layui-col-md6">'+
							'<div id="mainPurpose'+i+'" style="width: 100%;height:400px;"></div>'+
							'</div>');
						let myChart = echarts.init(document.getElementById("mainPurpose" + i), 'macarons');
						let option = {
							tooltip : {
								trigger : 'axis',
								axisPointer : {
									type : 'cross',
									label : {
										backgroundColor : '#6a7985'
									}
								}
							},
							legend : {
								data : [legend[i]]
							},
							xAxis : {
								boundaryGap : false,
								data : xAxis[i].data,
								axisLabel : {
									show : true,
									rotate : 30,
									margin : 8
								}
							},
							yAxis : {
								
								type : 'value',
								splitArea : {
									show : false
								}
							},
							dataZoom : [
								{
									type : 'slider',
									show : true,
									realtime : true,
									start : 0,
									end : 100,
									handleSize : 8,
									textStyle : {
										color : "rgba(102,102,102,0)"
									}
								},
								{
									type : 'inside'
								}
							],
							series : {
								name : series[i].name,
								data : series[i].data,
								type : series[i].type,
								label: {
					                normal: {
					                    show: i==0?true:false,
					                    position: 'top'
					                }
					            }
							}
						};
						myChart.setOption(option);
					}
				}
			}
		});
	},
	initSearchLine : function(){
		var main = this;
		$.ajax({
			url : context + 'hotSearch/findLine',
			dataType : "json",
			async : false,
			type : "POST",
			data : {
				itemId : main.itemId,
				catId : main.catId
			},
			success : function(result) {
				if (result.code == 200) {
					$('#Searchline').html("");
					var legend = result.data.legend;
					var xAxis = result.data.xAxis;
					var series = result.data.series;
					for (var i = 0; i < legend.length; i++) {
						$("#Searchline").append('<div class="layui-col-md6">'+
							'<div id="mainSearch'+i+'" style="width: 100%;height:400px;"></div>'+
							'</div>');
						let myChart = echarts.init(document.getElementById("mainSearch" + i), 'macarons');
						let option = {
							tooltip : {
								trigger : 'axis',
								axisPointer : {
									type : 'cross',
									label : {
										backgroundColor : '#6a7985'
									}
								}
							},
							legend : {
								data : [legend[i]]
							},
							xAxis : {
								boundaryGap : false,
								data : xAxis[i].data,
								axisLabel : {
									show : true,
									rotate : 30,
									margin : 8
								}
							},
							yAxis : {
								type : 'value',
								splitArea : {
									show : false
								}
							},
							dataZoom : [
								{
									type : 'slider',
									show : true,
									realtime : true,
									start : 0,
									end : 100,
									handleSize : 8,
									textStyle : {
										color : "rgba(102,102,102,0)"
									}
								},
								{
									type : 'inside'
								}
							],
							series : {
								name : series[i].name,
								data : series[i].data,
								type : series[i].type,
								label: {
					                normal: {
					                    show: i==0?true:false,
					                    position: 'top'
					                }
					            }
							}
						};
						myChart.setOption(option);
					}
				}
			}
		});
	},
	initSaleLine : function(){
		var main = this;
		$.ajax({
			url : context + 'hotSale/findLine',
			dataType : "json",
			async : false,
			type : "POST",
			data : {
				itemId : main.itemId,
				catId : main.catId
			},
			success : function(result) {
				if (result.code == 200) {
					$('#Saleline').html("");
					var legend = result.data.legend;
					var xAxis = result.data.xAxis;
					var series = result.data.series;
					for (var i = 0; i < legend.length; i++) {
						$("#Saleline").append('<div class="layui-col-md4">'+
							'<div id="mainSale'+i+'" style="width: 100%;height:400px;"></div>'+
							'</div>');
						let myChart = echarts.init(document.getElementById("mainSale" + i), 'macarons');
						let option = {
							tooltip : {
								trigger : 'axis',
								axisPointer : {
									type : 'cross',
									label : {
										backgroundColor : '#6a7985'
									}
								}
							},
							legend : {
								data : [legend[i]]
							},
							xAxis : {
								boundaryGap : false,
								data : xAxis[i].data,
								axisLabel : {
									show : true,
									rotate : 30,
									margin : 8
								}
							},
							dataZoom : [
								{
									type : 'slider',
									show : true,
									realtime : true,
									start : 0,
									end : 100,
									handleSize : 8,
									textStyle : {
										color : "rgba(102,102,102,0)"
									}
								},
								{
									type : 'inside'
								}
							],
							yAxis : {
								type : 'value',
								splitArea : {
									show : false
								}
							},
							series : {
								name : series[i].name,
								data : series[i].data,
								type : series[i].type,
								label: {
					                normal: {
					                    show: i==0?true:false,
					                    position: 'top'
					                }
					            }
							}
						};
						myChart.setOption(option);
					}
				}
			}
		});
	},
	initPurposeTable : function(){
		var main = this;
		layui.use(['table'], function() {
			var table = layui.table;
			table.render({
				elem : '#hotPurposeList',
				url : context + 'hotPurpose/findDetail',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //???????????????????????????????????????limit
				},
				where : {
					catId : main.catId,
					itemId : main.itemId
				},
				toolbar : '#hotPurposeToolbar',
				height : '400',
				title : '?????????????????????',
				cols : [ [ {
					type : 'numbers',
					title : '??????'
				}, {
					field : 'pictUrl',
					title : '??????',
					width : 60,
					templet : function(d){
						return '<img style="width:30px;heigth:30px;" src="'+d.pictUrl+'" onclick="show_img(this)"/>'
					}
				}, {
					field : 'itemId',
					title : '??????Id',
					hide : true,
					width : 150
				}, {
					field : 'itemTitle',
					title : '????????????',
					templet : function(d){
						return '<a href="' + d.detailUrl + '" target="_blank">' + d.itemTitle + '</a>';
					}
				}, {
					field : 'updateTime',
					title : '??????',
					width : 120,
					sort : true,
					templet : function(d){
						return d.updateTime.substring(0,10);
					}
				}, {
					field : 'shopTitle',
					title : '??????',
					hide : true
				}, {
					field : 'tradeIndex',
					title : '????????????',
					width : 150,
					templet : function(d){
						var w=d.tradeIndex.indexOf(".");
						return d.tradeIndex.substring(0,w+3);
					}
				}, {
					field : 'cateRank',
					title : '??????',
					width : 150
				}, {
					field : 'cateRankCqc',
					title : '??????(?????????)',
					width : 120,
					sort : true
				}, {
					field : 'cltHits',
					title : '????????????',
					width : 120,
					sort : true
				}, {
					field : 'cartHits',
					title : '??????????????????',
					width : 120,
					sort : true
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : 'hotPurposeReload',
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
	initSearchTable : function(){
		var main = this;
		layui.use(['table'], function() {
			var table = layui.table;
			table.render({
				elem : '#hotSearchList',
				url : context + 'hotSearch/findDetail',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //???????????????????????????????????????limit
				},
				where : {
					catId : main.catId,
					itemId : main.itemId
				},
				toolbar : '#hotSearchToolbar',
				height : '400',
				title : '?????????????????????',
				cols : [ [ {
					type : 'numbers',
					title : '??????'
				}, {
					field : 'pictUrl',
					title : '??????',
					width : 60,
					templet : function(d){
						return '<img style="width:30px;heigth:30px;" src="'+d.pictUrl+'" onclick="show_img(this)"/>'
					}
				}, {
					field : 'itemId',
					title : '??????Id',
					hide : true,
					width : 150
				}, {
					field : 'itemTitle',
					title : '????????????',
					templet : function(d){
						return '<a href="' + d.detailUrl + '" target="_blank">' + d.itemTitle + '</a>';
					}
				}, {
					field : 'updateTime',
					title : '??????',
					width : 120,
					sort : true,
					templet : function(d){
						return d.updateTime.substring(0,10);
					}
				}, {
					field : 'shopTitle',
					title : '??????',
					hide : true
				}, {
					field : 'tradeIndex',
					title : '????????????',
					width : 150
				}, {
					field : 'cateRank',
					title : '??????',
					width : 120,
					sort : true
				}, {
					field : 'seIpvUvHits',
					title : '????????????',
					width : 120,
					sort : true
				}, {
					field : 'uvIndex',
					title : '????????????',
					width : 120,
					sort : true
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : 'hotSearchReload',
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
	initSaleTable : function(){
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#hotSaleList',
				url : context + 'hotSale/findDetail',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //???????????????????????????????????????limit
				},
				where : {
					catId : main.catId,
					itemId : main.itemId
				},
				toolbar : '#hotSaleToolbar',
				height : '400',
				title : '??????????????????',
				cols : [ [ {
					type : 'numbers',
					title : '??????'
				}, {
					field : 'pictUrl',
					title : '??????',
					width : 60,
					templet : function(d){
						return '<img style="width:30px;heigth:30px;" src="'+d.pictUrl+'" onclick="show_img(this)"/>'
					}
				}, {
					field : 'itemId',
					title : '??????Id',
					hide : true,
					width : 150
				}, {
					field : 'itemTitle',
					title : '????????????',
					templet : function(d){
						return '<a href="' + d.detailUrl + '" target="_blank">' + d.itemTitle + '</a>';
					}
				}, {
					field : 'updateTime',
					title : '??????',
					sort : true,
					templet : function(d){
						return d.updateTime.substring(0,10);
					}
				}, {
					field : 'shopTitle',
					title : '??????',
					hide : true
				}, {
					field : 'tradeGrowthRange',
					title : '??????????????????',
					width : 150,
					templet : function(d){
						var result = d.tradeGrowthRange*100+"";
						var w=result.indexOf(".");
						return result.substring(0,w+3)+"%";
					}
				}, {
					field : 'tradeGrowthRangeCrc',
					title : '??????????????????(?????????)',
					width : 150,
					templet : function(d){
						var result = d.tradeGrowthRange*100+"";
						var w=result.indexOf(".");
						return result.substring(0,w+3)+"%";
					}
				}, {
					field : 'cateRank',
					title : '??????',
					width : 120,
					sort : true
				}, {
					field : 'cateRankCqc',
					title : '??????(?????????)',
					width : 120,
					sort : true
				}, {
					field : 'tradeIndex',
					title : '????????????',
					width : 120,
					sort : true,
					templet : function(d){
						var w=d.payRateIndex.indexOf(".");
						return d.payRateIndex.substring(0,w+3);
					}
				}, {
					field : 'payRateIndex',
					title : '??????????????????',
					width : 120,
					sort : true,
					templet : function(d){
						var w=d.payRateIndex.indexOf(".");
						return d.payRateIndex.substring(0,w+3);
					}
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : 'hotSaleReload',
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
	}
}

$(document).ready(function() {
	sycm.js.cate.info.main.init();
});