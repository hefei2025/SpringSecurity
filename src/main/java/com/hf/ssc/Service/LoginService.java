package com.hf.ssc.Service;

import com.hf.ssc.entity.User;
import com.hf.ssc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserMapper userMapper;

    public boolean loginCheck(String username,String password){
        boolean flag = false;
        User user = userMapper.findByUserNameAndPwd(username,password);

        if (user==null){
            System.err.println(username+"用户不存在");
        }else{
            flag = true;
        }
        return flag;
    }
}
