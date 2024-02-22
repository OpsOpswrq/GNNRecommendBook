package com.graduate.service.Impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.graduate.bean.CommonResult;
import com.graduate.bean.History;
import com.graduate.mapper.HistoryMapper;
import com.graduate.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    HistoryMapper historyMapper;
    @Override
    public JSONObject CountByBClass1(int bclass1, int userId) {
        String now = DateUtil.now();
        DateTime date = DateUtil.parse(now);
        DateTime beforeDate = DateUtil.offsetDay(date, -7);
        return historyMapper.CountByBClass1(bclass1,userId,beforeDate,date);
    }

    @Override
    public JSONObject CountByBClass1ANDBClass2(int bclass1, int bclass2, int userId) {
        String now = DateUtil.now();
        DateTime date = DateUtil.parse(now);
        DateTime beforeDate = DateUtil.offsetDay(date, -7);
        return historyMapper.CountByBClass1ANDBClass2(bclass1,bclass2,userId,beforeDate,date);
    }

    @Override
    public void AddHistory(History history) {
        DateTime date = DateUtil.date();
        history.setDate(date);
        historyMapper.InsertHistory(history);
    }

    @Override
    public CommonResult LoadHistory() {
        CommonResult result = new CommonResult();
        JSONObject entries = new JSONObject();
        List<JSONObject> jsonObjects = historyMapper.CountHotClass1();
        entries.set("all",jsonObjects);
        for(int i = 0;i < jsonObjects.size();i++){
            List<JSONObject> json = historyMapper.CountHotClass2(i + 1);
            entries.set("hot"+ (i+1),json);
        }
        JSONObject[] users_cnt = new JSONObject[7];
        DateTime date = DateUtil.date();
        String nowS = DateUtil.format(date, "yyyy-MM-dd");
        Date nowDay = DateUtil.parse(nowS);
        for(int i = 0;i < 7;i++){
            DateTime end = DateUtil.offsetDay(nowDay, -i);
            DateTime begin = DateUtil.offsetDay(nowDay, (-i) - 1);
            int cnt = historyMapper.CountHistoryByDay(begin, end);
            String format = DateUtil.format(end, "yyyy-MM-dd");
            JSONObject entries1 = new JSONObject();
            entries1.set("day",format);
            entries1.set("cnt",cnt);
            users_cnt[6 - i] = entries1;
        }
        entries.set("day",users_cnt);
        result.setCode(200);
        result.setMsg("200");
        result.setData(entries);
        return result;
    }
}
