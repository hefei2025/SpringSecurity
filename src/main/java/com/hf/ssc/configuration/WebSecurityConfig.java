package com.hf.ssc.configuration;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.hf.ssc.Service.MyUserDetailsService;
import com.hf.ssc.component.MyCaptchaAuthenticationProvider;
import com.hf.ssc.exception.MyAuthenticationFailureHandler;
import com.hf.ssc.filter.VerificationCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> myAuthenticationDetailsSource;

    @Autowired
    private AuthenticationProvider authenticationProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //??????authenticationProvider
        auth.authenticationProvider(authenticationProvider);
    }

    /*@Bean
    public PasswordEncoder passwordEncoder(){
        return new MyPasswordEncoder();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**","/captcha.jpg").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .formLogin()
                .failureHandler(new MyAuthenticationFailureHandler())
                .loginPage("/loginTest")
                .loginProcessingUrl("/auth/form").permitAll()
                //??????AuthenticationProvider??????????????????????????????myAuthenticationDetailsSource
                .authenticationDetailsSource(myAuthenticationDetailsSource)
                .and()
                //??????????????????????????????????????????????????????
                //.rememberMe().userDetailsService(userDetailsService)
        ;
        //????????????????????????????????????????????????????????????UsernamePasswordAuthenticationFilter??????
        //http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);

        /*http.authorizeRequests().anyRequest().authenticated().and().formLogin()
                .loginPage("/myLogin.html")
                //?????????????????????????????????
                .loginProcessingUrl("/login")
                //????????????????????????????????????
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest
                            , HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"error_code\":\"0\",\"message\":\"??????????????????\"}");
                    }
                })
                //????????????????????????????????????
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest
                            , HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        httpServletResponse.setStatus(401);
                        out.write("{\"error_code\":\"401\",\"name\":\""+e.getClass()+"\",\"message\":\""+e.getMessage()+"\"}");
                    }
                })
                .permitAll().and().csrf().disable();*/
    }

    /*@Bean
    public PasswordEncoder passwordEncoder(){
        return new MyPasswordEncoder();
    }*/

    /**
     * ???????????????
     * @return
     */
    @Bean
    public Producer captcha(){
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width","150");
        properties.setProperty("kaptcha.image.hegiht","50");
        //?????????
        properties.setProperty("kaptcha.textproducer.char.string","0123456789ABCDEFGHIJKLMNOPQRSTYVWXYZabcdefghijklmnopqrstuvwxyz");
        //????????????
        properties.setProperty("kaptcha.textproducer.char.length","4");
        Config config = new Config(properties);
        //????????????????????????????????????
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

   /* @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().passwordEncoder(new MyPasswordEncoder())
                .withUser("user").password("123").roles("USER")
                .and()
                .withUser("admin").password("123").roles("USER","ADMIN");

    }*/
}
