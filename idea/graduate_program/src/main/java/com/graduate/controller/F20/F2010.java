package com.graduate.controller.F20;

import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.bean.User;
import com.graduate.mapper.UserMapper;
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
 * 主页了
 */
public class F2010 {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    /**
     * 主页
     * @return
     */
    @GetMapping("/")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F20/F2010/index.html");
            log.info("/ 执行,进入书籍界面");
        }catch (Exception e){
            log.error("/ 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }

    /**
     * F2010 获取用户数据
     * @param ID
     * @return
     */
    @PostMapping("/F2010/ajax1")
    public String ajax1(@RequestParam("ID") int ID){
        CommonResult result = new CommonResult();
        try{
            User user = new User();
            ID = (ID - 985) / 380;
            user.setId(ID);
            result = userService.LoadUserByIdF2010(user);
            if(result.getCode() == 200){
                log.info("/F2010/ajax1 执行,id:{},提醒信息:{}",ID,"获取用户信息");
            }else{
                log.warn("/F2010/ajax1 执行,ID错误");
            }
        }catch (Exception e){
            log.error("/F2010/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
