/**
 * 
 */

$.fn.serializeObject = function() {
	  var o = {};
	  var a = this.serializeArray();
	  $.each(a, function() {
	      if (o[this.name] !== undefined) {
	          if (!o[this.name].push) {
	              o[this.name] = [o[this.name]];
	          }
	          o[this.name].push(this.value || '');
	      } else {
	          o[this.name] = this.value || '';
	      }
	  });
	  return o;
};

$.namespace = function() {
    var a=arguments, o=null, i, j, d;
    for (i=0; i<a.length; i=i+1) {
        d=a[i].split(".");
        o=window;
        for (j=0; j<d.length; j=j+1) {
            o[d[j]]=o[d[j]] || {};
            o=o[d[j]];
        }
    }
    return o;
};

function getUrlParameter(name) {
	name = name.replace(/[]/, "\[").replace(/[]/, "\[").replace(/[]/, "\\\]");
	var regexS = "[\\?&]" + name + "=([^&#]*)";
	var regex = new RegExp(regexS);
	var results = regex.exec(window.location.href);
	if (results == null) return ""; else {
		return results[1];
	}
}

function getFuncId(){
	return getUrlParameter("funcId");
}

function getSoftId(){
	return getUrlParameter("softId");
}

function setAddress(data){
	var proHtml = '';
	layui.use("form",function() {
		var form = layui.form;
		$.ajax({
			url: "/layui/address/address.json",//json文件位置，文件名
			type: "GET",//请求方式为get
			dataType: "json", //返回数据格式为json
			async:false,
			success: function(addressData) {
				for(var i=0;i<addressData.length;i++){
					var provide = addressData[i];
		            proHtml += '<option value="' + addressData[i].code + '">' + addressData[i].name + '</option>';
		            if(data[0]==provide.code){
		            	var cityHtml = '';
				        for (var j = 0; j < provide.childs.length; j++) {
				        	var city = provide.childs[j];
				            cityHtml += '<option value="' + city.code + '">' + city.name + '</option>';
				            if(data[1]==city.code){
				            	var areHtml = '';
					            for(var k=0;k<city.childs.length;k++){
									var area = city.childs[k];
									areHtml += '<option value="' + area.code + '">' + area.name + '</option>';
					            }
					            $("select[name=area]").append(areHtml).removeAttr("disabled");
						        $("#area").find("option[value="+data[2]+"]").prop("selected",true);
				            }
				            
				        }
				        $("select[name=city]").append(cityHtml).removeAttr("disabled");
				        $("#city").find("option[value="+data[1]+"]").prop("selected",true);
		            }
				}
				$("select[name=province]").append(proHtml);
				$("#province").find("option[value="+data[0]+"]").prop("selected",true);
				form.render();
			}
		});
	});
}

function openParentTab(tabId,title,url,icon){
	delCookie("tab");
	setCookie("tab", tabId);
	delCookie("title");
	setCookie("title", title);
	delCookie("icon");
	setCookie("icon", icon);
	delCookie("url");
	setCookie("url", url);
}

function getCookie(name){
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg)){
		return unescape(arr[2]);
	}else{
		return null;
	}
}

function delCookie(name){
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(name);
	if(cval!=null){
		document.cookie= name + "="+cval+";expires="+exp.toGMTString();
	}
}

function setCookie(c_name,value){
    var days = 1; //定义一天
    var exp = new Date();
    exp.setTime(exp.getTime() + days * 24 * 60 * 60 * 1000);
    // 写入Cookie, toGMTString将时间转换成字符串
    document.cookie = c_name + "=" + escape(value) + ";expires=" + exp.toGMTString + ";path=/;domain=" + document.domain;
}


function fmtAddress(data){
	var result = "";
	$.ajax({
		url: "/layui/address/address.json",//json文件位置，文件名
		type: "GET",//请求方式为get
		dataType: "json", //返回数据格式为json
		async:false,
		success: function(addressData) {//请求成功完成后要执行的方法 
			for(var i=0;i<addressData.length;i++){
				var provide = addressData[i];
				if(data[0]==provide.code){
					result += provide.name;
					for(var j=0;j<provide.childs.length;j++){
						var city = provide.childs[j];
						if(data[1]==city.code){
							result += city.name;
							for(var k=0;k<city.childs.length;k++){
								var area = city.childs[k];
								if(data[2]==area.code){
									result += area.name;
								}
							}
						}
					}
				}
			}
			
		}
	})
	return result;
}

function show_img(t) {
	layer.open({
	   type: 1,
	   title: false,
	   closeBtn: 0,
	   area: ['auto'],
	   skin: 'layui-layer-nobg', //没有背景色
	   shadeClose: true,
	   content: '<div style="text-align:center"><img src="' + t.src + '" style="width:500px;heigth:500px;"/></div>'
	});
}

//复制的方法
function copyText(text, callback) { // text: 要复制的内容， callback: 回调
	var tag = document.createElement('input');
	tag.setAttribute('id', 'cp_hgz_input');
	tag.value = text;
	document.getElementsByTagName('body')[0].appendChild(tag);
	document.getElementById('cp_hgz_input').select();
	document.execCommand('copy');
	document.getElementById('cp_hgz_input').remove();
	if (callback) {
		callback(text)
	}
}