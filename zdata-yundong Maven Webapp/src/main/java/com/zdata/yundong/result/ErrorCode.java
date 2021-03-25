package com.zdata.yundong.result;

public enum ErrorCode {

	SELECT_ERROR("查询出错！"),
	UPDATE_ERROR("更新出错！"),
	DELETE_ERROR("删除出错"),
	ADD_ERROR("添加出错");
	public final String msg;

	ErrorCode(String msg) {
		this.msg = msg;
	}
	
	public String getValue() {
		return msg;
	}
}
