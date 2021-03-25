package com.zdata.oas.controller;

import java.util.HashMap;
import java.util.Map;

import com.zdata.util.ResponseUtil;
import com.zdata.util.SessionUtils;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Reference(check=false)
	private TaskService taskService;
	
	@RequestMapping("/findTaskCount")
	public void findTaskCount(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject result = new JSONObject();
		if (taskService!=null) {
			String userId = SessionUtils.getCurrentUser(request).getUserId();
			Map<String, Object> AllTask  = this.taskService.findAllTask(userId);
			map.put("AllTask", AllTask);
			Map<String, Object> MyInvolved  = this.taskService.findMyInvolved(userId);
			map.put("MyInvolved", MyInvolved);
			Map<String, Object> MyTask  = this.taskService.findMyTask(userId);
			map.put("MyTask", MyTask);
			Map<String, Object> MyTaskDone  = this.taskService.findMyTaskDone(userId);
			map.put("MyTaskDone", MyTaskDone);
			Map<String, Object> ShareTask  = this.taskService.findShareTask(userId);
			map.put("ShareTask", ShareTask);
			result = JSONObject.parseObject(JSON.toJSONString(map));
			ResponseUtil.write(result, response);
		}
	}
}
