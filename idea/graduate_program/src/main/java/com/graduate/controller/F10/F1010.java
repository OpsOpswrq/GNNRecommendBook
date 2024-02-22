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

@RestController
@Slf4j
/**
 * 登录界面
 */
public class F1010 {
    @Autowired
    UserService userService;

    /**
     * 登录界面进入
     * @return
     */
    @GetMapping("/F1010")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F10/F1010/index.html");
            log.info("/F1010 执行,进入登录界面");
        }catch (Exception e){
            log.error("/F1010 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }

    /**
     * 登录按钮API
     * @param userId
     * @param password
     * @return
     */
    @PostMapping("/F1010/ajax1")
    public String Login(@RequestParam("userId") String userId,@RequestParam("password") String password){
        CommonResult result = new CommonResult();
        try{
            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            int code = userService.UserLogin(user);
            if(code == -1){
                log.warn("/F1010/ajax1 执行,userId:{},提醒信息:{}",userId,"用户不存在");
                result.setCode(403);
                result.setMsg("403-1");  // 用户不存在
            }
            else if(code == -2){
                log.warn("/F1010/ajax1 执行,userId:{},提醒信息:{}",userId,"密码不正确");
                result.setCode(403);
                result.setMsg("403-2"); // 密码不正确
            }else {
                StpUtil.login(userId,new SaLoginModel().setTimeout(24*60*60*7).setIsLastingCookie(true));
                JSONObject token = new JSONObject();
                token.set("tokenName","ID");
                code = code * 380 + 985;
                token.set("tokenValue",code);
                log.info("/F1010/ajax1 执行,userId:{},提醒信息:{}",userId,"登录成功");
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
