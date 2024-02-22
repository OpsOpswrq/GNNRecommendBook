package com.graduate.service.Impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.graduate.bean.Recommend;
import com.graduate.mapper.RecommendMapper;
import com.graduate.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    RecommendMapper recommendMapper;
    @Override
    public void InsertRecommend(Recommend recommend) {
        recommendMapper.InsertRecommend(recommend);
    }

    @Override
    public Recommend SearchRecommend(int userId, int bclass1) {
        String now = DateUtil.now();
        DateTime date = DateUtil.parse(now);
        DateTime beforeDate = DateUtil.offsetDay(date, -1);
        return recommendMapper.SearchRecommend(userId,bclass1,beforeDate,date);
    }
}
