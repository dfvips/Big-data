package com.zdata.util;

/**
 * 分页工具类
 * @author Administrator
 *
 */
public class PageUtil {

	/**
	 * 获取分页代码
	 * @param targetUrl 目标地址
	 * @param totalNum 总记录数
	 * @param IASloginUser 当前用户
	 * @param IASSoftID 当前软件
	 * @param currentPage 当前页
	 * @param pageSize 每页大小
	 * @return
	 */
	public static String getPagation(String targetUrl,int totalNum,int currentPage,int pageSize,String IASloginUser,String IASSoftID,String IASFuncID){
		int totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0){
			return "<font color=red>未查询到数据！</font>";
		}
		StringBuffer pageCode=new StringBuffer();
		pageCode.append("<li><a href='"+targetUrl+"?page=1&IASloginUser="+IASloginUser+"&IASSoftID="+IASSoftID+"&IASFuncID="+IASFuncID+"'>首页</a></li>");
		if(currentPage==1){
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		}else{
			pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage-1)+"&IASloginUser="+IASloginUser+"&IASSoftID="+IASSoftID+"&IASFuncID="+IASFuncID+"'>上一页</a></li>");
		}
		
		for(int i=currentPage-2;i<=currentPage+2;i++){
			if(i<1 || i>totalPage){
				continue;
			}
			if(i==currentPage){
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			}else{
				pageCode.append("<li><a href='"+targetUrl+"?page="+i+"&IASloginUser="+IASloginUser+"&IASSoftID="+IASSoftID+"&IASFuncID="+IASFuncID+"'>"+i+"</a></li>");
			}
			
		}
		
		if(currentPage==totalPage){
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		}else{
			pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage+1)+"&IASloginUser="+IASloginUser+"&IASSoftID="+IASSoftID+"&IASFuncID="+IASFuncID+"'>下一页</a></li>");
		}
		pageCode.append("<li><a href='"+targetUrl+"?page="+totalPage+"&IASloginUser="+IASloginUser+"&IASSoftID="+IASSoftID+"&IASFuncID="+IASFuncID+"'>尾页</a></li>");
		return pageCode.toString();
	}
}
