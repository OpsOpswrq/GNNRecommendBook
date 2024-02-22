package com.graduate.controller.F20;

import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.service.BookService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class F2014 {
    @Autowired
    BookService bookService;
    /**
     * 进入搜索界面
     * @return
     */
    @GetMapping("/F2014")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F20/F2014/index.html");
            log.info("/F2014 执行,进入收藏界面");
        }catch (Exception e){
            log.error("/F2014 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }

    /**
     * 获取图书信息1
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/F2014/ajax1")
    public String ajax1(@RequestParam("search") String search,@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize){
        CommonResult result = new CommonResult();
        try{
            int begin = (currentPage - 1) * pageSize;
            search = "%" + search + "%";
            result = bookService.LoadBook1(search,begin,pageSize);
            log.info("/F2014/ajax1 执行,提醒信息:{}","获取图书信息1");
        }catch (Exception e){
            log.error("/F2014/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 获取图书信息2
     * @param BClass1
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/F2014/ajax2")
    public String ajax2(@RequestParam("BClass1") int BClass1,@RequestParam("search") String search,@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize){
        CommonResult result = new CommonResult();
        try{
            int begin = (currentPage - 1) * pageSize;
            search = "%" + search + "%";
            result = bookService.LoadBook2(BClass1,search,begin,pageSize);
            log.info("/F2014/ajax2 执行,提醒信息:{}","获取图书信息2");
        }catch (Exception e){
            log.error("/F2014/ajax2 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 获取图书信息3
     * @param BClass1
     * @param BClass2
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/F2014/ajax3")
    public String ajax3(@RequestParam("BClass1") int BClass1,@RequestParam("BClass2") int BClass2,@RequestParam("search") String search,@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize){
        CommonResult result = new CommonResult();
        try{
            int begin = (currentPage - 1) * pageSize;
            search = "%" + search + "%";
            result = bookService.LoadBook3(BClass1,BClass2,search,begin,pageSize);
            log.info("/F2014/ajax3 执行,提醒信息:{}","获取图书信息3");
        }catch (Exception e){
            log.error("/F2014/ajax3 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 搜索记录加入
     * @param userId
     * @param BClass1
     * @param BClass2
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/F2014/ajax4")
    public String ajax4(@RequestParam("userId") int userId,@RequestParam("BClass1") int BClass1,@RequestParam("BClass2") int BClass2,@RequestParam("search") String search,@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize){
        CommonResult result = new CommonResult();
        try{
            int begin = (currentPage - 1) * pageSize;
            search = "%" + search + "%";
            result = bookService.LoadBook4((userId - 985) / 380,BClass1,BClass2,search,begin,pageSize);
            log.info("/F2014/ajax2 执行,提醒信息:{}","获取图书信息2");
        }catch (Exception e){
            log.error("/F2014/ajax2 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
