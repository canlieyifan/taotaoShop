package com.kk.cart.intercept;

import com.kk.cart.CartService;
import com.kk.pojo.User;
import com.kk.sso.UserService;
import com.kk.utils.CookieUtils;
import com.kk.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

public class LoginIntercept implements HandlerInterceptor {

    @Value("${COOKIE_USER}")
    private String userCookie;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

//        cookie中取出对应的token值
        String token= CookieUtils.getCookieValue(httpServletRequest, userCookie);
//            进行业务逻辑判断
        if(StringUtils.isBlank(token)) {
            return true;
        }
//      根据user判断是否登陆过期
        E3Result e3Result = userService.checkUserInfo(token);

        if(e3Result.getStatus()!=200){
            return true;
        }


        httpServletRequest.setAttribute("user",(User)e3Result.getData());
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
