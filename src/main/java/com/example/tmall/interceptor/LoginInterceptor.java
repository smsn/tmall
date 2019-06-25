package com.example.tmall.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// import com.example.tmall.model.User;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * LoginInterceptor 拦截器 中间件
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        //字符串数组 requireAuthPages，存放那些需要登录才能访问的路径
        String[] requireAuthPages = new String[]{
            "buy",
            "alipay",
            "payed",
            "cart",
            "bought",
            "confirmPay",
            "orderConfirmed",
            "forebuyone",
            "forebuy",
            "foreaddCart",
            "forecart",
            "forechangeOrderItem",
            "foredeleteOrderItem",
            "forecreateOrder",
            "forepayed",
            "forebought",
            "foreconfirmPay",
            "foreorderConfirmed",
            "foredeleteOrder",
            "forereview",
            "foredoreview"
        };
        String uri = request.getRequestURI();
        //去掉前缀
        String page = StringUtils.remove(uri, contextPath + "/");
        if (beginWith(page, requireAuthPages)) {
            // User user = (User) session.getAttribute("user");
            // if (user == null) {
            //     response.sendRedirect("login");
            //     return false;
            // }
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                response.sendRedirect("login");
                return false;
            }
        }
        return true;
    }

    private boolean beginWith(String page, String[] requireAuthPages) {
        for (String requireAuthPage : requireAuthPages) {
            if (StringUtils.startsWith(page, requireAuthPage)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}