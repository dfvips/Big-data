package com.zdata.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.dao.SysFuncDao;
import com.zdata.dao.SysRemindDao;
import com.zdata.dao.SysRoleFuncDao;
import com.zdata.dao.SysSoftFuncDao;
import com.zdata.dao.SysUserFuncDao;
import com.zdata.model.SysFunc;
import com.zdata.model.Ztree;
import com.zdata.service.SysFuncService;
import com.zdata.util.RSAUtils;
import com.zdata.util.ZtreeUtils;

@Service("sysFuncService")
public class SysFuncServiceImpl implements SysFuncService {
	
	@Autowired
	private SysFuncDao sysFuncDao;
	
	@Autowired
	private SysSoftFuncDao sysSoftFuncDao;
	
	@Autowired
	private SysRemindDao sysRemindDao;
	
	@Autowired
	private SysRoleFuncDao sysRoleFuncDao;
	
	@Autowired
	private SysUserFuncDao sysUserFuncDao;
	
	@Override
	public Map<String, Object> findByPidAndUserId(String funcId, String userId, String publicKey,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//公钥解密
			userId = RSAUtils.publicDecrypt(userId, RSAUtils.getPublicKey(publicKey));
			List<SysFunc> funcList = sysFuncDao.findByPidAndUserId(funcId, userId,request);
			for (int i = 0; i < funcList.size(); i++) {
				if(funcList.get(i).getIsParent()==1){
					String parentId = funcList.get(i).getFuncId();
					List<SysFunc> children = sysFuncDao.findByPidAndUserId(parentId, userId,request);
					funcList.get(i).setChildren(children);
				}
			}
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
			map.put(Constant.Rows_Name.getValue(), funcList);
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}


	@Override
	public Map<String, Object> findBySoftId(Integer softId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysFunc func = sysFuncDao.findByFuncId(softId.toString());
			List<SysFunc> funcList = sysFuncDao.findLikeByPid(softId.toString());
			final List<Ztree> trees = new ArrayList<Ztree>();
			trees.add(buileTree(func));
			if (funcList!=null) {
				for (SysFunc e : funcList) {
					trees.add(buileTree(e));
				}
			}
			
			ZtreeUtils ztreeUtils = new ZtreeUtils(trees);
			List<Ztree> result = ztreeUtils.builTree();
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
			map.put(Constant.Rows_Name.getValue(), result);
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}


	private Ztree buileTree(SysFunc func) {
		Ztree z=new Ztree();
		z.setId(func.getFuncId());
		z.setName(func.getFuncName());
		z.setpId(func.getFuncParentId());
		return z;
	}


	@Override
	public Map<String, Object> save(SysFunc sysFunc) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysFunc.setExchangTime(new Date());
			if (sysFunc.getId()==null) {
				sysFuncDao.add(sysFunc);
			}else{
				sysFuncDao.update(sysFunc);
			}
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}


	@Override
	public Map<String, Object> deleteByFuncId(String funcId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//删除预设权限中
			sysSoftFuncDao.deleteByFuncId(funcId);
			sysRemindDao.deleteByFuncId(funcId);
			sysRoleFuncDao.deleteByFuncId(funcId);
			sysUserFuncDao.deleteByFuncId(funcId);
			sysFuncDao.deleteByFuncId(funcId);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}


	@Override
	public Map<String, Object> findByFuncId(String funcId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysFunc sysFunc = sysFuncDao.findByFuncId(funcId);
			map.put(Constant.Row_Name.getValue(), sysFunc);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
		}
		return map;
	}


	@Override
	public Map<String, Object> saveCopy(String selectFuncId, String objectFuncId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//目标非叶子节点
			if(sysFuncDao.isParent(objectFuncId)){
				SysFunc selectFunc = sysFuncDao.findByFuncId(selectFuncId);
				SysFunc objectFunc = sysFuncDao.findByFuncId(objectFuncId);
				List<SysFunc> list = sysFuncDao.findLikeByFuncId(selectFuncId);
				//目标对象中的子节点
				List<SysFunc> objectList = sysFuncDao.findByPid(objectFuncId);
				Integer number = Integer.valueOf(objectList.get(0).getFuncId().substring(objectList.get(0).getFuncId().length()-3, objectList.get(0).getFuncId().length()))+1;
				String last = String.format("%03d", number);
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setId(null);
					String id = list.get(i).getFuncId().replaceFirst(selectFunc.getFuncParentId(), objectFunc.getFuncId());
					String pId = list.get(i).getFuncParentId().replaceFirst(selectFunc.getFuncParentId(), objectFunc.getFuncId());
					//处理父节点
					if(pId.length()>objectFunc.getFuncId().length()){
						String after = last + pId.substring(objectFunc.getFuncId().length()+3);
						String result = objectFunc.getFuncId() + after;
						pId = result;
						String afterId = last + id.substring(objectFunc.getFuncId().length()+3);
						String resultId = objectFunc.getFuncId() + afterId;
						id = resultId;
					}else{
						//更新节点就可以
						id = pId + last;
					}
					list.get(i).setFuncId(id);
					list.get(i).setFuncParentId(pId);
					sysFuncDao.add(list.get(i));
				}
			//目标叶子节点
			}else{
				SysFunc objectFunc = sysFuncDao.findByFuncId(objectFuncId);
				SysFunc selectFunc = sysFuncDao.findByFuncId(selectFuncId);
				List<SysFunc> list = sysFuncDao.findLikeByFuncId(selectFuncId);
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setId(null);
					String id = list.get(i).getFuncId().substring(selectFunc.getFuncParentId().length());
					String pId = list.get(i).getFuncParentId().substring(selectFunc.getFuncParentId().length());
					list.get(i).setFuncId(objectFunc.getFuncId()+id);
					list.get(i).setFuncParentId(objectFunc.getFuncId()+pId);
					sysFuncDao.add(list.get(i));
				}
				
			}
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
		}
		return map;
	}

	/**
	 * 获取两数组相同的字符串
	 * @param:        @param strArr1
	 * @param:        @param strArr2
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月25日 下午11:03:16 
	 */
	public static List<String> getAllSameElement(List<SysFunc> list1,List<SysFunc> list2){ 
        List<String> list = new ArrayList<String>();  
        int k = 0;  
        int j = 0;  
        while(k<list1.size() && j<list2.size()) {  
            if(list1.get(k).getFuncId().compareTo(list2.get(j).getFuncParentId())==0) {  
                if(list1.get(k).getFuncId().equals(list2.get(j).getFuncParentId()) ) {  
                    list.add(list1.get(k).getFuncId());  
                    k++;  
                    j++;  
                }  
                continue;  
            } else  if(list1.get(k).getFuncId().compareTo(list2.get(j).getFuncParentId())<0){  
                k++;  
            } else {  
                j++;  
            }  
        }  
       return list;  
    }  
}
