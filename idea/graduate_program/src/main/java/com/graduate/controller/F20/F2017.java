package com.graduate.controller.F20;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.mapper.HistoryMapper;
import com.graduate.service.HistoryService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class F2017 {
    @Autowired
    HistoryMapper historyMapper;
    /**
     * 进入数据图表
     * @return
     */
    @GetMapping("/F2017")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F20/F2017/index.html");
            log.info("/F2017 执行,进入热门图书");
        }catch (Exception e){
            log.error("/F2017 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }
    @PostMapping("/F2017/ajax1")
    public String ajax1(@RequestParam("ID") int ID){
        CommonResult result = new CommonResult();
        try{
            ID = (ID - 985) / 380;
            DateTime date = DateUtil.date();
            String nowS = DateUtil.format(date, "yyyy-MM-dd");
            Date end = DateUtil.parse(nowS);
            Date begin = DateUtil.offsetDay(end, -7);
            List<JSONObject> jsonObjects = historyMapper.CountHistoryUser(ID, begin, end);
            List<JSONObject> jsonObjects1 = historyMapper.CountHistoryHOT(begin, end);
            JSONObject entries = new JSONObject();
            entries.set("hot1",jsonObjects);
            entries.set("hot2",jsonObjects1);
            result.setCode(200);
            result.setMsg("200");
            result.setData(entries);
            log.info("/F2017/ajax1 执行,提醒信息:{}","获取热门书籍分类数据");
        }catch (Exception e){
            log.error("/F2017/ajax1 执行错误.错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
