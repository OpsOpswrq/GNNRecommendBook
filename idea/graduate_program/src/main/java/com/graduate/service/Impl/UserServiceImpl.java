package com.graduate.service.Impl;

import cn.hutool.json.JSONObject;
import com.graduate.bean.CommonResult;
import com.graduate.bean.User;
import com.graduate.mapper.UserMapper;
import com.graduate.service.UserService;
import com.graduate.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public int UserLogin(User BUser) {
        User user = userMapper.SearchUser(BUser);
        if(user == null){  // 用户不存在
            return -1;
        }
        if(user.getPassword().equals(BUser.getPassword())){  // 用户密码不匹配
            return user.getId();
        }else{
            return -2;
        }
    }

    @Override
    public int InsertUser(User BUser) {
        User user = userMapper.SearchUser(BUser);
        if (user == null){ // 没有userId重叠的存在
            BUser.setPicture("/T/UserData/default.png");
            userMapper.InsertUser(BUser);
            return BUser.getId();
        }
        else { // 存在userId重叠
            return -1;
        }
    }

    @Override
    public CommonResult LoadUserByIdF2010(User BUser) {
        CommonResult result = new CommonResult();
        User user = userMapper.SearchUserByID(BUser);
        if(user != null) {
            user.setPassword("");
            result.setCode(200);
            result.setMsg("200");
        }else{
            user = new User();
            user.setId(-1);
            result.setCode(404);
            result.setMsg("404");
        }
        result.setData(user);
        return result;
    }
    @Override
    public CommonResult LoadUserByIdF2011(User BUser) {
        CommonResult result = new CommonResult();
        User user = userMapper.SearchUserByID(BUser);
        if(user != null) {
            result.setCode(200);
            result.setMsg("200");
        }else{
            user = new User();
            user.setId(-1);
            result.setCode(404);
            result.setMsg("404");
        }
        result.setData(user);
        return result;
    }

    @Override
    public CommonResult UpdateUserData(User user) {
        CommonResult result = new CommonResult();
        User searchUser = userMapper.SearchUser(user);
        if(searchUser == null || searchUser.getId() == user.getId()){ // 分为改userId 和 不改userId的情况
            userMapper.UpdateUserByID(user);
            result.setCode(200);
            result.setMsg("200");
        }else{
            result.setCode(403); // 用户账号重名
            result.setMsg("403");
        }
        return result;
    }

    @Override
    public CommonResult UpdateUserPicture(MultipartFile photo,int ID) throws IOException {
        CommonResult result = new CommonResult();
        String photoName = photo.getOriginalFilename().trim(); //文件总名字
        String photoType = photoName.substring(photoName.lastIndexOf(".")).trim(); // 文件类型
        String photoUUID = UUID.randomUUID().toString().trim();
        String photoPath = "/T/UserData/";
        File file = new File(Constants.baseDir + photoPath + photoUUID + photoType);
        photo.transferTo(file); // 传图像文件
        userMapper.UpdateUserPictureById(photoPath + photoUUID + photoType,ID);
        result.setCode(200);
        result.setMsg("200");
        return result;
    }

    @Override
    public CommonResult LoadUserByIdF2013(int userId) {
        CommonResult result = new CommonResult();
        JSONObject entries = userMapper.SearchUserF2013(userId);
        result.setCode(200);
        result.setMsg("200");
        result.setData(entries);
        return result;
    }

    @Override
    public int[] CollectId() {
        return userMapper.CollectId();
    }

    @Override
    public CommonResult SearchUserF3002(String userDetail, int currentPage, int pageSize) {
        userDetail = "%" + userDetail + "%";
        int begin = (currentPage - 1) * pageSize;
        List<JSONObject> jsonObjects = userMapper.SearchUserF3002(userDetail, begin, pageSize);
        int cnt = userMapper.CountUserF3002(userDetail);
        JSONObject entries = new JSONObject();
        entries.set("users",jsonObjects);
        entries.set("total",cnt);
        CommonResult result = new CommonResult();
        result.setCode(200);
        result.setMsg("200");
        result.setData(entries);
        return result;
    }
}
