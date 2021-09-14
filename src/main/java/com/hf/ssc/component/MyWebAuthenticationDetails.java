package com.hf.ssc.component;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean imageCodeIsRight;

    public boolean getImageCodeIsRight() {
        return this.imageCodeIsRight;
    }

    //补充用户提交的验证码和session保存的验证码
    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String imageCode = request.getParameter("captcha");
        HttpSession session = request.getSession();
        String savedImageCode = (String) session.getAttribute("captcha");
        if (!StringUtils.isEmpty(savedImageCode)){
            //随手清除验证码，无论失败还是成功。客户端应在登录失败时刷新验证码
            session.removeAttribute("captcha");
            if (!StringUtils.isEmpty(imageCode) && imageCode.equalsIgnoreCase(savedImageCode)){
                this.imageCodeIsRight = true;
            }
        }
    }
}
