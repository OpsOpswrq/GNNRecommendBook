package com.graduate.controller.F20;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.mapper.BookMapper;
import com.graduate.mapper.HistoryMapper;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class F2016 {
    @Autowired
    HistoryMapper historyMapper;
    @Autowired
    BookMapper bookMapper;
    /**
     * 进入猜你喜欢
     * @return
     */
    @GetMapping("/F2016")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F20/F2016/index.html");
            log.info("/F2016 执行,进入猜你喜欢");
        }catch (Exception e){
            log.error("/F2016 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }
    @PostMapping("/F2016/ajax1")
    public String ajax1(@RequestParam("ID") int ID,@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize){
        CommonResult result = new CommonResult();
        try{
            ID = (ID - 985) / 380;
            DateTime date = DateUtil.date();
            String nowS = DateUtil.format(date, "yyyy-MM-dd");
            Date end = DateUtil.parse(nowS);
            Date begin = DateUtil.offsetDay(end, -7);
            List<JSONObject> integers = historyMapper.CountHistoryUserGETTWO(ID, begin, end);
            List<JSONObject> jsonObjects = new ArrayList<>();
            pageSize = pageSize / 2;
            List<JSONObject> jsonObjects1 = bookMapper.SearchBookANDGETONE(ID, Integer.parseInt(integers.get(0).get("bclass1").toString()), (currentPage - 1) * (pageSize + 5), (pageSize + 5));
            List<JSONObject> jsonObjects2 = bookMapper.SearchBookANDGETONEHALF(ID, Integer.parseInt(integers.get(1).get("bclass1").toString()), (currentPage - 1) * (pageSize - 5), (pageSize - 5));
            jsonObjects.addAll(jsonObjects1);
            jsonObjects.addAll(jsonObjects2);
            result.setCode(200);
            result.setMsg("200");
            result.setData(jsonObjects);
            log.info("/F2016/ajax1 执行,提醒信息:{}","加载推荐图书");
        }catch (Exception e){
            log.error("/F2016/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
