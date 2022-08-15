package com.kawai.fdtp.filter;

import com.alibaba.fastjson.JSON;
import com.kawai.fdtp.common.JWTUtil;
import com.kawai.fdtp.common.R;
import com.kawai.fdtp.common.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@WebFilter(filterName = "JWTFilter",urlPatterns = "/*",asyncSupported = true)
public class JWTFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json;charset=UTF-8");

        String requestURI = request.getRequestURI();

        log.info("拦截到请求 {}",requestURI);

        String[] urls = {
                "/user/login",
                "/user/logout",
                "/user/register"
        };

        boolean check = Check(urls, requestURI);

        if(check){
            log.info("无需过滤处理");
            filterChain.doFilter(request,response);
            return;
        }

        String token = request.getHeader("token");
        if(token!=null){
            Map<String ,String> map = JWTUtil.parseAccessToken(token);
            String user  = map.get("user");
            String role = map.get("role");

            if(SpringUtil.checkUrl(requestURI,role)){
                log.info("用户请求成功:user={},url={}",user,requestURI);
                filterChain.doFilter(request,response);
                return;
            }

            log.info("用户缺少权限:user={},url={}",user,requestURI);
            response.getWriter().write(JSON.toJSONString(R.noAuth("用户缺少权限")));
            return;
        }

        log.info("请求中无token");
        response.getWriter().write(JSON.toJSONString(R.noAuth("请求中无token")));


    }

    public boolean Check(String[] urls,String requestURI){
        for(String url : urls){
            boolean flag = PATH_MATCHER.match(url, requestURI);
            if(flag) return true;
        }

        return false;
    }
}
