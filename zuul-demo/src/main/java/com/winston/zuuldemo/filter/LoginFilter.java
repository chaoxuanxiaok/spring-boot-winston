package com.winston.zuuldemo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 模拟登陆校验的过滤器
 */
public class LoginFilter extends ZuulFilter {

    @Override
    public String filterType() {
        //登录校验，在前置拦截
        return "pre";
    }

    @Override
    public int filterOrder() {
        //顺序设置为1
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        // 返回true，代表过滤器生效
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        //登陆校验逻辑
        // 1.获取Zuul提供的请求上下文对象
        RequestContext ctx = RequestContext.getCurrentContext();
        // 2.从上下文中获取request对象
        HttpServletRequest req = ctx.getRequest();
        // 3.从请求中获取token
        String token = req.getParameter("token-access");
        // 4.判断
        if(token == null || "".equals(token.trim())){
            //没有token，校验失败，拦截
            ctx.setSendZuulResponse(false);
            //返回401状态码，或者重定向到登录页
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        //校验通过，也可以考虑将 用户信息放入上下文，继续向后执行
        return null;
    }
}
