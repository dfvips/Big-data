/**
 * 
 */
$.namespace('yundong.js.youngWord.info');

yundong.js.youngWord.info.main = {
	id : getUrlParameter("id"),
	init : function() {
		var main = this;
		main.initLine();
		main.initChart(0)
	},
	initLine : function(){
		var main = this;
		layui.use(['element'], function() {
			var element = layui.element;
			element.on('tab(content)', function(data){
				main.initChart(data.index);
			});
		});
	},
	initChart : function(i){
		var main = this;
		$.ajax({
			url : context + 'youngWord/findDetail?id='+main.id,
			dataType : "json",
			async : false,
			type : "GET",
			success : function(result) {
				if (result.code == 200) {
					var data = result.data;
					let myChart = echarts.init(document.getElementById("main"+i),'macarons');
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
							data : data.legend[i]
						},
						grid : {
							left : '3%',
							right : '4%',
							bottom : '3%',
							containLabel : true
						},
						xAxis : [
							{
								type : 'category',
								boundaryGap : false,
								data : data.xAxis[i].data,
								axisLabel : {
									show : true,
									rotate : 30,
									margin : 8
								}
							}
						],
						yAxis : [
							{
								type : 'value',
								splitArea : {
									show : false
								}
							}
						],
						series : data.series[i]
					};
					myChart.setOption(option);
				}
			}
		});
	}
}

$(document).ready(function() {
	yundong.js.youngWord.info.main.init();
});