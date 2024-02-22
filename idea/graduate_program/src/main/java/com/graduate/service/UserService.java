package com.graduate.service;

import com.graduate.bean.CommonResult;
import com.graduate.bean.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UserService {
    int UserLogin(User user);
    int InsertUser(User user);
    CommonResult LoadUserByIdF2010(User user);
    CommonResult LoadUserByIdF2011(User user);
    CommonResult UpdateUserData(User user);
    CommonResult UpdateUserPicture(MultipartFile photo,int ID) throws IOException;
    CommonResult LoadUserByIdF2013(int userId);
    int[] CollectId();
    CommonResult SearchUserF3002(String userDetail,int currentPage,int pageSize);
}
