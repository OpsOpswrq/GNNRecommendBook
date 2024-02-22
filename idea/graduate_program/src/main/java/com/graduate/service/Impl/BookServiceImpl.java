package com.graduate.service.Impl;

import cn.hutool.json.JSONObject;
import com.graduate.bean.Book;
import com.graduate.bean.CommonResult;
import com.graduate.bean.History;
import com.graduate.bean.Recommend;
import com.graduate.mapper.BookMapper;
import com.graduate.service.BookService;
import com.graduate.service.HistoryService;
import com.graduate.service.RecommendService;
import com.graduate.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;
    @Autowired
    RecommendService recommendService;
    @Autowired
    HistoryService historyService;
    @Override
    public CommonResult LoadBookByC1ANDC2NOID(int BClass1, int BClass2,int pageSize,int currentPage) {
        CommonResult result = new CommonResult();
        int begin = (currentPage - 1) * pageSize;
        JSONObject data = new JSONObject();
        int cnt = bookMapper.CountBookByC1ANDC2(BClass1, BClass2);
        data.set("total",cnt);
        List<Book> books = bookMapper.SelectBookByC1ANDC2(BClass1, BClass2, pageSize, begin);
        data.set("books",books);
        data.set("Sequence1",new int[]{1,2,3,4,5,6});
        data.set("Sequence2",new int[]{1,2,3,4,5});
        result.setCode(200);
        result.setMsg("200");
        result.setData(data);
        return result;
    }

    @Override
    public CommonResult LoadBookByC1ANDC2NOIDANDUserId(int userId, int BClass1, int BClass2, int pageSize, int currentPage) {
        CommonResult result = new CommonResult();
        int begin = (currentPage - 1) * pageSize;
        JSONObject data = new JSONObject();
        int cnt = bookMapper.CountBookByC1ANDC2(BClass1, BClass2);
        data.set("total",cnt);
        List<Book> books = bookMapper.SelectBookByC1ANDC2(BClass1, BClass2, pageSize, begin);
        data.set("books",books);
        Recommend recommend = recommendService.SearchRecommend(userId, BClass1);
        if(recommend == null){
            data.set("Sequence1",new int[]{1,2,3,4,5,6});
            data.set("Sequence2",new int[]{1,2,3,4,5});
        }else{
            String s1 = recommend.getSequence1();
            String[] newS1 = s1.substring(s1.indexOf("{") + 1, s1.lastIndexOf("}")).split(",");
            String s2 = recommend.getSequence2();
            String[] newS2 = s2.substring(s2.indexOf("{") + 1, s2.lastIndexOf("}")).split(",");
            data.set("Sequence1",newS1);
            data.set("Sequence2",newS2);
        }
        result.setCode(200);
        result.setMsg("200");
        result.setData(data);
        return result;
    }

    @Override
    public CommonResult LoadBookById(int bookId) {
        CommonResult result = new CommonResult();
        Book book = bookMapper.SelectBookById(bookId);
        result.setCode(200);
        result.setMsg("200");
        result.setData(book);
        return result;
    }

    @Override
    public CommonResult LoadBook1(String search,int begin, int pageSize) {
        CommonResult result = new CommonResult();
        List<Book> books = bookMapper.SelectBook1(search,begin, pageSize);
        int cnt = bookMapper.CountBook1(search);
        JSONObject entries = new JSONObject();
        entries.set("total",cnt);
        entries.set("books",books);
        result.setCode(200);
        result.setMsg("200");
        result.setData(entries);
        return result;
    }

    @Override
    public CommonResult LoadBook2(int BClass1, String search, int begin, int pageSize) {
        CommonResult result = new CommonResult();
        List<Book> books = bookMapper.SelectBook2(BClass1,search,begin, pageSize);
        int cnt = bookMapper.CountBook2(BClass1,search);
        JSONObject entries = new JSONObject();
        entries.set("total",cnt);
        entries.set("books",books);
        result.setCode(200);
        result.setMsg("200");
        result.setData(entries);
        return result;
    }

    @Override
    public CommonResult LoadBook3(int BClass1, int BClass2, String search, int begin, int pageSize) {
        CommonResult result = new CommonResult();
        List<Book> books = bookMapper.SelectBook3(BClass1,BClass2,search,begin, pageSize);
        int cnt = bookMapper.CountBook3(BClass1,BClass2,search);
        JSONObject entries = new JSONObject();
        entries.set("total",cnt);
        entries.set("books",books);
        result.setCode(200);
        result.setMsg("200");
        result.setData(entries);
        return result;
    }

    @Override
    public CommonResult LoadBook4(int userId, int BClass1, int BClass2,String search, int begin, int pageSize) {
        CommonResult result = new CommonResult();
        List<Book> books = bookMapper.SelectBook3(BClass1,BClass2,search,begin, pageSize);
        int cnt = bookMapper.CountBook3(BClass1,BClass2,search);
        History history = new History();
        history.setUserId(userId);
        history.setBclass1(BClass1);
        history.setBclass2(BClass2);
        historyService.AddHistory(history);
        JSONObject entries = new JSONObject();
        entries.set("total",cnt);
        entries.set("books",books);
        result.setCode(200);
        result.setMsg("200");
        result.setData(entries);
        return result;
    }

    @Override
    public CommonResult UpdateBookPicture(MultipartFile photo, int ID) throws IOException {
        CommonResult result = new CommonResult();
        String fileName = photo.getOriginalFilename().trim();
        String fileType = fileName.substring(fileName.lastIndexOf(".")).trim();
        String fileUUID = UUID.randomUUID().toString().trim();
        String photoPath = "/T/UserData/";
        File file = new File(Constants.baseDir + photoPath + fileUUID + fileType);
        photo.transferTo(file); // 传图像文件
        bookMapper.UpdateBookPicture(photoPath + fileUUID + fileType,ID);
        result.setCode(200);
        result.setMsg("200");
        return result;
    }

    @Override
    public CommonResult UpdateBook(JSONObject jsonObject) {
        CommonResult result = new CommonResult();
        bookMapper.UpdateBook(jsonObject);
        result.setCode(200);
        result.setMsg("200");
        return result;
    }
}
