package com.zdata.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.zdata.model.SysFile;
import com.zdata.service.SysFileService;
import com.zdata.util.ResponseUtil;

/**
 * SysFileController 控制器类
 * @date 2019-09-28 16:33:30
 * @version 1.0
 */
@Controller
@RequestMapping("/sysFile")
public class SysFileController {
	
	@Resource
	private SysFileService sysFileService;

	/** 多条件分页查询方法 
	 * @throws Exception */
	@RequestMapping("/find")
	public void find(SysFile record,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysFileService.find(record,request);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}

	/** 根据主键id查询方法 */
	@RequestMapping("/findOne")
	public SysFile findOne(Long id) {
		try {
			return sysFileService.findOne(id);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/** 添加方法 
	 * @throws Exception */
	@RequestMapping("/save")
	public void save(@RequestParam(value = "params", required = true) String params
			,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysFileService.save(params,request);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}

	/** 修改方法 */
	@RequestMapping("/update")
	public boolean update(SysFile sysFile) {
		try {
			sysFileService.update(sysFile);
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	/** 删除方法 
	 * @throws Exception */
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="id",required = false) String id
			,HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysFileService.delete(Integer.valueOf(id));
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}

	/**
	 * 文件上传
	 * <p>Title: saveFile</p>  
	 * <p>Description: </p>  
	 * @param tmpFiles
	 * @param num
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/saveFile")
	public void saveFile(@RequestParam(value="files",required = false) MultipartFile[] tmpFiles,
			@RequestParam(value = "num", required = false) String num,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysFileService.saveFile(tmpFiles, num, request);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 文件下载
	 * <p>Title: downFile</p>  
	 * <p>Description: </p>  
	 * @param request
	 * @param response
	 * @param fileUrl
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/downFile")
	public ResponseEntity<byte[]> downFile(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "fileUrl", required = false) String fileUrl,
			@RequestParam(value = "fileName", required = false) String fileName) throws IOException{
		String realPath = "D:/zk";//获取下载文件的路径
		File file = new File(realPath, fileUrl);//把下载文件构成一个文件处理   filename:前台传过来的文件名称
		HttpHeaders headers = new HttpHeaders();//设置头信息
		String downloadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");//设置响应的文件名
		headers.setContentDispositionFormData("attachment", downloadFileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// MediaType:互联网媒介类型 contentType：具体请求中的媒体类型信息
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
	
	@RequestMapping("/uploadFile")
	public void uploadFile(@RequestParam(value="file",required = false) MultipartFile file,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = sysFileService.uploadFile(file, request);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/uploadImage")
	public void uploadImage(@RequestParam(value="file",required = false) MultipartFile file,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = sysFileService.uploadImage(file, request);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
}
