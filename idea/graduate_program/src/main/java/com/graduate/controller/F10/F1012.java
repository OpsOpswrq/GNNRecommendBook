package com.graduate.controller.F10;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.bean.User;
import com.graduate.service.UserService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理登录界面
 */
@RestController
@Slf4j
public class F1012 {
    @Autowired
    UserService userService;

    /**
     * 管理员登录
     * @return
     */
    @GetMapping("/F1012")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F10/F1012/index.html");
            log.info("/F1012 执行,进入管理员登录界面");
        }catch (Exception e){
            log.error("/F1012 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }


    /**
     * 管理员登录按钮API
     * @param userId
     * @param password
     * @return
     */
    @PostMapping("/F1012/ajax1")
    public String ajax1(@RequestParam("userId") String userId, @RequestParam("password") String password){
        CommonResult result = new CommonResult();
        try{
            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            int code = userService.UserLogin(user);
            if(code == -1){
                log.warn("/F1012/ajax1 执行,userId:{},提醒信息:{}",userId,"用户不存在");
                result.setCode(403);
                result.setMsg("403-1");  // 用户不存在
            }
            else if(code == -2){
                log.warn("/F1012/ajax1 执行,userId:{},提醒信息:{}",userId,"密码不正确");
                result.setCode(403);
                result.setMsg("403-2"); // 密码不正确
            }else {
                StpUtil.login(userId,new SaLoginModel().setTimeout(24*60*60*7).setIsLastingCookie(true));
                JSONObject token = new JSONObject();
                token.set("tokenName","ID");
                code = code * 380 + 985;
                token.set("tokenValue",code);
                log.info("/F1012/ajax1 执行,userId:{},提醒信息:{}",userId,"登录成功");
                result.setCode(200);
                result.setMsg("200");  // 登录成功
                result.setData(token); // 设置token数据
            }
        }catch (Exception e){
            log.error("/F1010/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
