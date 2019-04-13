package top.wanmudong.oamly.modules.common.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wanmudong
 * @date 17:23 2019/1/10
 */
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("TokenFilter运行");
//        boolean isLogin = isLoginOut((HttpServletRequest) request);
//        HttpServletRequest request1 = (HttpServletRequest) servletRequest;
//        UsernamePasswordToken token = request1.getParameter("token");
//        if (!StringUtils.isEmpty(token)){
//            Subject subject = SecurityUtils.getSubject();
//            subject.login(token);
//        }
//
//        if (isLogin) {
//            chain.doFilter(request, response);
//        } else {
////            不存在则跳转到返回已注销的消息
//            request1.getRequestDispatcher(request1.getContextPath() + "/loginOut/msg").forward(request1, response1);
//
//        }
    }

    @Override
    public void destroy() {

    }
}
