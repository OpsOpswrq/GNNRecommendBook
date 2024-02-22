package com.graduate.controller.F20;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.graduate.bean.Collection;
import com.graduate.bean.Comment;
import com.graduate.bean.CommonResult;
import com.graduate.service.BookService;
import com.graduate.service.CollectionService;
import com.graduate.service.CommentService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图书界面
 */
@RestController
@Slf4j
public class F2000 {
    @Autowired
    BookService bookService;
    @Autowired
    CommentService commentService;
    @Autowired
    CollectionService collectionService;
    /**
     * 进入图书界面
     * @return
     */
    @GetMapping("/F2000")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F20/F2000/index.html");
            log.info("/F2000 执行,进入主界面");
        }catch (Exception e){
            log.error("/F2000 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }

    /**
     * 有判断ID是否已经登录，这是未登录的
     * @param BClass1
     * @param BClass2
     * @param pageSize
     * @param currentPage
     * @return
     */
    @PostMapping("/F2000/ajax1")
    public String ajax1(@RequestParam("BClass1") int BClass1,@RequestParam("BClass2") int BClass2,@RequestParam("pageSize") int pageSize,@RequestParam("currentPage") int currentPage){
        CommonResult result = new CommonResult();
        try{
            result = bookService.LoadBookByC1ANDC2NOID(BClass1, BClass2, pageSize, currentPage);
            log.info("/F2000/ajax1 执行,提醒信息:{}","获取图书信息");
        }catch (Exception e){
            log.error("/F2000/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 获取评论详情 以及是否收藏和评分平均 未登录时
     * @param bookId
     * @param currentPage
     * @return
     */
    @PostMapping("/F2000/ajax2")
    public String ajax2(@RequestParam("bookId") int bookId,@RequestParam("currentPage") int currentPage){ // bookId
        CommonResult result = new CommonResult();
        try{
            result = commentService.CollectCommentByBookId(bookId,currentPage);
            log.info("/F2000/ajax2 执行,提醒信息:{}","获取评论详情");
        }catch (Exception e){
            log.error("/F2000/ajax2 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 登录时，获取评论以及是否已经收藏了
     * @param userId
     * @param bookId
     * @param currentPage
     * @return
     */
    @PostMapping("/F2000/ajax3")
    public String ajax3(@RequestParam("userId") int userId,@RequestParam("bookId") int bookId,@RequestParam("currentPage") int currentPage){ // bookId
        CommonResult result = new CommonResult();
        try{
            result = commentService.CollectCommentByBookIdANDUserId((userId - 985) / 380,bookId,currentPage);
            log.info("/F2000/ajax3 执行,提醒信息:{}","获取评论详情");
        }catch (Exception e){
            log.error("/F2000/ajax3 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 插入评论
     * @param content
     * @param rate
     * @param userId
     * @param bookId
     * @return
     */
    @PostMapping("/F2000/ajax4")
    public String ajax4(@RequestParam("content") String content,@RequestParam("rate") int rate,@RequestParam("userId") int userId,@RequestParam("bookId") int bookId){
        CommonResult result = new CommonResult();
        try{
            Comment comment = new Comment();
            comment.setBookId(bookId);
            comment.setUserId((userId - 985) / 380);
            comment.setContent(content);
            comment.setRate(rate);
            comment.setDate(DateUtil.date());
            result = commentService.InsertComment(comment);
            log.info("/F2000/ajax4 执行,提醒信息:{}","提交评论");
        }catch (Exception e){
            log.error("/F2000/ajax4 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @PostMapping("/F2000/ajax5")
    public String ajax5(@RequestParam("commentId") int commentId){
        CommonResult result = new CommonResult();
        try{
            result = commentService.DeleteComment(commentId);
            log.info("/F2000/ajax5 执行,提醒信息:{}","删除评论");
        }catch (Exception e){
            log.error("/F2000/ajax5 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 进入收藏书
     * @param userId
     * @param bookId
     * @return
     */
    @PostMapping("/F2000/ajax6")
    public String ajax6(@RequestParam("userId") int userId,@RequestParam("bookId") int bookId){
        CommonResult result = new CommonResult();
        try{
            Collection collection = new Collection();
            collection.setBookId(bookId);
            collection.setUserId((userId - 985) / 380);
            collection.setDate(DateUtil.date());
            result = collectionService.AddCollection(collection);
            log.info("/F2000/ajax6 执行,提醒信息:{}","收藏书籍");
        }catch (Exception e){
            log.error("/F2000/ajax6 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 取消藏书
     * @param userId
     * @param bookId
     * @return
     */
    @PostMapping("/F2000/ajax7")
    public String ajax7(@RequestParam("userId") int userId,@RequestParam("bookId") int bookId){
        CommonResult result = new CommonResult();
        try{
            Collection collection = new Collection();
            collection.setBookId(bookId);
            collection.setUserId((userId - 985) / 380);
            result = collectionService.CancelCollection(collection);
            log.info("/F2000/ajax7 执行,提醒信息:{}","取消收藏书籍");
        }catch (Exception e){
            log.error("/F2000/ajax7 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 获取推荐序列
     * @param userId
     * @param BClass1
     * @param BClass2
     * @param pageSize
     * @param currentPage
     * @return
     */
    @PostMapping("/F2000/ajax8")
    public String ajax8(@RequestParam("userId") int userId,@RequestParam("BClass1") int BClass1,@RequestParam("BClass2") int BClass2,@RequestParam("pageSize") int pageSize,@RequestParam("currentPage") int currentPage){
        CommonResult result = new CommonResult();
        try{
            result = bookService.LoadBookByC1ANDC2NOIDANDUserId((userId - 985) / 380,BClass1, BClass2, pageSize, currentPage);
            log.info("/F2000/ajax8 执行,提醒信息:{}","获取图书信息");
        }catch (Exception e){
            log.error("/F2000/ajax8 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
