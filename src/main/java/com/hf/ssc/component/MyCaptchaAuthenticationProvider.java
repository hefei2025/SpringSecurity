package com.hf.ssc.component;

import com.hf.ssc.Service.MyUserDetailsService;
import com.hf.ssc.configuration.MyPasswordEncoder;
import com.hf.ssc.exception.VerificationCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyCaptchaAuthenticationProvider extends DaoAuthenticationProvider {



    public MyCaptchaAuthenticationProvider(MyUserDetailsService userDetailsService
            , PasswordEncoder passwordEncoder){
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails
            , UsernamePasswordAuthenticationToken token) throws AuthenticationException {

        //获取详细信息
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) token.getDetails();
        //一旦发现验证码不正确，就立即抛出相应的异常信息
        if (!details.getImageCodeIsRight()){
            throw new VerificationCodeException();
        }
        //调用父类方法完成密码验证
        super.additionalAuthenticationChecks(userDetails,token);
        //调用自定义实现完成密码验证
        //myAuthenticationProvider.additionalAuthenticationChecks(userDetails,token);

    }
}
