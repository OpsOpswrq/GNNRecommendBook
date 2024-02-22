package com.graduate.controller.F20;

import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.service.BookService;
import com.graduate.service.CommentService;
import com.graduate.service.UserService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class F2013 {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    /**
     * 进入F2013界面 评论管理界面
     * @return
     */
    @GetMapping("/F2013")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F20/F2013/index.html");
            log.info("/F2013 执行,进入收藏界面");
        }catch (Exception e){
            log.error("/F2013 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }

    /**
     * 获取评论详情
     * @param userId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/F2013/ajax1")
    public String ajax1(@RequestParam("userId") int userId,@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize){
        CommonResult result = new CommonResult();
        try{
            userId = (userId - 985) / 380;
            int begin = (currentPage - 1) * pageSize;
            result = commentService.LoadCommentByUserId(userId,begin,pageSize);
            log.info("/F2013/ajax1 执行,提醒信息:{}","获取评论信息");
        }catch (Exception e){
            log.error("/F2013/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @PostMapping("/F2013/ajax2")
    public String ajax2(@RequestParam("userId") int userId){
        CommonResult result = new CommonResult();
        try{
            result = userService.LoadUserByIdF2013((userId - 985) / 380);
            log.info("/F2013/ajax2 执行,提醒信息:{}","获取用户信息");
        }catch (Exception e){
            log.error("/F2013/ajax2 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 删除评论
     * @param ID
     * @return
     */
    @PostMapping("/F2013/ajax3")
    public String ajax3(@RequestParam("ID") int ID){
        CommonResult result = new CommonResult();
        try{
            result = commentService.DeleteComment(ID);
            log.info("/F2013/ajax3 执行,提醒信息:{}","删除评论");
        }catch (Exception e){
            log.error("/F2013/ajax3 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 获取图书信息
     * @param bookId
     * @return
     */
    @PostMapping("/F2013/ajax4")
    public String ajax4(@RequestParam("bookId") int bookId){
        CommonResult result = new CommonResult();
        try{
            result = bookService.LoadBookById(bookId);
            log.info("/F2013/ajax4 执行,提醒信息:{}","获取图书信息");
        }catch (Exception e){
            log.error("/F2013/ajax4 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
