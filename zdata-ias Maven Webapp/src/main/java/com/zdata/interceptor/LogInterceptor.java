package com.zdata.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.zdata.constant.Constant;
import com.zdata.dao.SysLogDao;
import com.zdata.model.SysLog;
import com.zdata.model.SysUser;
import com.zdata.util.RequestUtil;

public class LogInterceptor implements HandlerInterceptor {

	
	@Autowired
	private SysLogDao sysLogDao;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session.getAttribute(Constant.Current_User.getValue());
		Map<String,String[]> ParameterMap =  request.getParameterMap();
        Map<String,String> reqMap = new HashMap<String,String>();
        Set<Map.Entry<String,String[]>> entry = ParameterMap.entrySet();
        Iterator<Map.Entry<String,String[]>> it = entry.iterator();
        while (it.hasNext()){
            Map.Entry<String,String[]>  me = it.next();
            String key = me.getKey();
            String value = me.getValue()[0];
            reqMap.put(key,value);
        }
        String ipAddress = RequestUtil.getIpAddr(request);//ip地址
        SysLog log = new SysLog();
        log.setUserId(user.getUserId());
        log.setUserName(user.getUserName());
        log.setType(2);//操作日志
        log.setIpAddress(ipAddress);
        log.setSoftId(user.getSoftId());
        log.setCreateTime(new Date());
        log.setParams(reqMap.toString());
        log.setRequestAddress(request.getRequestURI());
        sysLogDao.add(log);
        return true;
   }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
