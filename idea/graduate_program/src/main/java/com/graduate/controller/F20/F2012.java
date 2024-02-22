package com.graduate.controller.F20;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.service.CollectionService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class F2012 {
    @Autowired
    CollectionService collectionService;
    /**
     * 进入藏书信息页
     * @return
     */
    @GetMapping("/F2012")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F20/F2012/index.html");
            log.info("/F2012 执行,进入收藏界面");
        }catch (Exception e){
            log.error("/F2012 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }
    @PostMapping("/F2012/ajax1")
    public String ajax1(@RequestParam("userId") int userId,@RequestParam("BClass1") int BClass1,@RequestParam("BClass2") int BClass2,@RequestParam("pageSize") int pageSize,@RequestParam("currentPage") int currentPage){
        CommonResult result = new CommonResult();
        try{
            JSONObject entries = new JSONObject();
            entries.set("userId",(userId - 985) / 380);
            entries.set("bclass1",BClass1);
            entries.set("bclass2",BClass2);
            int begin = (currentPage - 1) * pageSize;
            entries.set("begin",begin);
            entries.set("size",pageSize);
            result = collectionService.SearchCollectionByUserId(entries);
            log.info("/F2012/ajax1 执行,提醒信息:{}","获取藏书信息");
        }catch (Exception e){
            log.error("/F2012/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
