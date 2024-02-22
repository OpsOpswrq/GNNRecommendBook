package com.graduate.utils;

import cn.hutool.core.date.DateUtil;
import com.graduate.service.HistoryService;
import com.graduate.service.RecommendService;
import com.graduate.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@Slf4j
public class RecommendComputerUtil {
    @Autowired
    UserService userService;
    @Autowired
    HistoryService historyService;
    @Autowired
    RecommendService recommendService;
    @Scheduled(cron = "0 0 0 * * ?")  // 每日0点计算
    @GetMapping("/computer")
    public String Computer(){
        try{
            GNNUtil gnnUtil = new GNNUtil();
            gnnUtil.GNN1(userService,historyService,recommendService);
            log.info("定时任务执行了,时间是:{}", DateUtil.date().toDateStr());
        }catch (Exception e){
            log.error("定时任务执行错误,错误信息:{}",e.getMessage());
        }
        return "ok";
    }
}
