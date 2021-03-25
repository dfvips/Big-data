/**
 * 
 */
$.namespace('yundong.js.hotGoods.line');

yundong.js.hotGoods.line.main = {
	catId : decodeURI(getUrlParameter("catId")),
	goodsName : decodeURI(getUrlParameter("goodsName")),
	riseDate : decodeURI(getUrlParameter("riseDate")),
	init : function() {
		var main = this;
		main.initLines();
	},
	initLines : function() {
		var main = this;
		$.ajax({
			url : context + 'hotRise/findLine',
			dataType : "json",
			async : false,
			type : "POST",
			data : {
				goodsName : main.goodsName,
				riseDate : main.riseDate,
				catId : main.catId
			},
			success : function(result) {
				debugger;
				if (result.code == 200) {
					//画图
					var legend = result.data.legend;
					var xAxis = result.data.xAxis;
					var series = result.data.series;
					for (var i = 0; i < legend.length; i++) {
						$("#line").append('<div class="layui-col-md4">'+
							'<div id="main'+i+'" style="width: 100%;height:400px;"></div>'+
							'</div>');
						let myChart = echarts.init(document.getElementById("main" + i), 'macarons');
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
								interval:0.005,
								splitArea : {
									show : false
								}
							},
							series : series[i]
						};
						myChart.setOption(option);
						myChart.on('legendselectchanged', function(obj) {
							window.open('https://mobile.yangkeduo.com/search_result.html?search_key='+obj.name, '_blank');
						});
					}
				}
			}
		});
	}
}

$(document).ready(function() {
	yundong.js.hotGoods.line.main.init();
});