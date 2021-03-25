package com.zdata.ccc.util;

public class getWebPath {
    public static String getWebPaths(String fileName) {
        // file:/D:/JavaWeb/.metadata/.me_tcat/webapps/TestBeanUtils/WEB-INF/classes/
        String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
        // 去掉返回路径中各种不需要的东西
        path = path.replace('/', '\\'); // 将/换成\
        path = path.replace("file:", ""); // 去掉file:
        // path = path.replace("classes\\", ""); // 去掉class\
        path = path.substring(1); // 去掉第一个\,如 \D:\JavaWeb...
 
        // 如果有文件名，则在路径上加入文件名
        if(fileName!=null) {
            if (fileName.isEmpty() == false) {
                path += fileName;
            }
        }
        return path.replace("%20", " ").replace("\\classes", "");
    }
}
