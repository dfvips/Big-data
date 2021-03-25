package com.zdata.service.impl;

import com.zdata.model.DouYinCode;
import com.zdata.service.DouYinService;

@Service("douYinService")
public class DouYinServiceImpl implements DouYinService {

	@Override
	public String qrcodeAuth(DouYinCode code) {
		String result = "https://open.douyin.com/platform/oauth/connect/?client_key=" + code.getClientKey()
        + "&response_type="+code.getResponseType()+"&scope="+code.getScope()+"&redirect_uri="+code.getRedirectUri()+"&state="+code.getState();
		return result;
	}

}
