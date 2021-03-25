package com.zdata.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.zdata.constant.Constant;
import com.zdata.dao.SysFileDao;
import com.zdata.model.SysFile;
import com.zdata.service.SysFileService;
import com.zdata.util.SessionUtils;
import com.zdata.util.StringUtil;

@Service("sysFileService")
public class SysFileServiceImpl implements SysFileService {

	private Integer fileSize_1M = 1048576;
	private Integer fileSize_1G = 1073741824;
	private static String FILE_PATH = "E:\\zk\\files";
	private static String CMS_FILE_PATH = "E:\\zk\\cms";
	private static String  IMG_FILE_PATH = "E:\\zk\\images";
	@Resource
	private SysFileDao sysFileDao;

	@Override
	public Map<String, Object> find(SysFile record,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			record.setPage((record.getPage() - 1) * record.getRows());
			record.setUserId(SessionUtils.getCurrentUser(request).getUserId());
			List<SysFile> rows = this.sysFileDao.find(record);
			int total = sysFileDao.count(record);
			map.put(Constant.Rows_Name.getValue(), rows);
			map.put(Constant.Total_Name.getValue(), total);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public SysFile findOne(Long id) {
		return null;
	}

	@Override
	public Map<String, Object> save(String params,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(params!=null&&params!=""){
				String[] jsonData = params.split("!");
				for(int i =0;i<jsonData.length;i++){
					JSONObject jsonObject = JSONObject.parseObject(jsonData[i]);
					SysFile record = JSON.toJavaObject(jsonObject,SysFile.class);
					if (StringUtil.isNotEmpty(record.getFileUrl())) {
						record.setUserId(SessionUtils.getCurrentUser(request).getUserId());
						this.sysFileDao.add(record);
					}
				}
			}
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public boolean update(SysFile sysFile) {
		return false;
	}

	@Override
	public Map<String, Object> delete(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysFileDao.delete(id);
			map.put("status", "success");
		} catch (Exception e) {
			map.put("status", "fail");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> saveFile(MultipartFile[] tmpFiles, String num,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String imgB64 = "";
		String headImage = "";
		String fileSize = "";
		try {
			MultipartFile tmpFile = null;
			// 判断file数组不能为空并且长度大于0
			if (tmpFiles != null && tmpFiles.length > 0) {
				tmpFile = tmpFiles[Integer.valueOf(num) - 1];
			}
			long size = tmpFile.getSize();
			// 截取文件名后缀。
			String name = tmpFile.getOriginalFilename();
			String suffix = name
					.substring(name.lastIndexOf("."), name.length());
			// 大文件处理
			if (size > fileSize_1G) {
				float size_G = size / (float) 1073741824;
				fileSize = String.valueOf(size_G);
				if (fileSize.lastIndexOf(".") > -1) {
					fileSize = (fileSize + "00000").substring(0,
							fileSize.lastIndexOf(".") + 3)
							+ "G";
				} else {
					fileSize += "G";
				}
			} else if (size > fileSize_1M) {
				float size_M = size / (float) 1048576;
				fileSize = String.valueOf(size_M);
				if (fileSize.lastIndexOf(".") > -1) {
					fileSize = (fileSize + "00000").substring(0,
							fileSize.lastIndexOf(".") + 3)
							+ "M";
				} else {
					fileSize += "M";
				}
			} else {
				float size_KB = size / (float) 1024;
				fileSize = String.valueOf(size_KB);
				if (fileSize.lastIndexOf(".") > -1) {
					fileSize = (fileSize + "00000").substring(0,
							fileSize.lastIndexOf(".") + 3)
							+ "KB";
				} else {
					fileSize += "KB";
				}
			}
			headImage = b64ToFile(request, imgB64, suffix.toLowerCase());
			if (!tmpFile.isEmpty()) {
				String fileName = headImage.substring(6, headImage.length());
				File filepath = new File(FILE_PATH, fileName);
				if (!filepath.getParentFile().exists()) {
					filepath.getParentFile().mkdirs();
				}
				// 将上传文件保存到一个目标文件当中
				tmpFile.transferTo(new File(FILE_PATH + File.separator
						+ fileName));
			}
			map.put("fileName", name);
			map.put("fileSub", suffix.toLowerCase());
			map.put("status", "success");
			map.put("fileUrl", headImage);
			map.put("fileSize", fileSize);
		} catch (IOException e) {
			map.put("status", "fail");
			e.printStackTrace();
		}
		return map;
	}

	public String b64ToFile(HttpServletRequest request, String imgStr,
			String suffix) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
		Decoder decoder = Base64.getDecoder();
		byte[] b = decoder.decode(imgStr);
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}
		String date = UUID.randomUUID().toString();
		String fileName = date + suffix;
		return "files/" + fileName;
	}

	@Override
	public Map<String, Object> uploadFile(MultipartFile file, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			long size = file.getSize();
			// 大文件处理
			String fileSize = "0.0KB";
			if (size > fileSize_1G) {
				float size_G = size / (float) 1073741824;
				fileSize = String.valueOf(size_G);
				if (fileSize.lastIndexOf(".") > -1) {
					fileSize = (fileSize + "00000").substring(0,
							fileSize.lastIndexOf(".") + 3)
							+ "G";
				} else {
					fileSize += "G";
				}
			} else if (size > fileSize_1M) {
				float size_M = size / (float) 1048576;
				fileSize = String.valueOf(size_M);
				if (fileSize.lastIndexOf(".") > -1) {
					fileSize = (fileSize + "00000").substring(0,
							fileSize.lastIndexOf(".") + 3)
							+ "M";
				} else {
					fileSize += "M";
				}
			} else {
				float size_KB = size / (float) 1024;
				fileSize = String.valueOf(size_KB);
				if (fileSize.lastIndexOf(".") > -1) {
					fileSize = (fileSize + "00000").substring(0,
							fileSize.lastIndexOf(".") + 3)
							+ "KB";
				} else {
					fileSize += "KB";
				}
			}
			String name = file.getOriginalFilename();
			String suffix = name.substring(name.lastIndexOf("."), name.length());
			String realFileName = b64ToFile(request, "", suffix.toLowerCase());
			if (!file.isEmpty()) {
				String fileName = realFileName.substring(6, realFileName.length());
				File filepath = new File(CMS_FILE_PATH, fileName);
				if (!filepath.getParentFile().exists()) {
					filepath.getParentFile().mkdirs();
				}
				// 将上传文件保存到一个目标文件当中
				file.transferTo(new File(CMS_FILE_PATH + File.separator+ fileName));
			}
			map.put("fileName", name);
			map.put("fileSub", suffix.toLowerCase());
			map.put("fileUrl", CMS_FILE_PATH + "\\" + realFileName.substring(6, realFileName.length()));
			map.put("fileSize", fileSize);
			map.put("status", "success");
		} catch (IllegalStateException e) {
			map.put("status", "fail");
			e.printStackTrace();
		} catch (IOException e) {
			map.put("status", "fail");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> uploadImage(MultipartFile file, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String name = file.getOriginalFilename();
			String suffix = name.substring(name.lastIndexOf("."), name.length());
			String realFileName = b64ToFile(request, "", suffix.toLowerCase());
			if (!file.isEmpty()) {
				String fileName = realFileName.substring(6, realFileName.length());
				File filepath = new File(IMG_FILE_PATH, fileName);
				if (!filepath.getParentFile().exists()) {
					filepath.getParentFile().mkdirs();
				}
				// 将上传文件保存到一个目标文件当中
				file.transferTo(new File(IMG_FILE_PATH + File.separator+ fileName));
				map.put("code", 0);
				map.put("msg", "");
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("src", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/zdata-ias/images/"+realFileName.substring(6, realFileName.length()));
				data.put("title", realFileName.substring(6, realFileName.length()));
				map.put("data", data);
			}
		} catch (IllegalStateException e) {
			map.put("status", "fail");
			e.printStackTrace();
		} catch (IOException e) {
			map.put("status", "fail");
			e.printStackTrace();
		}
		return map;
	}
}
