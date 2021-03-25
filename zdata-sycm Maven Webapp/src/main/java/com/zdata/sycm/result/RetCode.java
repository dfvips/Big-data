package com.zdata.sycm.result;

public enum RetCode {

	// 成功
	SUCCESS(200),

	//图片上传成功
	IMG_SUCCESS(0),
	
	// 失败
	FAIL(400),

	// 未认证（签名错误）
	UNAUTHORIZED(401),

	// 接口不存在
	NOT_FOUND(404),

	// 服务器内部错误
	INTERNAL_SERVER_ERROR(500);

	public int code;

	RetCode(int code) {
		this.code = code;
	}
}
