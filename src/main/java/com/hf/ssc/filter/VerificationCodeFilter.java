package com.hf.ssc.filter;

import com.hf.ssc.exception.MyAuthenticationFailureHandler;
import com.hf.ssc.exception.VerificationCodeException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *    专门用于校验验证码的工具
 */
public class VerificationCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler = new MyAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //非登录请求，不校验验证码
        if (!"/auth/form".equals(httpServletRequest.getRequestURI())){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }else{
            try{
                verificationCode(httpServletRequest);
                filterChain.doFilter(httpServletRequest,httpServletResponse);
            }catch (VerificationCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse, e );
            }
        }
    }

    public void verificationCode(HttpServletRequest httpServletRequest) throws VerificationCodeException {
        String requestCode = httpServletRequest.getParameter("captcha");
        HttpSession session = httpServletRequest.getSession();
        String savedCode = (String)session.getAttribute("captcha");
        if (savedCode != null && !savedCode.isEmpty()){
            //随手清除验证码，无论失败还是成功。客户端应在登录失败时刷新验证码
            session.removeAttribute("captcha");
        }
        //校验不通过，抛出异常
        if (requestCode==null || savedCode==null ||requestCode.isEmpty() || savedCode.isEmpty() || !requestCode.equalsIgnoreCase(savedCode)){
            throw new VerificationCodeException();
        }
    }

}
