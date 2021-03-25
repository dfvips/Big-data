/**
 * 
 */
$.namespace('ccc.js.hotWords.index');

ccc.js.hotWords.index.main = {
	init : function() {
		var main = this;
		main.initCss();
		main.initzTree();
		main.initTable(2933);
		main.initPie(2933);
		main.initAreaStack(2933);
	},
	initCss : function() {
		var h = $(window).height() - 110;
		document.getElementById("cateTreeDiv").style.height = h + "px";
	},
	//初始化树
	initzTree : function() {
		var main = this;
		var setting = {
			check : {
				enable : false
			},
			data : {
				key : {
					name : "catName"
				}
			},
			edit : {
				drag : {
					isMove : false,
					isCopy : false
				},
				enable : true,
				editNameSelectAll : true,
				showRemoveBtn : false,
				showRenameBtn : false,
			},
			callback : {
				onClick : zTreeOnClick
			},
			view : {
				showIcon : true,
				showLine : true
			}
		};
		$.ajax({
			url : context + "goodsCate/findTree",
			type : "POST",
			dataType : "json",
			data : {

			},
			success : function(result) {
				if (result.code == 200) {
					var zNodes = result.data;
					var zTreeObj = $.fn.zTree.init($("#cateTree"), setting, zNodes);
					zTreeObj.expandAll(true);
				}
			}
		});
		function zTreeOnClick(event, treeId, treeNode) {
			main.initTable(treeNode.catId);
		}
	},
	initPie: function(catId){
		var result = null;
		$.ajax({
			url : context + "hotWords/findByCatId?catId="+catId,
			dataType : "json",
			async : false,
			type : "GET",
			success : function(data) {
				if (data.code == 200) {
					result = data.data;
					console.log(result);
				}
			}
		});
		
		let myChart = echarts.init(document.getElementById('main'),'macarons');
		var legendData = [];
		var seriesData = [];
		var selected = {};
		var infos = result.reverse();
		for(var i=1;i<=infos.length;i++){
			var item = infos[i-1];
			legendData.push(item.word);
			var name = item.word;
			var num = item.clickNum;
			var obj = {
					name:name,
					value:num
			}
			seriesData.push(obj);
			selected[item.word] = i < 10;
		}
		var option = {
			tooltip: {
		        trigger: 'item',
		        formatter: '{a} <br/>{b} : {c} ({d}%)'
		    },
			legend: {
		        type: 'scroll',
		        orient: 'vertical',
		        right: 10,
		        top: 20,
		        bottom: 20,
		        data: legendData,
		        selected: selected,
		        formatter: function(params){
		        	return params;
		        }
		    },
			series : [
				{
					name: "热词",
					type: "pie",
					radius : '50%',
					center: ["40%", "53%"], 
					data : seriesData,
					itemStyle: {
					    normal: {
					        label: {
					            show: true,
					            formatter: function(v) {
					                return v.name.substring(0,5);
					            }
					        },
					        labelLine: {
					            show: true
					        }
					    }
					}
				},
				{
					name: "热词",
					type: "pie",
					radius : '50%',
					center: ["40%", "53%"], 
					data : seriesData,
					itemStyle: {
					    normal: {
					        label: {
					            show: true,
					            formatter: '{d}%',
					            position: "inner"
					        },
					        labelLine: {
					            show: true
					        }
					    }
					}
				}
			]
		};
		myChart.setOption(option);
		myChart.on('click', function (params) {
			$("#word").val(params.name.substr(2));
			$(".search button").click();
		});
	},
	initAreaStack:function(catId){
		
	},
	initTable: function(catId){
		var main = this;
		layui.use(['table','laydate','form'], function() {
			var table = layui.table;
			var form = layui.form;
			var laydate = layui.laydate;
			laydate.render({
			    elem: '#updateDate',
			    range: true
			});
			table.render({
				elem : '#wordList',
				url : context + 'hotWords/find?catId='+catId,
				request: {
				    pageName: 'pageNum',
				    limitName: 'pageSize' //每页数据量的参数名，默认：limit
				},
				limits : [100,500,1000,2000,3000,4000,5000],
				limit : 100 ,
				where : {
					
				},
				height : 280,
				title : '关键词列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号',
					width : 80,
				}, {
					field : 'word',
					title : '名称',
					templet : function(d){
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key='+d.word+'" target="_blank">'+d.word+'</a>';
					}
				}, {
					field : 'pv',
					title : '搜索热度',
					width : 120,
					sort  : true
				}, {
					field : 'clickNum',
					title : '点击热度',
					width : 120,
					sort  : true
				}, {
					field : 'ctr',
					title : '点击率',
					width : 100,
					sort  : true
				}, {
					field : 'cvr',
					title : '转化率',
					width : 100,
					sort  : true
				}, {
					field : 'competeValue',
					title : '竞争强度',
					width : 100,
					sort  : true
				}, {
					field : 'imprAvgBid',
					title : '市场平均出价',
					width : 100,
					sort  : true
				}, {
					field : 'updateDate',
					title : '时间',
					width : 150,
					sort  : true,
					templet : function(d){
						return d.updateDate.substring(0,10);
					}
				} ] ],
				page : true,
				response : {
					statusCode : 200
				},
				id: 'wordReload',
				parseData : function(res) { 
					let dt = res.data;
					for (var i = 0; i < dt.length; i++) {
						dt[i].ctr = (dt[i].ctr*100).toFixed(2)+"%";
						dt[i].cvr = (dt[i].cvr*100).toFixed(2)+"%";
						dt[i].imprAvgBid = (dt[i].imprAvgBid/1000).toFixed(2);
					}
					return {
						'code':res.code,
						'msg':res.msg,
						'count':res.count,
						'data':dt
					}
				}
			});
			var $ = layui.$,
				active = {
					reload : function() {
						var data = $('#search').serializeObject();
						//执行重载
						table.reload('wordReload', {
							page : {
								curr : 1 //重新从第 1 页开始
							},
							where : data
						}, 'data');
					}
				};
			$('.search .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	}
}

$(document).ready(function() {
	ccc.js.hotWords.index.main.init();
});