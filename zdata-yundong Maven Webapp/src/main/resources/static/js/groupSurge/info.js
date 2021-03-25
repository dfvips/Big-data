/**
 * 
 */
$.namespace('yundong.js.groupSurge.info');

yundong.js.groupSurge.info.main = {
	goodsName : decodeURI(getUrlParameter("goodsName")),
	init : function() {
		var main = this;
		main.initLine();
	},
	initLine : function() {
		var main = this;
		$.ajax({
			url : context + 'groupSurge/findDetail',
			dataType : "json",
			async : false,
			type : "POST",
			data : {
				goodsName : main.goodsName
			},
			success : function(result) {
				if (result.code == 200) {
					var legend = result.data.views.legend;
					var xAxis = result.data.views.xAxis;
					var series = result.data.views.series;
					for (var i = 0; i < legend.length; i++) {
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
								splitArea : {
									show : false
								}
							},
							series : series[i]
						};
						myChart.setOption(option);
					}
				}
			}
		});
	}
}


$(document).ready(function() {
	yundong.js.groupSurge.info.main.init();
});