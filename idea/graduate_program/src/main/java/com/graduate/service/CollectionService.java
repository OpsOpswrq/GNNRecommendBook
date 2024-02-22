package com.graduate.service;

import cn.hutool.json.JSONObject;
import com.graduate.bean.Collection;
import com.graduate.bean.CommonResult;
import org.springframework.stereotype.Service;

@Service
public interface CollectionService {
    CommonResult AddCollection(Collection collection);
    CommonResult CancelCollection(Collection collection);
    CommonResult SearchCollectionByUserId(JSONObject jsonObject);
}
