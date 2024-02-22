package com.graduate.controller.F30;

import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.service.HistoryService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class F3004 {
    @Autowired
    HistoryService historyService;
    /**
     * 进入首页
     * @return
     */
    @GetMapping("/F3004")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F30/F3004/index.html");
            log.info("/F3004 执行,进入首页");
        }catch (Exception e){
            log.error("/F3004 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }
    @PostMapping("/F3004/ajax1")
    public String ajax1(){
        CommonResult result = new CommonResult();
        try{
            result = historyService.LoadHistory();
            log.info("/F3004/ajax1 执行,提醒信息:{}","获取图表数据");
        }catch (Exception e){
            log.error("/F3004/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
