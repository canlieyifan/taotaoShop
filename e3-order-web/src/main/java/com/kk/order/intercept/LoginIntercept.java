package com.kk.order.intercept;

import com.kk.pojo.User;
import com.kk.sso.UserService;
import com.kk.utils.CookieUtils;
import com.kk.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginIntercept implements HandlerInterceptor {

    @Value("${COOKIE_USER}")
    private String userCookie;

    @Value("${SSO_URL}")
    private String ssoUrl;


    @Autowired
    private UserService userService;


    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        System.out.println("hello");
//        cookie中取出对应的token值
        String token= CookieUtils.getCookieValue(httpServletRequest, userCookie);
//            进行业务逻辑判断
        if(StringUtils.isBlank(token)) {
            httpServletResponse.sendRedirect(ssoUrl+"redirect="+ httpServletRequest.getRequestURL());
            return false;
        }
//      根据user判断是否登陆过期
        E3Result e3Result = userService.checkUserInfo(token);


        if(e3Result.getStatus()!=200){
            httpServletResponse.sendRedirect(ssoUrl+"redirect="+ httpServletRequest.getRequestURL());
            return false;
        }

        httpServletRequest.setAttribute("user",(User)e3Result.getData());
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
