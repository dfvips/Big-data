/**
 * 
 */

$.namespace('sycm.js.cate.index');
sycm.js.cate.index.main = {
	init : function() {
		var main = this;
		main.initCss();
		main.initJs();
		main.initzTree();
	},
	initJoin : function(catId){
		var main = this;
		var updateTime = $("#updateTimeJoin").val();
		if(updateTime==null||updateTime==""){
			updateTime = main.getDate(0);
			$("#updateTimeJoin").val(main.getDate(0));
		}
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#joinList',
				url : context + 'cate/findJoin',
				request : {
					
				},
				where : {
					catId : catId,
					updateTime : updateTime
				},
				toolbar : '#joinToolbar',
				height : 'full-175',
				title : '连续上升三天（交集）列表',
				cols : [ [ {
					type : 'radio'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'pictUrl',
					title : '图片',
					width : 60,
					templet : function(d){
						return '<img style="width:30px;heigth:30px;" src="'+d.pictUrl+'" onclick="show_img(this)"/>'
					}
				}, {
					field : 'itemId',
					title : '宝贝Id',
					width : 150
				}, {
					field : 'itemTitle',
					title : '宝贝标题',
					templet : function(d){
						return '<a href="' + d.detailUrl + '" target="_blank">' + d.itemTitle + '</a>';
					}
				}, {
					field : 'updateTime',
					title : '时间',
					width : 120,
					sort : true,
					templet : function(d){
						return d.updateTime.substring(0,10);
					}
				}, {
					field : 'shopTitle',
					title : '店铺',
					hide : true
				}, {
					field : 'tradeGrowthRange',
					title : '交易增长幅度',
					width : 150,
					templet : function(d){
						var result = d.tradeGrowthRange*100+"";
						var w=result.indexOf(".");
						return result.substring(0,w+3)+"%";
					}
				}, {
					field : 'tradeGrowthRangeCrc',
					title : '交易增长幅度(较前日)',
					width : 150,
					templet : function(d){
						var result = d.tradeGrowthRange*100+"";
						var w=result.indexOf(".");
						return result.substring(0,w+3)+"%";
					}
				}, {
					field : 'cateRank',
					title : '排名',
					width : 120,
					sort : true
				}, {
					field : 'cateRankCqc',
					title : '排名(较前日)',
					width : 120,
					sort : true
				}, {
					field : 'tradeIndex',
					title : '交易指数',
					width : 120,
					sort : true,
					templet : function(d){
						var w=d.payRateIndex.indexOf(".");
						return d.payRateIndex.substring(0,w+3);
					}
				}, {
					field : 'payRateIndex',
					title : '支付转化指数',
					width : 120,
					sort : true,
					templet : function(d){
						var w=d.payRateIndex.indexOf(".");
						return d.payRateIndex.substring(0,w+3);
					}
				}
				] ],
				page : false,
				response : {
					statusCode : 200
				},
				id : 'joinReload',
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
					var data = $('#searchJoin').serializeObject();
					table.reload('joinReload', {
						where : data
					}, 'data');
				}
			};
			$('.searchJoin .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('toolbar(joinTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				switch (obj.event) {
				case 'aliSearch':
					if(data.length==0){
						layer.msg("请选择一条数据！");
					}else{
						window.open('https://api.dfvips.com/oss?url='+encodeURIComponent("https:"+data[0].pictUrl), "_blank");
					}
					break;
				}
			});
		});
	},
	initJs : function() {
		var main = this;
		layui.use([ 'element', 'laydate', 'form' ], function() {
			var element = layui.element;
			var form = layui.form;
			var laydate = layui.laydate;
			laydate.render({
				elem : '#updateTime',
				done: function(value, date){
					$('.searchHot .search').click();
				}
			});
			laydate.render({
				elem : '#updateTimeLl',
				done: function(value, date){
					$('.searchHotLl .search').click();
				}
			});
			laydate.render({
				elem : '#updateTimeYx',
				done: function(value, date){
					$('.searchHotYx .search').click();
				}
			});
			laydate.render({
				elem : '#updateTimeJoin',
				done: function(value, date){
					$('.searchJoin .layui-btn').click();
				}
			});
			laydate.render({
				elem : '#updateTimeSale',
				done: function(value, date){
				}
			});
			laydate.render({
				elem : '#updateTimeSearch',
				done: function(value, date){
				}
			});
			laydate.render({
				elem : '#updateTimePurpose',
				done: function(value, date){
				}
			});
			element.on('tab(content)', function(data){
				var zTreeObj = $.fn.zTree.getZTreeObj("cateTree");
				var selectedNodes = zTreeObj.getSelectedNodes();
				if(selectedNodes.length==0){
					//layer.msg("请选择类目");
				}else{
					switch (data.index) {
						case 0:
							main.initHotSale(selectedNodes[0].catId);
							break;
						case 1:
							main.initHot(selectedNodes[0].catId);
							break;
						case 2:
							main.initHotSearch(selectedNodes[0].catId);
							break;
						case 3:
							main.initHotLl(selectedNodes[0].catId);
							break;
						case 4:
							main.initHotPurpose(selectedNodes[0].catId);
							break;
						case 5:
							main.initHotYx(selectedNodes[0].catId);
							break;
						case 6:
							main.initJoin(selectedNodes[0].catId);
							break;
					}
				}
				location.hash = 'content='+ this.getAttribute('lay-id');
			});
		});
	},
	initCss : function() {
		var h = $(window).height() - 108;
		var h1 = $(window).height() - 225;
		document.getElementById("cateTreeDiv").style.height = h + "px";
		document.getElementById("Saleline").style.height = h1 + "px";
		document.getElementById("Searchline").style.height = h1 + "px";
		document.getElementById("Purposeline").style.height = h1 + "px";
	},
	getDate : function(num) {
		var date = new Date();
		date.setDate(date.getDate() - num);
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
	initHotYx : function(catId){
		var main = this;
		var updateTime = $("#updateTimeYx").val();
		if(updateTime==null||updateTime==""){
			updateTime = main.getDate(0);
			$("#updateTimeYx").val(main.getDate(0));
		}
		layui.use(['table'], function() {
			var table = layui.table;
			table.render({
				elem : '#hotYxList',
				url : context + 'hotPurpose/findHot?catId=' + catId,
				where : {
					updateTime : updateTime
				},
				toolbar : '#hotYxToolbar',
				height : 'full-250',
				title : '连续上升三天（高意向）列表',
				cols : [ [ {
					type : 'radio'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'pictUrl',
					title : '图片',
					width : 60,
					templet : function(d){
						return '<img style="width:30px;heigth:30px;" src="'+d.pictUrl+'" onclick="show_img(this)"/>'
					}
				}, {
					field : 'itemId',
					title : '宝贝Id',
					width : 150
				}, {
					field : 'itemTitle',
					title : '名称',
					templet : function(d){
						return '<a href="' + d.detailUrl + '" target="_blank">' + d.itemTitle + '</a>';
					}
				}, {
					field : 'updateTime',
					title : '时间',
					width : 120,
					sort : true,
					templet : function(d){
						return d.updateTime.substring(0,10);
					}
				}, {
					field : 'shopTitle',
					title : '店铺',
					hide : true
				}, {
					field : 'tradeIndex',
					title : '交易指数',
					width : 150,
					templet : function(d){
						var w=d.tradeIndex.indexOf(".");
						return d.tradeIndex.substring(0,w+3);
					}
				}, {
					field : 'cateRank',
					title : '排名',
					width : 150
				}, {
					field : 'cateRankCqc',
					title : '排名(较前日)',
					width : 120,
					sort : true
				}, {
					field : 'cltHits',
					title : '收藏人气',
					width : 120,
					sort : true
				}, {
					field : 'cltHits',
					title : '收藏人气',
					width : 120,
					sort : true
				}, {
					field : 'cartHits',
					title : '加购人气',
					width : 120,
					sort : true
				}
				] ],
				page : false,
				response : {
					statusCode : 200
				},
				id : 'hotYxReload',
				parseData : function(res) {
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : res.data
					}
				},
				done: function(res, curr, count){
					if(res.code==200){
						$('#Purposeline').html("");
						for(var i=0;i<res.data.length;i++){
							main.initPurposeLines(res.data[i].catId,res.data[i].itemId);
						}
					}
				}
			});
			var $ = layui.$,
			active = {
				reload : function() {
					var data = $('#searchHotYx').serializeObject();
					table.reload('hotYxReload', {
						where : data
					}, 'data');
				},
				delCache : function() {
					var updateTimeYx = $("#updateTimeYx").val();
					if(updateTimeYx==null||updateTimeYx==""){
						layer.msg("请选择时间");
					}else{
						$.ajax({
							url : context + 'hotPurpose/delCache',
							dataType : "json",
							async : false,
							type : "POST",
							data : {
								updateTime : updateTimeYx,
								catId : catId
							},
							success : function(result) {
								if(result.code==200){
									layer.msg("操作成功！");
								}
							}
						});
					}
				}
			};
			$('.searchHotYx .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('rowDouble(hotYxTable)', function(obj){
				var url = context + "cate/info?itemId=" + obj.data.itemId+"&catId="+obj.data.catId;
				layer.open({
					title : obj.data.itemTitle,
					type : 2,
					shadeClose : true,
					area : [ '1200px', '650px' ],
					content : url
				});
			});
			table.on('toolbar(hotYxTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				switch (obj.event) {
				case 'aliSearch':
					if(data.length==0){
						layer.msg("请选择一条数据！");
					}else{
						window.open('https://api.dfvips.com/oss?url='+encodeURIComponent("https:"+data[0].pictUrl), "_blank");
					}
					break;
				}
			});
		});
	},
	initHotLl : function(catId){
		var main = this;
		var updateTime = $("#updateTimeLl").val();
		if(updateTime==null||updateTime==""){
			updateTime = main.getDate(0);
			$("#updateTimeLl").val(main.getDate(0));
		}
		layui.use(['table'], function() {
			var table = layui.table;
			table.render({
				elem : '#hotLlList',
				url : context + 'hotSearch/findHot?catId=' + catId,
				where : {
					updateTime : updateTime
				},
				toolbar : '#hotLlToolbar',
				height : 'full-250',
				title : '连续上升三天（高流量）列表',
				cols : [ [ {
					type : 'radio'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'pictUrl',
					title : '图片',
					width : 60,
					templet : function(d){
						return '<img style="width:30px;heigth:30px;" src="'+d.pictUrl+'" onclick="show_img(this)"/>'
					}
				}, {
					field : 'itemId',
					title : '宝贝Id',
					width : 150
				}, {
					field : 'itemTitle',
					title : '名称',
					templet : function(d){
						return '<a href="' + d.detailUrl + '" target="_blank">' + d.itemTitle + '</a>';
					}
				}, {
					field : 'updateTime',
					title : '时间',
					width : 120,
					sort : true,
					templet : function(d){
						return d.updateTime.substring(0,10);
					}
				}, {
					field : 'shopTitle',
					title : '店铺',
					hide : true
				}, {
					field : 'tradeIndex',
					title : '交易指数',
					width : 150
				}, {
					field : 'cateRank',
					title : '排名',
					width : 120,
					sort : true
				}, {
					field : 'seIpvUvHits',
					title : '搜索人气',
					width : 120,
					sort : true
				}, {
					field : 'uvIndex',
					title : '流量指数',
					width : 120,
					sort : true
				}
				] ],
				page : false,
				response : {
					statusCode : 200
				},
				id : 'hotLlReload',
				parseData : function(res) {
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : res.data
					}
				},
				done: function(res, curr, count){
					if(res.code==200){
						$('#Searchline').html("");
						for(var i=0;i<res.data.length;i++){
							main.initSearchLines(res.data[i].catId,res.data[i].itemId);
						}
					}
				}
			});
			var $ = layui.$,
			active = {
				reload : function() {
					var data = $('#searchHotLl').serializeObject();
					table.reload('hotLlReload', {
						where : data
					}, 'data');
				},
				delCache : function() {
					var updateTimeLl = $("#updateTimeLl").val();
					if(updateTimeLl==null||updateTimeLl==""){
						layer.msg("请选择时间");
					}else{
						$.ajax({
							url : context + 'hotSearch/delCache',
							dataType : "json",
							async : false,
							type : "POST",
							data : {
								updateTime : updateTimeLl,
								catId : catId
							},
							success : function(result) {
								if(result.code==200){
									layer.msg("操作成功！");
								}
							}
						});
					}
				}
			};
			$('.searchHotLl .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('rowDouble(hotLlTable)', function(obj){
				var url = context + "cate/info?itemId=" + obj.data.itemId+"&catId="+obj.data.catId;
				layer.open({
					title : obj.data.itemTitle,
					type : 2,
					shadeClose : true,
					area : [ '1200px', '650px' ],
					content : url
				});
			});
			table.on('toolbar(hotLlTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				switch (obj.event) {
				case 'aliSearch':
					if(data.length==0){
						layer.msg("请选择一条数据！");
					}else{
						window.open('https://api.dfvips.com/oss?url='+encodeURIComponent("https:"+data[0].pictUrl), "_blank");
					}
					break;
				}
			});
		});
	},
	initPurposeLines : function(catId,itemId){
		var main = this;
		$.ajax({
			url : context + 'hotPurpose/findLine',
			dataType : "json",
			async : false,
			type : "POST",
			data : {
				itemId : itemId,
				catId : catId
			},
			success : function(result) {
				if (result.code == 200) {
					var legend = result.data.legend;
					var xAxis = result.data.xAxis;
					var series = result.data.series;
					for (var i = 0; i < legend.length; i++) {
						$("#Purposeline").append('<div class="layui-col-md3">'+
							'<div id="mainPurpose_'+itemId+'_'+i+'" style="width: 95%;height:400px;"></div>'+
							'</div>');
						let myChart = echarts.init(document.getElementById("mainPurpose_"+itemId+'_'+ i), 'macarons');
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
	initSearchLines : function(catId,itemId){
		var main = this;
		$.ajax({
			url : context + 'hotSearch/findLine',
			dataType : "json",
			async : false,
			type : "POST",
			data : {
				itemId : itemId,
				catId : catId
			},
			success : function(result) {
				if (result.code == 200) {
					var legend = result.data.legend;
					var xAxis = result.data.xAxis;
					var series = result.data.series;
					for (var i = 0; i < legend.length; i++) {
						$("#Searchline").append('<div class="layui-col-md3">'+
							'<div id="mainSearch_'+itemId+'_'+i+'" style="width: 95%;height:400px;"></div>'+
							'</div>');
						let myChart = echarts.init(document.getElementById("mainSearch_"+itemId+'_'+ i), 'macarons');
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
	initSaleLines : function(catId,itemId){
		var main = this;
		$.ajax({
			url : context + 'hotSale/findLine',
			dataType : "json",
			async : false,
			type : "POST",
			data : {
				itemId : itemId,
				catId : catId
			},
			success : function(result) {
				if (result.code == 200) {
					var legend = result.data.legend;
					var xAxis = result.data.xAxis;
					var series = result.data.series;
					for (var i = 0; i < legend.length; i++) {
						$("#Saleline").append('<div class="layui-col-md4">'+
							'<div id="mainSale'+'_'+itemId+'_'+i+'" style="width: 95%;height:400px;"></div>'+
							'</div>');
						let myChart = echarts.init(document.getElementById("mainSale_"+itemId+"_"+i), 'macarons');
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
	initHot : function(catId){
		var main = this;
		var updateTime = $("#updateTime").val();
		if(updateTime==null||updateTime==""){
			updateTime = main.getDate(0);
			$("#updateTime").val(main.getDate(0));
		}
		layui.use(['table'], function() {
			var table = layui.table;
			table.render({
				elem : '#hotList',
				url : context + 'hotSale/findHot?catId=' + catId,
				where : {
					updateTime : updateTime
				},
				toolbar : '#hotToolbar',
				height : 'full-250',
				title : '热销排行列表',
				cols : [ [ {
					type : 'radio'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'pictUrl',
					title : '图片',
					width : 60,
					templet : function(d){
						return '<img style="width:30px;heigth:30px;" src="'+d.pictUrl+'" onclick="show_img(this)"/>'
					}
				}, {
					field : 'itemId',
					title : '宝贝Id',
					width : 150
				}, {
					field : 'itemTitle',
					title : '名称',
					templet : function(d){
						return '<a href="' + d.detailUrl + '" target="_blank">' + d.itemTitle + '</a>';
					}
				}, {
					field : 'updateTime',
					title : '时间',
					width : 120,
					sort : true,
					templet : function(d){
						return d.updateTime.substring(0,10);
					}
				}, {
					field : 'shopTitle',
					title : '店铺',
					hide : true
				}, {
					field : 'tradeGrowthRange',
					title : '交易增长幅度',
					width : 150,
					templet : function(d){
						var result = d.tradeGrowthRange*100+"";
						var w=result.indexOf(".");
						return result.substring(0,w+3)+"%";
					}
				}, {
					field : 'tradeGrowthRangeCrc',
					title : '交易增长幅度(较前日)',
					width : 150,
					templet : function(d){
						var result = d.tradeGrowthRange*100+"";
						var w=result.indexOf(".");
						return result.substring(0,w+3)+"%";
					}
				}, {
					field : 'cateRank',
					title : '排名',
					width : 120,
					sort : true
				}, {
					field : 'cateRankCqc',
					title : '排名(较前日)',
					width : 120,
					sort : true
				}, {
					field : 'tradeIndex',
					title : '交易指数',
					width : 120,
					sort : true,
					templet : function(d){
						if(d.tradeIndex!=null){
							var w=d.tradeIndex.indexOf(".");
							return d.tradeIndex.substring(0,w+3);
						}else{
							return "";
						}
						
					}
				}, {
					field : 'payRateIndex',
					title : '支付转化指数',
					width : 120,
					sort : true,
					templet : function(d){
						if(d.payRateIndex!=null){
							var w=d.payRateIndex.indexOf(".");
							return d.payRateIndex.substring(0,w+3);
						}else{
							return "";
						}
					}
				}
				] ],
				page : false,
				response : {
					statusCode : 200
				},
				id : 'hotReload',
				parseData : function(res) {
					return {
						'code' : res.code,
						'msg' : res.msg,
						'count' : res.count,
						'data' : res.data
					}
				},
				done: function(res, curr, count){
					if(res.code==200){
						$('#Saleline').html("");
						for(var i=0;i<res.data.length;i++){
							main.initSaleLines(res.data[i].catId,res.data[i].itemId);
						}
					}
				}
				
			});
			var $ = layui.$,
			active = {
				reload : function() {
					var data = $('#searchHot').serializeObject();
					table.reload('hotReload', {
						where : data
					}, 'data');
				},
				delCache : function() {
					var updateTime = $("#updateTime").val();
					if(updateTime==null||updateTime==""){
						layer.msg("请选择时间");
					}else{
						$.ajax({
							url : context + 'hotSale/delCache',
							dataType : "json",
							async : false,
							type : "POST",
							data : {
								updateTime : updateTime,
								catId : catId
							},
							success : function(result) {
								if(result.code==200){
									layer.msg("操作成功！");
								}
							}
						});
					}
				}
			};
			$('.searchHot .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('rowDouble(hotTable)', function(obj){
				var url = context + "cate/info?itemId=" + obj.data.itemId+"&catId="+obj.data.catId;
				layer.open({
					title : obj.data.itemTitle,
					type : 2,
					shadeClose : true,
					area : [ '1200px', '650px' ],
					content : url
				});
			});
			table.on('toolbar(hotTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				switch (obj.event) {
				case 'aliSearch':
					if(data.length==0){
						layer.msg("请选择一条数据！");
					}else{
						window.open('https://api.dfvips.com/oss?url='+encodeURIComponent("https:"+data[0].pictUrl), "_blank");
					}
					break;
				}
			});
		});
	},
	initHotSearch : function(catId){
		var main = this;
		layui.use(['table'], function() {
			var table = layui.table;
			table.render({
				elem : '#hotSearchList',
				url : context + 'hotSearch/find',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {
					catId : catId
				},
				toolbar : '#hotSearchToolbar',
				height : 'full-175',
				title : '高流量排行列表',
				cols : [ [ {
					type : 'radio'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'pictUrl',
					title : '图片',
					width : 60,
					templet : function(d){
						return '<img style="width:30px;heigth:30px;" src="'+d.pictUrl+'" onclick="show_img(this)"/>'
					}
				}, {
					field : 'itemId',
					title : '宝贝Id',
					width : 150
				}, {
					field : 'itemTitle',
					title : '名称',
					templet : function(d){
						return '<a href="' + d.detailUrl + '" target="_blank">' + d.itemTitle + '</a>';
					}
				}, {
					field : 'updateTime',
					title : '时间',
					width : 120,
					sort : true,
					templet : function(d){
						return d.updateTime.substring(0,10);
					}
				}, {
					field : 'shopTitle',
					title : '店铺',
					hide : true
				}, {
					field : 'tradeIndex',
					title : '交易指数',
					width : 150
				}, {
					field : 'cateRank',
					title : '排名',
					width : 120,
					sort : true
				}, {
					field : 'seIpvUvHits',
					title : '搜索人气',
					width : 120,
					sort : true
				}, {
					field : 'uvIndex',
					title : '流量指数',
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
			var $ = layui.$,
			active = {
				reload : function() {
					var data = $('#searchHotSearch').serializeObject();
					table.reload('hotSearchReload', {
						where : data
					}, 'data');
				}
			};
			$('.searchHotSearch .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('rowDouble(hotSearchTable)', function(obj){
				var url = context + "cate/info?itemId=" + obj.data.itemId+"&catId="+obj.data.catId;
				layer.open({
					title : obj.data.itemTitle,
					type : 2,
					shadeClose : true,
					area : [ '1200px', '650px' ],
					content : url
				});
			});
			table.on('toolbar(hotSearchTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				switch (obj.event) {
				case 'aliSearch':
					if(data.length==0){
						layer.msg("请选择一条数据！");
					}else{
						window.open('https://api.dfvips.com/oss?url='+encodeURIComponent("https:"+data[0].pictUrl), "_blank");
					}
					break;
				}
			});
		});
	},
	initHotSale : function(catId){
		var main = this;
		layui.use('table', function() {
			var table = layui.table;
			table.render({
				elem : '#hotSaleList',
				url : context + 'hotSale/find',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {
					catId : catId
				},
				toolbar : '#hotSaleToolbar',
				height : 'full-175',
				title : '热销排行列表',
				cols : [ [ {
					type : 'radio'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'pictUrl',
					title : '图片',
					width : 60,
					templet : function(d){
						return '<img style="width:30px;heigth:30px;" src="'+d.pictUrl+'" onclick="show_img(this)"/>'
					}
				}, {
					field : 'itemId',
					title : '宝贝Id',
					width : 150
				}, {
					field : 'itemTitle',
					title : '宝贝标题',
					templet : function(d){
						return '<a href="' + d.detailUrl + '" target="_blank">' + d.itemTitle + '</a>';
					}
				}, {
					field : 'updateTime',
					title : '时间',
					width : 120,
					sort : true,
					templet : function(d){
						return d.updateTime.substring(0,10);
					}
				}, {
					field : 'shopTitle',
					title : '店铺',
					hide : true
				}, {
					field : 'tradeGrowthRange',
					title : '交易增长幅度',
					width : 150,
					templet : function(d){
						var result = d.tradeGrowthRange*100+"";
						var w=result.indexOf(".");
						return result.substring(0,w+3)+"%";
					}
				}, {
					field : 'tradeGrowthRangeCrc',
					title : '交易增长幅度(较前日)',
					width : 150,
					templet : function(d){
						var result = d.tradeGrowthRange*100+"";
						var w=result.indexOf(".");
						return result.substring(0,w+3)+"%";
					}
				}, {
					field : 'cateRank',
					title : '排名',
					width : 120,
					sort : true
				}, {
					field : 'cateRankCqc',
					title : '排名(较前日)',
					width : 120,
					sort : true
				}, {
					field : 'tradeIndex',
					title : '交易指数',
					width : 120,
					sort : true,
					templet : function(d){
						var w=d.payRateIndex.indexOf(".");
						return d.payRateIndex.substring(0,w+3);
					}
				}, {
					field : 'payRateIndex',
					title : '支付转化指数',
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
			var $ = layui.$,
			active = {
				reload : function() {
					var data = $('#searchHotSale').serializeObject();
					table.reload('hotSaleReload', {
						where : data
					}, 'data');
				}
			};
			$('.searchHotSale .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('rowDouble(hotSaleTable)', function(obj){
				var url = context + "cate/info?itemId=" + obj.data.itemId+"&catId="+obj.data.catId;
				layer.open({
					title : obj.data.itemTitle,
					type : 2,
					shadeClose : true,
					area : [ '1200px', '650px' ],
					content : url
				});
			});
			table.on('toolbar(hotSaleTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				switch (obj.event) {
				case 'aliSearch':
					if(data.length==0){
						layer.msg("请选择一条数据！");
					}else{
						window.open('https://api.dfvips.com/oss?url='+encodeURIComponent("https:"+data[0].pictUrl), "_blank");
					}
					break;
				}
			});
		});
	},
	initzTree : function() {
		var main = this;
		var setting = {
			check : {
				enable : false
			},
			data : {
				key : {
					name : "catName"
				},
				simpleData : {
					enable : true,
					idKey : "catId",
					pIdKey : "catParent"
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
			url : context + "cate/findTree",
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
			var layid = location.hash.replace(/^#content=/, '');
			switch (layid) {
				case "0":
					main.initHotSale(treeNode.catId);
					break;
				case "1":
					main.initHot(treeNode.catId);
					break;
				case "2":
					main.initHotSearch(treeNode.catId);
					break;
				case "3":
					main.initHotLl(treeNode.catId);
					break;
				case "4":
					main.initHotPurpose(treeNode.catId);
					break;
				case "5":
					main.initHotYx(treeNode.catId);
					break;
				case "6":
					main.initJoin(treeNode.catId);
				default:
					main.initHotSale(treeNode.catId);
			}
		}
	},
	initHotPurpose: function(catId){
		var main = this;
		layui.use(['table'], function() {
			var table = layui.table;
			table.render({
				elem : '#hotPurposeList',
				url : context + 'hotPurpose/find',
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize' //每页数据量的参数名，默认：limit
				},
				where : {
					catId : catId
				},
				toolbar : '#hotPurposeToolbar',
				height : 'full-175',
				title : '高意向排行列表',
				cols : [ [ {
					type : 'radio'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					field : 'pictUrl',
					title : '图片',
					width : 60,
					templet : function(d){
						return '<img style="width:30px;heigth:30px;" src="'+d.pictUrl+'" onclick="show_img(this)"/>'
					}
				}, {
					field : 'itemId',
					title : '宝贝Id',
					width : 150
				}, {
					field : 'itemTitle',
					title : '名称',
					templet : function(d){
						return '<a href="' + d.detailUrl + '" target="_blank">' + d.itemTitle + '</a>';
					}
				}, {
					field : 'updateTime',
					title : '时间',
					width : 120,
					sort : true,
					templet : function(d){
						return d.updateTime.substring(0,10);
					}
				}, {
					field : 'shopTitle',
					title : '店铺',
					hide : true
				}, {
					field : 'tradeIndex',
					title : '交易指数',
					width : 150,
					templet : function(d){
						var w=d.tradeIndex.indexOf(".");
						return d.tradeIndex.substring(0,w+3);
					}
				}, {
					field : 'cateRank',
					title : '排名',
					width : 150
				}, {
					field : 'cateRankCqc',
					title : '排名(较前日)',
					width : 120,
					sort : true
				}, {
					field : 'cltHits',
					title : '收藏人气',
					width : 120,
					sort : true
				}, {
					field : 'cltHits',
					title : '收藏人气',
					width : 120,
					sort : true
				}, {
					field : 'cartHits',
					title : '加购人气',
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
			var $ = layui.$,
			active = {
				reload : function() {
					var data = $('#searchHotPurpose').serializeObject();
					table.reload('hotPurposeReload', {
						where : data
					}, 'data');
				}
			};
			$('.searchHotPurpose .layui-btn').unbind('click').click( function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
			table.on('rowDouble(hotPurposeTable)', function(obj){
				var url = context + "cate/info?itemId=" + obj.data.itemId+"&catId="+obj.data.catId;
				layer.open({
					title : obj.data.itemTitle,
					type : 2,
					shadeClose : true,
					area : [ '1200px', '650px' ],
					content : url
				});
			});
			table.on('toolbar(hotPurposeTable)', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data;
				switch (obj.event) {
				case 'aliSearch':
					if (data.length == 0) {
						layer.msg("请选择一条数据！");
					} else {
						window.open('https://api.dfvips.com/oss?url=' + encodeURIComponent("https:" + data[0].pictUrl), "_blank");
					}
					break;
				}
			});
		});
	}
}

$(document).ready(function() {
	sycm.js.cate.index.main.init();
});