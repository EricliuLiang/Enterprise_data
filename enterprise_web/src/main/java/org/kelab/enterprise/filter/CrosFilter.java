package org.kelab.enterprise.filter;

import org.cn.wzy.util.PropertiesUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Wzy
 * on 2018/5/8
 */
public class CrosFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 获取配置信息
        String AccessControlAllowOrigin = PropertiesUtil.StringValue("AccessControlAllowOrigin");
        String AccessControlAllowMethods = PropertiesUtil.StringValue("AccessControlAllowMethods");
        String AccessControlMaxAge = PropertiesUtil.StringValue("AccessControlMaxAge");
        String AccessControlAllowHeaders = PropertiesUtil.StringValue("AccessControlAllowHeaders");

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 表明它允许"http://xxx"发起跨域请求
        httpServletResponse.setHeader("Access-Control-Allow-Origin",
                AccessControlAllowOrigin);
        // 表明它允许xxx的请求方法
        httpServletResponse.setHeader("Access-Control-Allow-Methods",
                AccessControlAllowMethods);
        httpServletResponse.setHeader("Allow", AccessControlAllowMethods);
        // 表明在xxx秒内，不需要再发送预检验请求，可以缓存该结果
        httpServletResponse.setHeader("Access-Control-Max-Age",
                AccessControlMaxAge);
        // 表明它允许跨域请求包含xxx头
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                AccessControlAllowHeaders);
        chain.doFilter(request, response);
    }
}
