package com.graduate.controller.F30;

import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.bean.User;
import com.graduate.service.AdminService;
import com.graduate.service.UserService;
import com.graduate.utils.VHelper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
public class F3001 {
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;
    /**
     * 管理员界面  拦截器的使用
     * @return
     */
    @GetMapping("/admin")
    public String Index(@CookieValue(value = "ID",defaultValue = "-1") int ID, HttpServletResponse response) throws IOException {
        if(ID == -1){
            response.sendRedirect("/F1010");
            return "";
        }else{
            int cnt = adminService.CountAdmin((ID - 985) / 380);
            if(cnt == 0){
                response.sendRedirect("/");
                return "";
            }else{
                VHelper vHelper = null;
                try{
                    vHelper = new VHelper("T/F30/F3001/index.html");
                    log.info("/admin 执行,进入管理员界面");
                }catch (Exception e){
                    log.error("/admin 执行错误,错误信息:{}",e.getMessage());
                }
                return vHelper.render();
            }
        }
    }

    /**
     * 获取用户信息
     * @param ID
     * @return
     */
    @PostMapping("/F3001/ajax1")
    public String ajax1(@RequestParam("ID") int ID){
        CommonResult result = new CommonResult();
        try{
            User user = new User();
            ID = (ID - 985) / 380;
            user.setId(ID);
            result = userService.LoadUserByIdF2011(user);
            if(result.getCode() == 200){
                log.info("/F3001/ajax1 执行,id:{},提醒信息:{}",ID,"获取用户信息");
            }else{
                log.warn("/F3001/ajax1 执行,ID错误");
            }
        }catch (Exception e){
            log.error("/F3001/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
