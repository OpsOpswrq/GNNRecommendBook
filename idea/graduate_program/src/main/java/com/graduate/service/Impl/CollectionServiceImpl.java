package com.graduate.service.Impl;

import cn.hutool.json.JSONObject;
import com.graduate.bean.Collection;
import com.graduate.bean.CommonResult;
import com.graduate.mapper.CollectionMapper;
import com.graduate.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionMapper collectionMapper;
    @Override
    public CommonResult AddCollection(Collection collection) {
        CommonResult result = new CommonResult();
        collectionMapper.InsertCollection(collection);
        result.setCode(200);
        result.setMsg("200");
        return result;
    }

    @Override
    public CommonResult CancelCollection(Collection collection) {
        CommonResult result = new CommonResult();
        collectionMapper.DeleteCollection(collection);
        result.setCode(200);
        result.setMsg("200");
        return result;
    }

    @Override
    public CommonResult SearchCollectionByUserId(JSONObject jsonObject) {
        CommonResult result = new CommonResult();
        List<JSONObject> jsonObjects = collectionMapper.SearchCollectionByUserId(jsonObject);
        int cnt = collectionMapper.CountCollection(jsonObject);
        JSONObject entries = new JSONObject();
        entries.set("books",jsonObjects);
        entries.set("total",cnt);
        result.setCode(200);
        result.setMsg("200");
        result.setData(entries);
        return result;
    }
}
