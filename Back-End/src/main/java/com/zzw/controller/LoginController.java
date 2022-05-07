package com.zzw.controller;

import com.zzw.entity.User;
import com.zzw.service.LoginService;
import com.zzw.utils.LoginSubmit;
import com.zzw.utils.Result;
import com.zzw.utils.encryption.AesFront;
import com.zzw.utils.jwttoken.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    @CrossOrigin
    public Result<User> selectLogin(@RequestBody LoginSubmit loginSubmit) throws Exception {

        User user = new User();
        user.setName(loginSubmit.getName());
        //先对前端传过来的东西进行解密。
        String decrypted = AesFront.aesDecrypt(loginSubmit.getPassword(),"1985718124500000");
        user.setPassword(decrypted);


        if(loginService.selectLogin(user) == 1){
            User user0 = loginService.selectLoginUser(user);  //这个uer0是数据库中完整的那个user不是前端传进来的那个只有用户名和密码的user
            String token = JwtUtils.tokenGenerate(user0.getId(),user0.getName(),user0.getPower());
            user0.setPassword(null);
            user0.setToken(token);
            return Result.success(user0);
        }
        return Result.error("-1","登录失败！");
    }

}
