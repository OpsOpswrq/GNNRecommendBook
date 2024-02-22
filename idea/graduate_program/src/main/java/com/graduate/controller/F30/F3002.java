package com.graduate.controller.F30;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.graduate.bean.CommonResult;
import com.graduate.mapper.CheckUserMapper;
import com.graduate.mapper.CommentMapper;
import com.graduate.mapper.HistoryMapper;
import com.graduate.mapper.UserMapper;
import com.graduate.service.UserService;
import com.graduate.utils.VHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class F3002 {
    @Autowired
    UserService userService;
    @Autowired
    CheckUserMapper checkUserMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    HistoryMapper historyMapper;
    /**
     * 进入管理员管理人员界面
     * @return
     */
    @GetMapping("/F3002")
    public String Index(){
        VHelper vHelper = null;
        try{
            vHelper = new VHelper("T/F30/F3002/index.html");
            log.info("/F3002 执行,进入用户管理界面");
        }catch (Exception e){
            log.error("/F3002 执行错误,错误信息:{}",e.getMessage());
        }
        return vHelper.render();
    }

    /**
     * 搜索人员
     * @param userDetail
     * @param currentPage
     * @param pageSize
     * @return
     */
    @PostMapping("/F3002/ajax1")
    public String ajax1(@RequestParam("userDetail") String userDetail,@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize){
        CommonResult result = new CommonResult();
        try{
            result = userService.SearchUserF3002(userDetail,currentPage,pageSize);
            log.info("/F3002/ajax1 执行,提醒信息:{}","搜索用户信息");
        }catch (Exception e){
            log.error("/F3002/ajax1 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 插入用户名单
     * @param users
     * @param description
     * @return
     */
    @PostMapping("/F3002/ajax2")
    public String ajax2(@RequestParam("users") String users,@RequestParam("description") String description){
        CommonResult result = new CommonResult();
        try{
            checkUserMapper.InsertUsers(users,description, DateUtil.date());
            result.setCode(200);
            result.setMsg("200");
            log.info("/F3002/ajax2 执行,提醒信息:{}","插入人员名单");
        }catch (Exception e){
            log.error("/F3002/ajax2 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 搜查名单
     * @param date
     * @return
     */
    @PostMapping("/F3002/ajax3")
    public String ajax3(@RequestParam("date") String date){
        CommonResult result = new CommonResult();
        try{
            List<JSONObject> jsonObjects = new ArrayList<>();
            if(date.equals("1")){
                jsonObjects = checkUserMapper.SearchCheckUserAll();
            }else{
                JSONArray objects = JSONUtil.parseArray(date);
                String begin = objects.get(0).toString();
                String end = objects.get(1).toString();
                Date beginDate = DateUtil.parse(begin);
                Date endDate = DateUtil.parse(end);
                jsonObjects = checkUserMapper.SearchCheckUserList(beginDate, endDate);
            }
            result.setData(jsonObjects);
            result.setCode(200);
            result.setMsg("200");
            log.info("/F3002/ajax3 执行,提醒信息:{}","获取入库人员名单");
        }catch (Exception e){
            log.error("/F3002/ajax3 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }

    /**
     * 打印内容
     * @param id
     * @return
     */
    @GetMapping("/F3002/print/{id}")
    public String doPrint(@PathVariable("id") int id){
        VHelper vh = null;
        try{
            vh = new VHelper("T/F30/F3002/print0.html");
            JSONObject entries = checkUserMapper.SearchCheckUser(id);
            String users = entries.get("users").toString();
            JSONArray users_array = JSONUtil.parseArray(users);
            Date date = DateUtil.parse(DateUtil.now(), "yyyy-MM-dd HH:mm:ss");
            vh.setVar("users",users_array);
            vh.setVar("time",date);
            log.info("/F3002/print 执行,提醒信息:{}","打印名单");
        }catch (Exception e){
            log.error("/F3002/print 执行错误,错误信息:{}",e.getMessage());
        }
        return vh.render();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @PostMapping("/F3002/ajax4")
    public String ajax4(@RequestParam("ID") Integer id){
        CommonResult result = new CommonResult();
        try{
//            System.out.println(id);
            historyMapper.DeleteBookByUserId(id);
            userMapper.DeleteUser(id);
            commentMapper.DeleteCommentByUserId(id);
            result.setCode(200);
            result.setMsg("200");
            log.info("/F3002/ajax4 执行,提醒信息:{}","删除用户");
        }catch (Exception e){
            log.error("/F3002/ajax4 执行错误,错误信息:{}",e.getMessage());
        }
        return JSONUtil.toJsonStr(result);
    }
}
