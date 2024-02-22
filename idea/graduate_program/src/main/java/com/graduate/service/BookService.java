package com.graduate.service;

import cn.hutool.json.JSONObject;
import com.graduate.bean.CommonResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface BookService {
    CommonResult LoadBookByC1ANDC2NOID(int BClass1,int BClass2,int pageSize,int currentPage);
    CommonResult LoadBookByC1ANDC2NOIDANDUserId(int userId,int BClass1,int BClass2,int pageSize,int currentPage);
    CommonResult LoadBookById(int bookId);
    CommonResult LoadBook1(String search,int begin,int pageSize);
    CommonResult LoadBook2(int BClass1,String search,int begin,int pageSize);
    CommonResult LoadBook3(int BClass1,int BClass2,String search,int begin,int pageSize);
    CommonResult LoadBook4(int userId,int BClass1,int BClass2,String search,int begin,int pageSize);
    CommonResult UpdateBookPicture(MultipartFile file,int ID) throws IOException;
    CommonResult UpdateBook(JSONObject jsonObject);
}
