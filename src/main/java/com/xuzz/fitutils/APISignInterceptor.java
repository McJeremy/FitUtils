package com.rongqiangu.weather.common.web;

import com.rongqiangu.weather.util.APISignUtil;
import com.rongqiangu.weather.util.StringUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * api接口验证
 */
public class APISignInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //不需要验证的规则

        //文件上传不需要签名认证
        if (request.getRequestURI().contains("/file/upload")) {
            return true;
        }

       if(!APISignUtil.validateSign(request)){
            if (request.getRequestURI().contains("/auth/login")) {
                return true;
            }
            System.out.println("sign error.");
            return false;
        }

        System.out.println("sign ok.");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
