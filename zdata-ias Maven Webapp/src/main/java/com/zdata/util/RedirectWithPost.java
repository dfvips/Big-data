package com.zdata.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 用POST方式 重定向
 *
 * @author royFly
 */
public class RedirectWithPost {
    Map<String, String> parameter = new HashMap<String, String>();
    HttpServletResponse response;

    public RedirectWithPost(HttpServletResponse response) {
        this.response = response;
    }

    public void setParameter(String key, String value) {
        this.parameter.put(key, value);
    }

    public void sendByPost(String url) throws IOException {
        this.response.setContentType("text/html");
        PrintWriter out = this.response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println(" <HEAD>");
        out.println(" <meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">");
        out.println(" <TITLE>loading</TITLE>");
        out.println(" <meta http-equiv=\"Content-Type\" content=\"text/html charset=GBK\">\n");
        out.println(" </HEAD>");
        out.println(" <BODY>");
        out.println("<form name=\"submitForm\" action=\"" + url + "\" method=\"post\">");
        Iterator<String> it = this.parameter.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            out.println("<input type=\"hidden\" name=\"" + key + "\" value=\"" + this.parameter.get(key) + "\"/>");
        }
        out.println("</from>");
        out.println("<script>window.document.submitForm.submit();</script> ");
        out.println(" </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }
}
