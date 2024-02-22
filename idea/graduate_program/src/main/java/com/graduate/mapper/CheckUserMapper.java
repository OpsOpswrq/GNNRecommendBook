package com.graduate.mapper;

import cn.hutool.json.JSONObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface CheckUserMapper {
    @Insert("insert into checkuser(users,description,date) values (#{users},#{description},#{date});")
    void InsertUsers(@Param("users") String users, @Param("description") String description, @Param("date") Date date);
    @Select("select * from checkuser where date >= #{begin} and date <= #{end};")
    List<JSONObject> SearchCheckUserList(@Param("begin") Date begin,@Param("end") Date end);
    @Select("select * from checkuser;")
    List<JSONObject> SearchCheckUserAll();
    @Select("select * from checkuser where id = #{id};")
    JSONObject SearchCheckUser(@Param("id") int id);
}
