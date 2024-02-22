package com.graduate.controller.F20;

import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.bean.User;
import com.graduate.service.UserService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人修改信息界面
 */
@RestController
@Slf4j
public class F2011 {
    @Autowired
    UserService userService;
    /**
     * 个人信息界面
     * @return
     */
    @GetMapping("/F2011")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F20/F2011/index.html");
            log.info("/F2011 执行,进入用户信息界面");
        }catch (Exception e){
            log.error("/F2011 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }

    /**
     * 获取F2011界面的用户数据
     * @param ID
     * @return
     */
    @PostMapping("/F2011/ajax1")
    public String ajax1(@RequestParam("ID") int ID){
        CommonResult result = new CommonResult();
        try{
            User user = new User();
            ID = (ID - 985) / 380;
            user.setId(ID);
            result = userService.LoadUserByIdF2011(user);
            if(result.getCode() == 200){
                log.info("/F2011/ajax1 执行,id:{},提醒信息:{}",ID,"获取用户信息");
            }else{
                log.warn("/F2011/ajax1 执行,ID错误");
            }
        }catch (Exception e){
            log.error("/F2011/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 修改用户数据信息
     * @param userString
     * @return
     */
    @PostMapping("/F2011/ajax2")
    public String ajax2(@RequestParam("user") String userString){
        CommonResult result = new CommonResult();
        try{
            User user = JSONUtil.toBean(userString, User.class);
            result = userService.UpdateUserData(user);
            log.info("/F2011/ajax2 执行,id:{},提醒信息:{}",user.getId(),"修改用户信息");
        }catch (Exception e) {
            log.error("/F2011/ajax2 执行出现错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 修改头像
     * @param ID
     * @param photo
     * @return
     */
    @PostMapping("/F2011/ajax3")
    public String ajax3(@CookieValue("ID") int ID, @RequestParam("file") MultipartFile photo){
        CommonResult result = new CommonResult();
        try{
            ID = (ID - 985) / 380;
            result = userService.UpdateUserPicture(photo,ID);
            log.info("/F2011/ajax3 执行,提醒信息:{}","修改图片");
        }catch (Exception e){
            log.error("/F2011/ajax3 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
