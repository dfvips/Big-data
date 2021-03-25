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

/**
 * 将时间戳转换成正常时间格式
 */
function timestampToTime(timestamp) {
	var date = new Date(timestamp);
    content = '';
    content += date.getFullYear();
    content += '-';
    content += ((date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date.getMonth() + 1));
    content += '-';
    content += (date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate());
    content += ' ';
    content += (date.getHours() < 10 ? ("0" + date.getHours()) : date.getHours());
    content += ':';
    content += (date.getMinutes() < 10 ? ("0" + date.getMinutes()) : date.getMinutes());
    content += ':';
    content += (date.getSeconds() < 10 ? ("0" + date.getSeconds()) : date.getSeconds());
    return content;
}

/**
 * 时间对象转时间
 */
function fmtDateTime(value){
	if (value && value.time) {
	    var date = new Date(value.time);
	    content = '';
	    content += date.getFullYear();
	    content += '-';
	    content += ((date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date.getMonth() + 1));
	    content += '-';
	    content += (date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate());
	    content += ' ';
	    content += (date.getHours() < 10 ? ("0" + date.getHours()) : date.getHours());
	    content += ':';
	    content += (date.getMinutes() < 10 ? ("0" + date.getMinutes()) : date.getMinutes());
	    content += ':';
	    content += (date.getSeconds() < 10 ? ("0" + date.getSeconds()) : date.getSeconds());
	    return content;
	} else {
	    return '---';
	}
}