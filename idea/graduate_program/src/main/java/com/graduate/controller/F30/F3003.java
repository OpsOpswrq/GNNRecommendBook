package com.graduate.controller.F30;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.mapper.*;
import com.graduate.service.BookService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class F3003 {
    @Autowired
    BookService bookService;
    @Autowired
    CheckBookMapper checkBookMapper;
    @Autowired
    CollectionMapper collectionMapper;
    @Autowired
    HistoryMapper historyMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    BookMapper bookMapper;
    /**
     * 进入图书管理界面
     * @return
     */
    @GetMapping("/F3003")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F30/F3003/index.html");
            log.info("/F3003 执行,进入图书管理界面");
        }catch (Exception e){
            log.error("/F3003 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }

    /**
     * loadBook1
     * @param bookDetail
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/F3003/ajax1")
    public String ajax1(@RequestParam("bookDetail") String bookDetail,@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize){
        CommonResult result = new CommonResult();
        try{
            int begin = (currentPage - 1) * pageSize;
            bookDetail = "%" + bookDetail + "%";
           result = bookService.LoadBook1(bookDetail,begin,pageSize);
           log.info("/F3003/ajax1 执行,提醒信息:{}","加载书籍信息");
        }catch (Exception e){
            log.error("/F3003/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * loadBook2
     * @param BClass1
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/F3003/ajax2")
    public String ajax2(@RequestParam("BClass1") int BClass1,@RequestParam("search") String search,@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize){
        CommonResult result = new CommonResult();
        try{
            int begin = (currentPage - 1) * pageSize;
            search = "%" + search + "%";
            result = bookService.LoadBook2(BClass1,search,begin,pageSize);
            log.info("/F3003/ajax2 执行,提醒信息:{}","获取图书信息2");
        }catch (Exception e){
            log.error("/F3003/ajax2 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * loadBook3
     * @param BClass1
     * @param BClass2
     * @param search
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/F3003/ajax3")
    public String ajax3(@RequestParam("BClass1") int BClass1,@RequestParam("BClass2") int BClass2,@RequestParam("search") String search,@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize){
        CommonResult result = new CommonResult();
        try{
            int begin = (currentPage - 1) * pageSize;
            search = "%" + search + "%";
            result = bookService.LoadBook3(BClass1,BClass2,search,begin,pageSize);
            log.info("/F3003/ajax3 执行,提醒信息:{}","获取图书信息3");
        }catch (Exception e){
            log.error("/F3003/ajax3 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 修改图片信息
     * @param bookId
     * @param photo
     * @return
     */
    @PostMapping("/F3003/ajax4")
    public String ajax4(@RequestParam("ID") int bookId, @RequestParam("file")MultipartFile photo){  // important
        CommonResult result = new CommonResult();
        try{
            result = bookService.UpdateBookPicture(photo,bookId);
            log.info("/F3003/ajax4 执行,提醒信息:{}","修改图片");
        }catch (Exception e){
            log.error("/F3003/ajax4 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 修改图片信息
     * @param book
     * @return
     */
    @PostMapping("/F3003/ajax5")
    public String ajax5(@RequestParam("book") String book){
        CommonResult result = new CommonResult();
        try{
            JSONObject entries = JSONUtil.parseObj(book);
            result = bookService.UpdateBook(entries);
            log.info("/F3003/ajax5 执行,提醒信息:{}","修改图书信息");
        }catch (Exception e){
            log.error("/F3003/ajax5 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 插入书籍名单
     * @param books
     * @param description
     * @return
     */
    @PostMapping("/F3003/ajax6")
    public String ajax6(@RequestParam("books") String books,@RequestParam("description") String description){
        CommonResult result = new CommonResult();
        try{
            checkBookMapper.InsertBooks(books,description, DateUtil.date());
            result.setCode(200);
            result.setMsg("200");
            log.info("/F3003/ajax2 执行,提醒信息:{}","插入书籍名单");
        }catch (Exception e){
            log.error("/F3003/ajax6 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 按照时间间隔获取书籍名单
     * @param date
     * @return
     */
    @PostMapping("/F3003/ajax7")
    public String ajax7(@RequestParam("date") String date){
        CommonResult result = new CommonResult();
        try{
            List<JSONObject> jsonObjects = new ArrayList<>();
            if(date.equals("1")){
                jsonObjects = checkBookMapper.SearchCheckBookAll();
            }else{
                JSONArray objects = JSONUtil.parseArray(date);
                String begin = objects.get(0).toString();
                String end = objects.get(1).toString();
                Date beginDate = DateUtil.parse(begin);
                Date endDate = DateUtil.parse(end);
                jsonObjects = checkBookMapper.SearchCheckBookList(beginDate,endDate);
            }
            result.setData(jsonObjects);
            result.setCode(200);
            result.setMsg("200");
            log.info("/F3003/ajax7 执行,提醒信息:{}","获取书籍名单");
        }catch (Exception e){
            log.error("/F3003/ajax7 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 打印名单
     * @param id
     * @return
     */
    @GetMapping("/F3003/print/{id}")
    public String doPrint(@PathVariable("id") int id){
        VHelper vh = null;
        try{
            vh = new VHelper("T/F30/F3003/print0.html");
            JSONObject entries = checkBookMapper.SearchCheckBook(id);
            String books = entries.get("books").toString();
            JSONArray books_array = JSONUtil.parseArray(books);
            Date date = DateUtil.parse(DateUtil.now(), "yyyy-MM-dd HH:mm:ss");
            vh.setVar("books",books_array);
            vh.setVar("time",date);
            vh.setVar("BClass1",new String[]{"","文学", "流行", "文化", "生活", "经管", "科技"});
            vh.setVar("BClass2",new String[][]{
                    {"","","","","",""},
                    {"","小说","文学","外国文学","经典","中国文学"},
                    {"","漫画","推理","绘本","悬疑","科幻"},
                    {"","历史","心理学","哲学","社会学","传记"},
                    {"","生活","爱情","成长","心理","旅行"},
                    {"","经济学","管理","商业","金融","投资"},
                    {"","科普","互联网","科学","编程","算法"}});
            log.info("/F3003/print 执行,提醒信息:{}","打印名单");
        }catch (Exception e){
            log.error("/F3003/print 执行错误,错误信息:{}",e.getMessage());
        }
        return vh.render();
    }
    @PostMapping("/F3003/ajax8")
    public String ajax8(@RequestParam("ID") Integer id){
        CommonResult result = new CommonResult();
        try{
//            System.out.println(id);
            historyMapper.DeleteBookByBookId(id);
            collectionMapper.DeleteCollectionByBookId(id);
            bookMapper.DeleteBook(id);
            commentMapper.DeleteCommentByBookId(id);
            result.setCode(200);
            result.setMsg("200");
            log.info("/F3003/ajax8 执行,提醒信息:{}","删除书籍");
        }catch (Exception e){
            log.error("/F3003/ajax8 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
