package com.graduate.service;

import cn.hutool.json.JSONObject;
import com.graduate.bean.CommonResult;
import com.graduate.bean.History;
import org.springframework.stereotype.Service;

@Service
public interface HistoryService {
    JSONObject CountByBClass1(int bclass1, int userId);
    JSONObject CountByBClass1ANDBClass2(int bclass1,int bclass2,int userId);
    void AddHistory(History history);
    CommonResult LoadHistory();
}
