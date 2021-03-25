package com.zdata.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.zdata.constant.Constant;
import com.zdata.model.SysSoft;
import com.zdata.model.SysUser;
import com.zdata.model.User;
import com.zdata.service.SysSoftService;
import com.zdata.service.UserService;
import com.zdata.util.DesUtils;
import com.zdata.util.SessionUtils;
import com.zdata.util.StringUtil;

/**
 * 登陆过滤器
 * @author Administrator
 *
 */
@WebFilter(filterName="login",urlPatterns={"*.jsp"})
public class LoginFilter implements Filter {

	@Autowired
	private SysSoftService sysSoftService;
	
	@Autowired
	private UserService userService;
	
	private static final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);
	
	private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/views/register.jsp","/views/GFKD_login.jsp")));
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 获得在下面代码中要用的request,response,session对象
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        //跨域请求，*代表允许全部类型
        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
      	//允许请求方式
        servletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
      	//用来指定本次预检请求的有效期，单位为秒，在此期间不用发出另一条预检请求
        servletResponse.setHeader("Access-Control-Max-Age", "3600");
      	//请求包含的字段内容，如有多个可用哪个逗号分隔如下
        servletResponse.setHeader("Access-Control-Allow-Headers", "content-type,x-requested-with,Authorization, x-ui-request,lang");
      	//访问控制允许凭据，true为允许
        servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        
        String path = servletRequest.getRequestURI().substring(servletRequest.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);
        if (allowedPath) {
        	chain.doFilter(request, response);
		}else{
			HttpSession session = servletRequest.getSession();
			//从session获取当前用户
	        SysUser user = (SysUser) session.getAttribute(Constant.Current_User.getValue());
	        //用户不存在，尝试URL解密获取用户
	        if(user==null){
	        	String encoded = request.getParameter("IASloginUser");
				String cryptKey = request.getParameter("cryptKey");
				if(StringUtil.isNotEmpty(encoded)&&StringUtil.isNotEmpty(cryptKey)){
					try {
						String userId = DesUtils.decrypt(encoded, cryptKey);
						User curUser = this.userService.loadByUserId(userId);
						session.setAttribute(Constant.Current_User.getValue(), curUser);//当前用户
						SysSoft soft = sysSoftService.findBySoftId(curUser.getSysSoftId());
						session.setAttribute(Constant.Current_Soft.getValue(), soft);
						chain.doFilter(request, response); // 让目标资源执行，放行
						LOG.info("用户解密成功，session有效！");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	        }else{
	        	chain.doFilter(request, response); // 让目标资源执行，放行
	        }
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

}
