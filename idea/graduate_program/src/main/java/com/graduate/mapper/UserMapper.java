package com.graduate.mapper;

import cn.hutool.json.JSONObject;
import com.graduate.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select id from user;")
    int[] CollectId();
    @Select("select * from user where userId = #{userId};")
    User SearchUser(User user);
    @Insert("insert into user(userId,name,password) values(#{userId},#{name},#{password});")  // insert 加入数据段,返回自增id
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void InsertUser(User user);
    @Select("select * from user where id = #{Id};")
    User SearchUserByID(User user);
    @Update("update user set userId = #{userId},name = #{name},age = #{age},sex = #{sex},picture = #{picture},email = #{email},password = #{password} where id = #{Id};")
    void UpdateUserByID(User user);
    @Update("update user set picture = #{picture} where id = #{Id};")
    void UpdateUserPictureById(@Param("picture") String picture,@Param("Id") int Id);
    @Select("select name,picture from user where id = #{userId};")
    JSONObject SearchUserF2013(@Param("userId") int userId);
    @Select("select id,userId,name,age,sex,email,picture from user where userId like #{userDetail} or name like #{userDetail} or email like #{userDetail} limit #{begin},#{pageSize};")
    List<JSONObject> SearchUserF3002(@Param("userDetail") String userDetail,@Param("begin") int begin,@Param("pageSize") int pageSize);
    @Select("select count(*) from user where userId like #{userDetail} or name like #{userDetail} or email like #{userDetail};")
    int CountUserF3002(@Param("userDetail") String userDetail);
    @Delete("delete from user where id = #{id};")
    void DeleteUser(@Param("id") int id);
    @Update("update user set address = #{address} where id = #{id};")
    void UpdateAddress(@Param("address") String address,@Param("id") int id);
}
