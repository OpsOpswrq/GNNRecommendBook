package com.graduate.mapper;

import cn.hutool.json.JSONObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface CheckBookMapper {
    @Insert("insert into checkbook(books,description,date) values (#{books},#{description},#{date});")
    void InsertBooks(@Param("books") String books, @Param("description") String description, @Param("date") Date date);
    @Select("select * from checkbook;")
    List<JSONObject> SearchCheckBookAll();
    @Select("select * from checkbook where date >= #{begin} and date <= #{end};")
    List<JSONObject> SearchCheckBookList(@Param("begin") Date begin,@Param("end") Date end);
    @Select("select * from checkbook where id = #{id};")
    JSONObject SearchCheckBook(@Param("id") int id);
}
