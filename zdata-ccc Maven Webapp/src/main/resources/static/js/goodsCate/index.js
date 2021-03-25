/**
 * 
 */
$.namespace('ccc.js.goodsCate.index');

ccc.js.goodsCate.index.main = {
	funcId : getUrlParameter("funcId"),
	flagOne : false,
	flagTwo : false,
	flagThree : false,
	tabIndex : 0,
	init : function() {
		var main = this;
		main.initCss();
		main.initJs();
		main.initzTree();
		main.initPie(3099);
		main.initEcharts(3099);
		main.initTable(3099);
	},
	initJs : function(){
		var main = this;
		layui.use(['element', 'laydate', 'form' ], function() {
			var element = layui.element;
			var laydate = layui.laydate;
			var form = layui.form;
			laydate.render({
				elem : '#updateDate',
				range : true
			});
			laydate.render({
				elem : '#updateDateThree',
				range : false
			});
			laydate.render({
				elem : '#riseDate',
				range : false
			});
			laydate.render({
				elem : '#groupRiseDate',
				range : false
			});
			laydate.render({
				elem : '#processUpdateDate',
				range : false
			});
			element.on('tab(content)', function(data) {
				location.hash = 'content='+ this.getAttribute('lay-id');
				main.tabIndex = data.index;
				if (data.index == 1) {
					if (!main.flagOne) {
						main.initRiseHistory(3099);
						main.initRiseThree(3099);
						main.initThreeHot(3099);
						main.initCollect();
						main.flagOne = true;
					}
				} else if (data.index == 2) {
					if (!main.flagTwo) {
						main.initLine(3099);
						main.initLine2(3099);
						main.initLine3(3099);
						main.flagTwo = true;
					}
				} else if(data.index == 3) {
					if(!main.flagThree){
						main.initGroupTable(3099);
						main.flagThree = true;
					}
				} else if(data.index == 4){
					if(!main.flagFour){
						main.initProcessTable(3099);
						main.flagFour = true;
					}
				}
				//element.tabChange('content', this.getAttribute('lay-id'));
			});
		});
	},
	initProcessTable : function(catId){
		var main = this;
		layui.use(['table','form'], function() {
			var table = layui.table;
			var form = layui.form;
			table.render({
				elem : '#processList',
				url : context + 'hotGoods/findProgress?catId=' + catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {
					updateDate : main.getDate(0)
				},
				toolbar : '#processToolbar',
				height : 'full-200',
				title : '排名幅度列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.goodsName + '" target="_blank">' + d.goodsName + '</a>';
					}
				}, {
					field : 'catName',
					title : '类目',
					width : 150
				}, {
					field : 'orderNum',
					title : '订单指数',
					width : 120,
					sort : true
				}, {
					field : 'visitorNum',
					title : '访客指数',
					width : 120,
					sort : true
				}, {
					field : 'updateDate',
					title : '时间',
					width : 200,
					sort : true
				}, {
					field : 'processNum',
					title : '幅度',
					width : 200,
					sort : true
				}
				] ],
				page : false,
				response : {
					statusCode : 200
				},
				id : 'processReload',
				parseData : function(res) {
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
						var riseDate = $("#processUpdateDate").val();
						if(riseDate==null||riseDate==""){
							layer.msg("请选择查询时间");
							return;
						}
						var data = $('#processSearch').serializeObject();
						//执行重载
						table.reload('processReload', {
							where : data
						}, 'data');
					}
				};
			$('.processSearch .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('rowDouble(processTable)', function(obj){
			  var url = context + "hotGoods/info?goodsName=" + obj.data.goodsName;
				layer.open({
					title : '详情',
					type : 2,
					shade : [ 0 ],
					area : [ '1200px', '650px' ],
					content : url
				});
			});
		});
	},
	initCss : function() {
		var h = $(window).height() - 108;
		var h1 = $(window).height() - 70;
		document.getElementById("cateTreeDiv").style.height = h + "px";
		document.getElementById("resultDiv").style.height = h1 + "px";
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
				onClick : zTreeOnClick,
				onDblClick: zTreeOnDblClick
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
		
		function zTreeOnDblClick(event, treeId, treeNode){
			var tabId = main.funcId + treeNode.catId + "";
			var title = treeNode.catName + "增长趋势";
			var url = context + "goodsCate/line?catId=" + treeNode.catId;
			var icon = "layui-icon-chart"
			//打开Tab
			openParentTab(tabId, title, url, icon);
		}
		
		function zTreeOnClick(event, treeId, treeNode) {
			if(main.tabIndex==0){
				main.initTable(treeNode.catId);
				if(!treeNode.isParent){
					main.initEcharts(treeNode.catId);
					main.initPie(treeNode.catId);
				}
			}else if(main.tabIndex==1){
				main.initRiseHistory(treeNode.catId);
				main.initRiseThree(treeNode.catId);
				main.initThreeHot(treeNode.catId);
			}else if(main.tabIndex==2){
				main.initLine(treeNode.catId);
				main.initLine2(treeNode.catId);
				main.initLine3(treeNode.catId);
			}else if(main.tabIndex==3){
				main.initGroupTable(treeNode.catId);
			}else if(main.tabIndex==4){
				main.initProcessTable(treeNode.catId);
			}
		}
	},
	initTable : function(catId) {
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#hotList',
				url : context + 'hotGoods/findListByCatId?catId=' + catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {

				},
				height : 240,
				title : '榜单列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.goodsName + '" target="_blank">' + d.goodsName + '</a>';
					}
				}, {
					field : 'num',
					title : '上榜链接数',
					width : 120
				}, {
					field : 'rowNum',
					title : '类目排名',
					width : 120
				}, {
					field : 'orderNum',
					title : '订单指数',
					width : 120,
					sort : true
				}, {
					field : 'visitorNum',
					title : '访客指数',
					width : 120,
					sort : true
				}, {
					field : 'traning',
					title : '转化',
					width : 100,
					sort : true
				}, {
					field : 'updateDate',
					title : '时间',
					width : 150,
					sort : true,
					templet : function(d) {
						return d.updateDate.substring(0, 10);
					}
				} ] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : 'hotReload',
				parseData : function(res) {
					var dt = res.data;
					for (var i = 0; i < dt.length; i++) {
						if (i > 0) {
							if (dt[i].goodsName == dt[i - 1].goodsName) {
								dt[i].orderNumInc = (((dt[i].orderNum - dt[i - 1].orderNum) / dt[i - 1].orderNum) * 100).toFixed(2) + "%";
								dt[i].visitorNumInc = (((dt[i].visitorNum - dt[i - 1].visitorNum) / dt[i - 1].visitorNum) * 100).toFixed(2) + "%";
							} else {
								dt[i].orderNumInc = '';
								dt[i].visitorNumInc = '';
							}
						} else {
							dt[i].orderNumInc = '';
							dt[i].visitorNumInc = '';
						}
					}
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : dt
					}
				}
			});
			var $ = layui.$,
				active = {
					reload : function() {
						var data = $('#search').serializeObject();
						//执行重载
						table.reload('hotReload', {
							where : data
						}, 'data');
					}
				};
			$('.search .layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			//监听行双击事件
			table.on('rowDouble(hotTable)', function(obj){
			  var url = context + "hotGoods/info?goodsName=" + obj.data.goodsName;
				layer.open({
					title : '详情',
					type : 2,
					shade : [ 0 ],
					area : [ '1200px', '650px' ],
					content : url
				});
			});
		});
	},
	initLine : function(catId) {
		var main = this;
		var res;
		$.ajax({
			url : context + 'trend/findByCatId?catId=' + catId,
			dataType : "json",
			async : false,
			type : "GET",
			success : function(data) {
				if (data.code == 200) {
					res = main.result = data.data;
				}
			}
		});
		var l = res.legend;
		var a = [];
		a[0] = l[0];
		res.legend = a;
		var series = res.series;
		var s = [];
		s[0] = series[0];
		res.series = s;
		var arr = res.xAxis[0].data;
		for (var i = 0; i < arr.length; i++) {
			arr[i] = arr[i].replace("2020-", "");
		}
		res.xAxis[0].data = arr;
		let myChart = echarts.init(document.getElementById("mainn1"), 'macarons');
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
				data : res.legend
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
					data : res.xAxis[0].data,
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
			series : res.series
		};
		myChart.setOption(option);
	},
	initLine2 : function(catId) {
		var main = this;
		var res;
		$.ajax({
			url : context + 'trend/findByCatId?catId=' + catId,
			dataType : "json",
			async : false,
			type : "GET",
			success : function(data) {
				if (data.code == 200) {
					res = main.result = data.data;
				}
			}
		});
		var l = res.legend;
		var a = [];
		a[0] = l[1];
		res.legend = a;
		var series = res.series;
		var s = [];
		s[0] = series[1];
		res.series = s;
		var arr = res.xAxis[0].data;
		for (var i = 0; i < arr.length; i++) {
			arr[i] = arr[i].replace("2020-", "");
		}
		res.xAxis[0].data = arr;
		let myChart = echarts.init(document.getElementById("mainn2"), 'macarons');
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
				data : res.legend
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
					data : res.xAxis[0].data,
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
			series : res.series
		};
		myChart.setOption(option);
	},
	initLine3 : function(catId) {
		var main = this;
		var res;
		$.ajax({
			url : context + 'trend/findByCatId?catId=' + catId,
			dataType : "json",
			async : false,
			type : "GET",
			success : function(data) {
				if (data.code == 200) {
					res = data.data;
				}
			}
		});
		var l = res.legend;
		var a = [];
		a[0] = l[2];
		res.legend = a;
		var series = res.series;
		var s = [];
		s[0] = series[2];
		res.series = s;
		var arr = res.xAxis[0].data;
		for (var i = 0; i < arr.length; i++) {
			arr[i] = arr[i].replace("2020-", "");
		}
		res.xAxis[0].data = arr;
		let myChart = echarts.init(document.getElementById("mainn3"), 'macarons');
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
				data : res.legend
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
					data : res.xAxis[0].data,
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
			series : res.series
		};
		myChart.setOption(option);
	},
	initRiseHistory : function(catId) {
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#historyList',
				url : context + 'hotRise/find?catId='+catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {
					riseDate : '2020-08-08'
				},
				toolbar : '#historyToolbar',
				height : 'full-320',
				title : '新上榜列表',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.goodsName + '" target="_blank">' + d.goodsName + '</a>';
					}
				}, {
					field : 'orderNum',
					title : '订单指数',
					width : 105,
					sort : true
				}, {
					field : 'visitorNum',
					title : '访客指数',
					width : 105,
					sort : true
				}, {
					field : 'riseDate',
					title : '上榜时间',
					width : 105,
					sort : true
				}, {
					field : 'translation',
					title : '转化率',
					width : 105,
					sort : true,
					hide : true
				}, {
					field : 'visitorPercent',
					title : '访客占比',
					width : 105,
					sort : true,
					hide : true
				}, {
					field : 'orderPercent',
					title : '订单占比',
					width : 105,
					sort : true,
					hide : true
				}, {
					field : 'tag',
					title : '连续上榜天数',
					width : 150,
					sort : true
				}
				] ],
				page : false,
				response : {
					statusCode : 200
				},
				id : 'historyReload',
				parseData : function(res) {
					let dt = res.data;
					for (var i = 0; i < dt.length; i++) {
						let obj = dt[i];
						obj.riseDate = obj.riseDate.substring(0, 10);
						obj.orderPercent = (obj.orderPercent * 100).toFixed(2) + '%';
						obj.translation = (obj.translation * 100).toFixed(2) + '%';
						obj.visitorPercent = (obj.visitorPercent * 100).toFixed(2) + '%';
						dt[i] = obj;
					}
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : dt
					}
				},
				done : function() {
					var div = document.getElementById("historyDiv");
					var table_main = div.getElementsByClassName('layui-table-main'); //通过class获取table_main
					if (table_main != null && table_main.length > 0) {
						var table = table_main[0].getElementsByClassName('layui-table'); //通过class获取table
						if (table != null && table.length > 0) {
							var trs = table[0].querySelectorAll("tr");
							for (var i = 0; i < trs.length; i++) {
								var obj = trs[i];
								var tag = obj.childNodes[9].innerText;
								if (tag < 3) {
									obj.style.color = 'rgba(102,102,102)';
									obj.style.backgroundColor = 'rgba(150,222,232)';
								} else if (tag < 6) {
									obj.style.color = 'rgba(102,102,102)';
									obj.style.backgroundColor = 'rgba(63,177,227,.6)';
								} else if (tag < 9) {
									obj.style.color = 'rgba(102,102,102)';
									obj.style.backgroundColor = 'rgba(63,177,227)';
								} else {
									obj.style.color = 'rgba(102,102,102)';
									obj.style.backgroundColor = 'rgba(60, 160, 190)';
								}
							}
						}
					}
				}
			});
			//头工具栏事件
			table.on('toolbar(historyTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				if (data.length == 0) {
					layer.msg("请选择一条数据！");
					return;
				} else {
					if (obj.event === 'collent') {
						$.ajax({
							url : context + "hotCollect/save",
							type : "POST",
							dataType : "json",
							data : {
								hotRiseId : data[0].id,
								state : 1
							},
							async : false,
							success : function(result) {
								if (result.code == 200) {
									layer.msg("收藏成功！");
									main.initCollect();
								} else if (result.code == 400) {
									layer.msg(result.msg);
								}
							}
						});
					}
				}
			});
			table.on('rowDouble(historyTable)', function(obj) {
				var data = obj.data;
				var url = context + "hotGoods/info?goodsName=" + data.goodsName;
				layer.open({
					title : '详情',
					type : 2,
					shade : [ 0 ],
					area : [ '1200px', '650px' ],
					content : url,
				});
			});
			//监听表格排序问题
			table.on('sort(historyTable)', function(obj) {
				var div = document.getElementById("historyDiv");
				var table_main = div.getElementsByClassName('layui-table-main'); //通过class获取table_main
				if (table_main != null && table_main.length > 0) {
					var table = table_main[0].getElementsByClassName('layui-table'); //通过class获取table
					if (table != null && table.length > 0) {
						var trs = table[0].querySelectorAll("tr");
						for (var i = 0; i < trs.length; i++) {
							var obj = trs[i];
							var tag = obj.childNodes[9].innerText;
							if (tag < 3) {
								obj.style.color = 'rgba(102,102,102)';
								obj.style.backgroundColor = 'rgba(150,222,232)';
							} else if (tag < 6) {
								obj.style.color = 'rgba(102,102,102)';
								obj.style.backgroundColor = 'rgba(63,177,227,.6)';
							} else if (tag < 9) {
								obj.style.color = 'rgba(102,102,102)';
								obj.style.backgroundColor = 'rgba(63,177,227)';
							} else {
								obj.style.color = 'rgba(102,102,102)';
								obj.style.backgroundColor = 'rgb(60, 160, 190)';
							}
						}
					}
				}
			});
			var $ = layui.$,
				active = {
					reload : function() {
						var data = $('#historySearch').serializeObject();
						//执行重载
						table.reload('historyReload', {
							where : data,
						}, 'data');
					},
					trend : function() {
						var riseDate = $("#riseDate").val();
						if(riseDate==null||riseDate==""){
							layer.msg("请选择查询时间");
							return;
						}
						var zTreeObj = $.fn.zTree.getZTreeObj("cateTree");
						var selectedNodes = zTreeObj.getSelectedNodes();
						var url = context + "hotGoods/line?";
						if(selectedNodes.length==0){
							url += $('#historySearch').serialize();
						}else{
							url += $('#historySearch').serialize()+"&catId="+selectedNodes[0].catId;
						}
						layer.open({
							title : '线图统计(统计信息：热销排行榜超过一天的数据)',
							type : 2,
							shade : [ 0 ],
							area : [ '1200px', '650px' ],
							content : url
						});
					}
				};
			$('.historySearch .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});
	},
	//在榜三天，历史数据
	initThreeHot : function(catId) {
		var main = this;
		$("#updateDateThree").val(main.getDate(0));
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#threeHotList',
				url : context + 'hotGoods/findHot',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {
					catId : catId,
					updateDate : main.getDate(0)
				},
				height : 'full-260',
				title : '榜单列表',
				cols : [ [ {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.goodsName + '" target="_blank">' + d.goodsName + '</a>';
					}
				}, {
					field : 'orderNum',
					title : '订单指数',
					width : 120,
					sort : true
				}, {
					field : 'rowNum',
					title : '类目排名',
					width : 120,
					sort : true
				}, {
					field : 'visitorNum',
					title : '访客指数',
					width : 120,
					sort : true
				}, {
					field : 'updateDate',
					title : '时间',
					width : 150,
					sort : true,
					templet : function(d) {
						return d.updateDate.substring(0, 10);
					}
				} ] ],
				page : false,
				response : {
					statusCode : 200
				},
				id : 'threeHotReload',
				parseData : function(res) {
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
					var data = $('#threeHotSearch').serializeObject();
					//执行重载
					table.reload('threeHotReload', {
						where : data,
					}, 'data');
				}
			};
			$('.threeHotSearch .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('rowDouble(threeHotTable)', function(obj) {
				var data = obj.data;
				var url = context + "hotGoods/info?goodsName=" + data.goodsName;
				layer.open({
					title : '详情',
					type : 2,
					shade : [ 0 ],
					area : [ '1200px', '650px' ],
					content : url
				});
			});
		});
	},
	initRiseThree : function(catId) {
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#threeDayList',
				url : context + 'hotRise/find?catId=' + catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {
					riseDate : main.getDate(2)
				},
				toolbar : '#threeDayToolbar',
				height : 'full-220',
				title : '榜单列表',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.goodsName + '" target="_blank">' + d.goodsName + '</a>';
					}
				}, {
					field : 'orderNum',
					title : '订单指数',
					width : 120,
					sort : true
				}, {
					field : 'visitorNum',
					title : '访客指数',
					width : 120,
					sort : true
				}, {
					field : 'riseDate',
					title : '上榜时间',
					width : 120,
					sort : true
				}, {
					field : 'translation',
					title : '转化率',
					width : 120,
					sort : true
				}, {
					field : 'visitorPercent',
					title : '访客占比',
					width : 120,
					sort : true
				}, {
					field : 'orderPercent',
					title : '订单占比',
					width : 120,
					sort : true
				}, {
					field : 'tag',
					title : '连续上榜天数',
					width : 120,
					sort : true
				}
				] ],
				page : false,
				response : {
					statusCode : 200
				},
				id : 'threeDayReload',
				parseData : function(res) {
					var data = [];
					for (var i = 0; i < res.data.length; i++) {
						let obj = res.data[i];
						obj.riseDate = obj.riseDate.substring(0, 10);
						obj.orderPercent = (obj.orderPercent * 100).toFixed(2) + '%';
						obj.translation = (obj.translation * 100).toFixed(2) + '%';
						obj.visitorPercent = (obj.visitorPercent * 100).toFixed(2) + '%';
						//连续上榜三天，并且订单数大于150单
						if (obj.tag == 3) {
							data.push(res.data[i]);
						}
					}
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : data
					}
				}
			});
			table.on('rowDouble(threeDayTable)', function(obj) {
				var data = obj.data;
				var url = context + "hotGoods/info?goodsName=" + data.goodsName;
				layer.open({
					title : '详情',
					type : 2,
					shade : [ 0 ],
					area : [ '1200px', '650px' ],
					content : url
				});
			});
			//头工具栏事件
			table.on('toolbar(threeDayTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				if (data == null) {
					return;
				} else {
					if (obj.event === 'collent') {
						$.ajax({
							url : context + "hotCollect/save",
							type : "POST",
							dataType : "json",
							data : {
								hotRiseId : data[0].id,
								state : 1
							},
							async : false,
							success : function(result) {
								if (result.code == 200) {
									layer.msg("收藏成功！");
									main.initCollect();
								} else if (result.code == 400) {
									layer.msg(result.msg);
								}
							}
						});

					}
				}
			});
		});
	},
	initGroupTable : function(catId){
		var main = this;
		layui.use(['table','form'], function() {
			var table = layui.table;
			var form = layui.form;
			table.render({
				elem : '#groupRiseList',
				url : context + 'groupRise/find?catId=' + catId,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {
					
				},
				toolbar : '#groupRiseToolbar',
				height : 'full-200',
				title : '拼团飙升列表',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						if(d.goodUrl!=null){
							return '<a href="' + d.goodUrl + '" target="_blank">' + d.groupSurge.goodsName + '</a>';
						}else{
							return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.groupSurge.goodsName + '" target="_blank">' + d.groupSurge.goodsName + '</a>';
						}
					}
				}, {
					field : 'catName',
					title : '类目',
					width : 120,
					sort : true,
					templet : function(d) {
						return d.groupSurge.catName;
					}
					
				}, {
					field : 'isTag',
					title : '标记',
					width : 110,
					templet: '#isTagTmp'
				}, {
					field : 'orderNum',
					title : '订单指数',
					width : 120,
					sort : true,
					templet : function(d) {
						return d.groupSurge.orderNum;
					}
				}, {
					field : 'visitorNum',
					title : '访客指数',
					width : 120,
					sort : true,
					templet : function(d) {
						return d.groupSurge.visitorNum;
					}
				}, {
					field : 'riseDate',
					title : '上榜时间',
					width : 200,
					sort : true
				}
				] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : 'groupRiseReload',
				parseData : function(res) {
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
						var data = $('#groupRiseSearch').serializeObject();
						//执行重载
						table.reload('groupRiseReload', {
							where : data
						}, 'data');
					},
					trend : function() {
						var groupRiseDate = $("#groupRiseDate").val();
						if(groupRiseDate==null||groupRiseDate==""){
							layer.msg("请选择查询时间");
							return;
						}
						var zTreeObj = $.fn.zTree.getZTreeObj("cateTree");
						var selectedNodes = zTreeObj.getSelectedNodes();
						var url = context + "groupSurge/line?";
						if(selectedNodes.length==0){
							url += $('#groupRiseSearch').serialize();
						}else{
							url += $('#groupRiseSearch').serialize()+"&catId="+selectedNodes[0].catId;
						}
						layer.open({
							title : '线图统计(统计信息：上拼团飙升榜超过一天的数据)',
							type : 2,
							shade : [ 0 ],
							area : [ '1200px', '700px' ],
							content : url
						});
					}
				};
			$('.groupRiseSearch .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			form.on('switch(isTag)', function(obj){
				var id = $(this).attr('mid');
				var isTag=obj.elem.checked?1:0;
				$.ajax({
					url : context + "groupRise/updateTag",
					type : "post",
					dataType : "json",
					data : {
						isTag : isTag,
						id : id
					},
					async : false,
					success : function(result) {
						if(result.code==200){
							layer.msg("操作成功！");
							var data = $('#groupRiseSearch').serializeObject();
							//执行重载
							table.reload('groupRiseReload', {
								where : data
							}, 'data');
						}
					}
				});
			});
			table.on('rowDouble(groupRiseTable)', function(obj) {
				var data = obj.data;
				var url = context + "groupSurge/info?goodsName=" + data.groupSurge.goodsName;
				layer.open({
					title : '详情',
					type : 2,
					shade : [ 0 ],
					area : [ '1200px', '550px' ],
					content : url
				});
			});
			table.on('toolbar(groupRiseTable)', function(obj){
				var data = table.checkStatus(obj.config.id).data;
				if(data.length==0){
					layer.msg("请选择一条数据！")
				}else{
					if(obj.event=="setUrl"){
						layer.prompt({
						  formType: 2,
						  value: data[0].goodUrl,
						  title: '请宝贝地址',
						  area: ['300px', '100px']
						}, function(value, index, elem){
							delete  data[0].groupSurge
						    data[0].goodUrl = value;
						  	$.ajax({
								url : context + "groupRise/save",
								type : "POST",
								dataType : "json",
								data : data[0],
								async : false,
								success : function(result) {
									if (result.code == 200) {
										layer.msg("操作成功！");
										layer.close(index);
										var data = $('#groupRiseSearch').serializeObject();
										table.reload('groupRiseReload', {
											where : data
										}, 'data');
									} else if (result.code == 400) {
										layer.msg(result.msg);
									}
								}
							});
						});
					}
				}
			});
		});
	},
	getDate : function(num) {
		var date = new Date();
		date.setDate(date.getDate() - num); //获取3天前的日期
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		if (month < 10) {
			month = '0' + month;
		}
		var day = date.getDate() - 1;
		if (day < 10) {
			day = '0' + day;
		}
		return year + "-" + month + "-" + day;
	},
	initPie : function(catId) {
		var result = null;
		$.ajax({
			url : context + "hotGoods/findPieByCatId?catId=" + catId,
			dataType : "json",
			async : false,
			type : "GET",
			success : function(data) {
				if (data.code == 200) {
					result = data.data;
				}
			}
		});

		let myChart = echarts.init(document.getElementById('main2'), 'macarons');
		var legendData = [];
		var seriesData = [];
		var selected = {};
		var infos = result.series[0].infos.reverse();
		for (var i = 1; i <= infos.length; i++) {
			var item = infos[i - 1];
			legendData.push(item.name);
			seriesData.push(item);
			selected[item.name] = i < 10;
		}
		var option = {
			tooltip : {
				trigger : 'item',
				formatter : '{a} <br/>{b} : {c} ({d}%)'
			},
			legend : {
				type : 'scroll',
				orient : 'vertical',
				right : 10,
				top : 20,
				bottom : 20,
				data : legendData,
				selected : selected,
				formatter : function(params) {
					return params.substring(0, 5);
				}
			},
			series : [
				{
					name : result.series[0].name,
					type : result.series[0].type,
					radius : '80%',
					center : [ "40%", "53%" ],
					data : seriesData,
					itemStyle : {
						normal : {
							label : {
								show : true,
								formatter : function(v) {
									return v.name.substring(0, 5);
								}
							},
							labelLine : {
								show : true
							}
						}
					}
				},
				{
					name : result.series[0].name,
					type : result.series[0].type,
					radius : '80%',
					center : [ "40%", "53%" ],
					data : seriesData,
					itemStyle : {
						normal : {
							label : {
								show : true,
								formatter : '{d}%',
								position : "inner"
							},
							labelLine : {
								show : true
							}
						}
					}
				}
			]
		};
		myChart.setOption(option);
		myChart.on('click', function(params) {
			var index = params.name.indexOf(":");
			$("#goodsName").val(params.name.substr(index + 1));
			$(".search button").click();
		});
	},
	initEcharts : function(catId) {
		var result = null;
		$.ajax({
			url : context + "hotGoods/findByCatId?catId=" + catId,
			dataType : "json",
			async : false,
			type : "GET",
			success : function(data) {
				if (data.code == 200) {
					result = data.data;
				}
			}
		});
		if(result!=null){
			let myChart = echarts.init(document.getElementById('main'));
			var colors = [ '#5793f3', '#675bba' ];
			let option = {
				color : colors,
				tooltip : {
					trigger : 'axis'
				},
				dataZoom : [
					{
						type : 'slider',
						show : true,
						start : 94,
						end : 100,
						handleSize : 8,
						textStyle : {
							color : "rgba(102,102,102,0)"
						}
					},
					{
						type : 'inside',
						start : 94,
						end : 100
					}
				],
				grid : {
					right : '20%'
				},
				legend : {
					data : result.legend
				},
				xAxis : [
					{
						type : 'category',
						axisLabel : {
							show : false,
						},
						data : result.xAxis[0].data
					}
				],
				yAxis : [
					{
						type : 'value',
						name : result.yAxis[0].name,
						min : result.yAxis[0].min,
						max : result.yAxis[0].max,
						position : 'right',
						axisLine : {
							lineStyle : {
								color : colors[0]
							}
						},
						axisLabel : {
							show : false,
							formatter : '{value}'
						},
						splitLine : {
							"show" : false
						}
					},
					{
						type : 'value',
						name : result.yAxis[1].name,
						min : result.yAxis[1].min,
						max : result.yAxis[1].max,
						position : 'left',
						axisLine : {
							lineStyle : {
								color : colors[1]
							}
						},
						axisLabel : {
							show : false,
							formatter : '{value}'
						}
					}
				],
				series : [
					{
						name : result.series[0].name,
						type : 'bar',
						data : result.series[0].data,
					},
					{
						name : result.series[1].name,
						type : 'line',
						yAxisIndex : 1,
						data : result.series[1].data,
					}
				]
			};
			myChart.setOption(option);
			myChart.on('click', function(params) {
				$("#goodsName").val(params.name);
				$(".search button").click();
			});
		}
	},
	initCollect : function() {
		var main = this;
		layui.use([ 'table', 'element', 'form' ], function() {
			var table = layui.table;
			var element = layui.element;
			var form = layui.form;
			table.render({
				elem : '#collect',
				url : context + 'hotCollect/find?state=1',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {

				},
				height : 'full-220',
				title : '历史收藏列表',
				toolbar : '#collectToolbar',
				cols : [ [ {
					type : 'radio',
					fixed : 'left'
				}, {
					type : 'numbers',
					title : '序号',
					hide : true
				}, {
					field : 'goodsName',
					title : '名称',
					templet : function(d) {
						return '<a href="https://mobile.yangkeduo.com/search_result.html?search_key=' + d.hotRise.goodsName + '" target="_blank">' + d.hotRise.goodsName + '</a>';
					}
				}, {
					field : 'collectDateTime',
					title : '收藏时间',
					width : 200,
					sort : true
				} ] ],
				page : true,
				response : {
					statusCode : 200
				},
				id : 'collectReload',
				parseData : function(res) {
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : res.data
					}
				}
			});
			table.on('toolbar(collectTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				if (data == null) {
					return;
				} else {
					if (obj.event === 'cancel') {
						$.ajax({
							url : context + "hotCollect/cancel?id=" + data[0].hotRise.id,
							type : "GET",
							dataType : "json",
							async : false,
							success : function(result) {
								if (result.code == 200) {
									layer.msg("取消成功！");
									main.initCollect();
								} else if (result.code == 400) {
									layer.msg(result.msg);
								}
							}
						});
					}else if (obj.event === 'edit') {
						layer.prompt({
						  formType: 2,
						  value: data[0].url,
						  title: '请输入值',
						  area: ['300px', '100px']
						}, function(value, index, elem){
							delete  data[0].hotRise
						    data[0].url = value;
						  	$.ajax({
								url : context + "hotCollect/save",
								type : "POST",
								dataType : "json",
								data : data[0],
								async : false,
								success : function(result) {
									if (result.code == 200) {
										layer.msg("操作成功！");
										layer.close(index);
										main.initCollect();
									} else if (result.code == 400) {
										layer.msg(result.msg);
									}
								}
							});
						    
						});
					}else if (obj.event === 'trend') {
						var url = context + "hotTrend/info?hotRiseId=" + data[0].hotRiseId;
						layer.open({
							title : '数据监控',
							type : 2,
							shade : [0],
							area : ['1200px','650px'],
							content : url
						});
					}
					
				}
			});
			table.on('rowDouble(collectTable)', function(obj) {
				var data = obj.data;
				var url = context + "hotGoods/info?goodsName=" + data.hotRise.goodsName;
				layer.open({
					title : '详情',
					type : 2,
					shade : [ 0 ],
					area : [ '1200px', '650px' ],
					content : url,
				});
			});
		});
	},
}
$(document).ready(function() {
	ccc.js.goodsCate.index.main.init();
});