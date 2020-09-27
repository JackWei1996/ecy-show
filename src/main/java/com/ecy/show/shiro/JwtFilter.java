package com.ecy.show.shiro;

import com.ecy.show.util.InterceptorUtil;
import com.ecy.show.util.JwtUtil;
import com.ecy.show.util.TResultCode;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//代码的执行流程 preHandle -> isAccessAllowed -> isLoginAttempt -> executeLogin
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String token = JwtUtil.getToken(httpServletRequest);

        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 执行登录认证
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        try {
            executeLogin(request, response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            try {
                InterceptorUtil.AccessFail(request,response,e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //如果抛出此异常，全局异常拦截也无法
//            throw new AuthenticationException(e.getMessage());
        }
        return true;
    }

    /**
     * 该方法会在验证失败后调用，这里由于是前后端分离，后台不控制页面跳转
     * 因此重写改成传输JSON数据
     */
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        InterceptorUtil.AccessFail(request,response, TResultCode.USER_NOT_LOGGED_IN);
    }
    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            InterceptorUtil.allowCORS(httpServletRequest,httpServletResponse);
            return true;
        }
        return super.preHandle(request, response);
    }

}
