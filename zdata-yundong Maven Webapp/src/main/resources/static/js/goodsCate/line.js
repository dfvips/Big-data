/**
 * 
 */
$.namespace('yundong.js.goodsCate.line');

yundong.js.goodsCate.line.main = {
	catId : getUrlParameter("catId"),
	data : null,
	init: function(){
		var main = this;
		$.ajax({
			url : context + "hotGoods/findPieCatId?catId="+main.catId,
			type : "POST",
			dataType : "json",
			data : {
				
			},
			async : false,
			success : function(result) {
				if(result.code==200){
					main.data = result.data;
				}
			}
		});
//		console.log(main.data.xAxis[0].data);
		let html = "";
		let t=[];
		for (var i= 0; i < main.data.xAxis[0].data.length-1; i++) {
			let time = main.data.xAxis[0].data[i];
			time = time.replace('2020-','');
			t[i]=time;	
		}
		main.data.xAxis[0].data=t;
		for (var i = 0; i < main.data.legend.length; i++) {
			if(i%3==0){
				if(i!=0){
					html+='</div>';
				}
				if(i!=main.data.legend.length-1){
					html+='<div class="layui-row">';
				}
			}
			let id = 'main'+i;
			html+='<div class="layui-col-md4">';
			html+='<div id="'+id+'" style="width: 98%;height:400px;"></div>';
			html+='</div>';
		}
		$(".layui-card-body").html(html);
		for (var i = 0; i < main.data.legend.length; i++) {
			let id = 'main'+i;
			let dt = {data:{}};
			let tt=i+1;
			dt.data.legend=[main.data.legend[i]];
			dt.data.series=[main.data.series[i]];
//			main.data.xAxis[0].data=t;
			dt.data.xAxis=main.data.xAxis;
			main.initEchart(id,dt,tt);
		}
	},
	initEchart:function(id,result,tt){
		let arr = result.data.series[0].data;
		for (var i = 0; i < arr.length; i++) {
			arr[i] = arr[i]*100;
		}
		result.data.series[0].data = arr;
		let myChart = echarts.init(document.getElementById(id),'macarons');
		let option = {
		    title: {
		        text: tt
		    },
		    toolbox:{
		    	show:true,
		    	feature: {
                    restore: {
                        show: true
                    },
                    saveAsImage: {
                        show: true
                    }
                }
		    },
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
		        data: result.data.legend
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
		            data: result.data.xAxis[0].data,
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
		            interval: 0.5, 
		            splitArea : {show : false},
		            axisLabel: {  
                        show: true,  
                        
                        formatter: '{value}%'  
                    }
		        }
		    ],
		    series: result.data.series
		};
		myChart.setOption(option);
		myChart.on('legendselectchanged', function(obj) {
			window.open('https://mobile.yangkeduo.com/search_result.html?search_key='+obj.name, '_blank');
			var url = context + "hotGoods/info?goodsName=" + obj.name;
			layui.use('table', function() {
				layer.open({
					title : '详情',
					type : 2,
					shade : [ 0 ],
					area : [ '1200px', '650px' ],
					content : url
				});
			});
		});
	}
}

$(document).ready(function() {
	yundong.js.goodsCate.line.main.init();
});