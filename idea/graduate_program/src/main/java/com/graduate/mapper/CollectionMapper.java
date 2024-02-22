package com.graduate.mapper;

import cn.hutool.json.JSONObject;
import com.graduate.bean.Collection;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollectionMapper {
    @Select("select date,count(*) as cnt from collection where userId = #{userId} and bookId = #{bookId};")
    JSONObject SearchCollectionByUserIdANDBookId(@Param("userId") int userId,@Param("bookId") int bookId);
    @Insert("insert into collection(bookId,userId,date) values (#{bookId},#{userId},#{date});")
    void InsertCollection(Collection collection);
    @Delete("delete from collection where userId = #{userId} and bookId = #{bookId};")
    void DeleteCollection(Collection collection);
    @Select("select a.*,b.date from book as a,collection as b " +
            "where userId = #{userId} and " +
            "a.id = b.bookId and " +
            "a.bclass1 = #{bclass1} and a.bclass2 = #{bclass2} and " +
            "b.bookId in (select bookId from collection where userId = #{userId}) order by b.date asc limit #{begin},#{size};")
    List<JSONObject> SearchCollectionByUserId(JSONObject jsonObject);
    @Select("select count(*) from book as a,collection as b " +
            "where userId = #{userId} and " +
            "a.id = b.bookId and " +
            "a.bclass1 = #{bclass1} and a.bclass2 = #{bclass2} and " +
            "b.bookId in (select bookId from collection where userId = #{userId});")
    int CountCollection(JSONObject jsonObject);
    @Delete("delete from collection where bookId = #{bookId};")
    void DeleteCollectionByBookId(@Param("bookId") int bookId);
}
