package com.graduate.mapper;

import cn.hutool.json.JSONObject;
import com.graduate.bean.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("select a.id,a.name,a.picture,b.id as commentId,b.content,b.rate,b.date from user as a,comment as b where b.bookId = #{bookId} and a.id = b.userId and a.id in (select userId from comment where bookId = #{bookId}) order by date asc limit #{begin},3;")
    List<JSONObject> CollectCommentByBookId(@Param("bookId") int bookId,@Param("begin") int begin);
    @Select("select count(*) from comment where bookId = #{bookId};")
    Integer CountBookComment(@Param("bookId") int bookId);
    @Select("select avg(rate) from comment where bookId = #{bookId};")
    Float AvgBookRate(@Param("bookId") int bookId);
    @Insert("insert into comment(bookId,userId,content,rate,date) values (#{bookId},#{userId},#{content},#{rate},#{date});")
    void InsertComment(Comment comment);
    @Delete("delete from comment where id = #{commentId};")
    void DeleteComment(@Param("commentId") int commentId);
    @Select("select count(*) from comment where userId = #{userId};")
    int CountCommentByUserId(@Param("userId") int userId);
    @Select("select a.id as bId,a.name as bookName,b.* from book as a,comment as b where a.id = b.bookId and b.userId = #{userId} and a.id in (select bookId from comment where userId = #{userId}) order by b.date asc limit #{begin},#{size};")
    List<JSONObject> SearchCommentByUserId(@Param("userId") int userId,@Param("begin") int begin,@Param("size") int size);
    @Select("select a.id as userId,a.name as userName,a.picture as userPicture,b.name as bookName,b.id as bookId,b.bclass1 as bclass1,b.bclass2 as bclass2,c.id as commentId,c.content as content,c.rate as rate,c.date as date from user as a,book as b,comment as c where c.content like #{commentDetail} and  c.userId = a.id and c.bookId = b.id limit #{begin},#{pageSize};")
    List<JSONObject> SearchCommentForF3005(@Param("commentDetail") String commentDetail,@Param("begin") int begin,@Param("pageSize") int pageSize);
    @Select("select count(*) from user as a,book as b,comment as c where c.content like #{commentDetail} and  c.userId = a.id and c.bookId = b.id;")
    int CountCommentForF3005(@Param("commentDetail") String commentDetail);
    @Delete("delete from comment where userId = #{userId};")
    void DeleteCommentByUserId(@Param("userId") int userId);
    @Delete("delete from comment where bookId = #{bookId};")
    void DeleteCommentByBookId(@Param("bookId") int bookId);
}
