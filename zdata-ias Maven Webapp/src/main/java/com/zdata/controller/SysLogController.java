package com.zdata.controller;

import java.util.List;

import com.zdata.dao.SysLogDao;
import com.zdata.model.PageBean;
import com.zdata.model.SysLog;
import com.zdata.util.ResponseUtil;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {
	
	@Autowired
	private SysLogDao sysLogDao;
	
	@RequestMapping("/find")
	public void find(SysLog log,@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "pageSize", required = false) String pageSize,
			HttpServletResponse response) throws Exception{
		JSONObject result = new JSONObject();
		try {
			PageBean pageBean = new PageBean(Integer.parseInt(pageNumber),Integer.parseInt(pageSize));
			List<SysLog> logList = sysLogDao.find(log,pageBean);
			int total = sysLogDao.count(log, pageBean);
			result.put("success", true);
			result.put("rows", logList);
			result.put("total", total);
		} catch (Exception e) {
			result.put("success", false);
			e.printStackTrace();
		}
		ResponseUtil.write(result, response);
	}
}
