package com.graduate.controller.F10;

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
 * 注册界面
 */
public class F1011 {
    @Autowired
    UserService userService;
    /**
     * 注册界面进入
     * @return
     */
    @GetMapping("/F1011")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F10/F1011/index.html");
            log.info("/F1011 执行,进入注册界面");
        }catch (Exception e){
            log.error("/F1011 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }

    /**
     * 注册按钮API
     * @return
     */
    @PostMapping("/F1011/ajax1")
    public String register(@RequestParam("userId") String userId,@RequestParam("name") String name,@RequestParam("password") String password){
        CommonResult result = new CommonResult();
        try{
            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            user.setName(name);
            int ID = userService.InsertUser(user);
            if(ID > 0){
                result.setCode(200);
                result.setMsg("200");
                log.info("/F1011/ajax1 执行,userId:{},提醒信息:{}",userId,"注册成功");
            }
            else{ // 用户ID重叠注册
                result.setCode(403);
                result.setMsg("403-1");
                log.warn("/F1011/ajax1 执行,提醒信息:{}","userId重叠");
            }
        }catch (Exception e){
            log.error("/F1011/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
