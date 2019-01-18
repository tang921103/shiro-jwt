package com.sj.controller;

import com.sj.entities.UserEntity;
import com.sj.utils.JWTUtil;
import com.sj.utils.MsgUtil;
import com.sj.utils.PasswordUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @RequestMapping("/main")
    public String showLogin(){
        return "login";
    }
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(UserEntity userEntity){
        System.out.println(userEntity);
        Map<String,Object> map = MsgUtil.returnRightMsg(null);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userEntity.getUsername(), userEntity.getPassword());
        if(!subject.isAuthenticated()){
            try{
                //通过jwt签名后得到的token
                String token = JWTUtil.sign(userEntity.getUsername(), PasswordUtil.encodePwd(userEntity.getUsername(),userEntity.getPassword()));
                //返回给页面
                map.put("token",token);
                subject.login(usernamePasswordToken);
            }catch (UnknownAccountException e){
                logger.info("账号不存在");
                map = MsgUtil.returnErrorMsg("账号不存在");
            }catch(IncorrectCredentialsException e){
                logger.info("密码错误");
                map = MsgUtil.returnErrorMsg("密码错误");
            }catch (AuthenticationException e){
                logger.info("认证失败");
                map = MsgUtil.returnErrorMsg("认证失败");
            }
        }else{
            logger.info("已经登陆了");
            map = MsgUtil.returnErrorMsg("已经登陆了");
        }
        return map;
    }
}
