package com.graduate.service;

import com.graduate.bean.Recommend;
import org.springframework.stereotype.Service;

@Service
public interface RecommendService {
    void InsertRecommend(Recommend recommend);
    Recommend SearchRecommend(int userId, int bclass1);
}
