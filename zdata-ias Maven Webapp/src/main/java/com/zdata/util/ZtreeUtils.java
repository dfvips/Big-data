package com.zdata.util;

import java.util.ArrayList;
import java.util.List;

import com.zdata.model.Ztree;

public class ZtreeUtils {

	private List<Ztree> ZtreeList = new ArrayList<Ztree>();
	
    public ZtreeUtils(List<Ztree> ZtreeList) {
        this.ZtreeList=ZtreeList;
    }

    //建立树形结构
    public List<Ztree> builTree(){
        List<Ztree> treeZtrees =new  ArrayList<Ztree>();
        for(Ztree ZtreeNode : getRootNode()) {
            ZtreeNode=buildChilTree(ZtreeNode);
            treeZtrees.add(ZtreeNode);
        }
        return treeZtrees;
    }

    //递归，建立子树形结构
    private Ztree buildChilTree(Ztree pNode){
        List<Ztree> chilZtrees =new  ArrayList<Ztree>();
        for(Ztree ZtreeNode : ZtreeList) {
            if(ZtreeNode.getpId().equals(pNode.getId())) {
                chilZtrees.add(buildChilTree(ZtreeNode));
            }
        }
        pNode.setChildren(chilZtrees);
        return pNode;
    }

    //获取根节点
    private List<Ztree> getRootNode() {
        List<Ztree> rootZtreeLists =new  ArrayList<Ztree>();
        for(Ztree ZtreeNode : ZtreeList) {
            if(ZtreeNode.getpId().equals("000")) {
                rootZtreeLists.add(ZtreeNode);
            }
        }
        return rootZtreeLists;
    }
}
