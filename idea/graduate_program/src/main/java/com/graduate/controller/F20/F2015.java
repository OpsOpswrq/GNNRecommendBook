package com.graduate.controller.F20;

import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.bean.User;
import com.graduate.mapper.UserMapper;
import com.graduate.service.UserService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class F2015 {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    /**
     * 进入地址管理界面
     * @return
     */
    @GetMapping("/F2015")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F20/F2015/index.html");
            log.info("/F2015 执行,进入地址管理界面");
        }catch (Exception e){
            log.error("/F2015 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }

    /**
     * 获取用户信息地址
     * @param ID
     * @return
     */
    @PostMapping("/F2015/ajax1")
    public String ajax1(@RequestParam("ID") int ID){
        CommonResult result = new CommonResult();
        try{
            User user = new User();
            ID = (ID - 985) / 380;
            user.setId(ID);
            result = userService.LoadUserByIdF2011(user);
            if(result.getCode() == 200){
                log.info("/F2015/ajax1 执行,id:{},提醒信息:{}",ID,"获取用户信息");
            }else{
                log.warn("/F2015/ajax1 执行,ID错误");
            }
        }catch (Exception e){
            log.error("/F2015/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 更新地址
     * @param address
     * @param ID
     * @return
     */
    @PostMapping("/F2015/ajax2")
    public String ajax2(@RequestParam("address") String address, @Param("ID") int ID){
        CommonResult result = new CommonResult();
        try{
            userMapper.UpdateAddress(address,(ID - 985) / 380);
            result.setCode(200);
            result.setMsg("200");
        }catch (Exception e){
            log.error("/F2015/ajax2 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
