package com.graduate.controller.F30;

import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.service.CommentService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class F3005 {
    @Autowired
    CommentService commentService;
    /**
     * 进入评论管理界面
     * @return
     */
    @GetMapping("/F3005")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F30/F3005/index.html");
            log.info("/F3005 执行,进入评论管理界面");
        }catch (Exception e){
            log.error("/F3005 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }
    @PostMapping("/F3005/ajax1")
    public String ajax1(@RequestParam("commentDetail") String commentDetail,@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize){
        CommonResult result = new CommonResult();
        try{
            result = commentService.SearchCommentForF3005(commentDetail, currentPage, pageSize);
            log.info("/F3005/ajax1 执行,提醒信息:{}","获取评论信息");
        }catch (Exception e){
            log.error("/F3005/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
