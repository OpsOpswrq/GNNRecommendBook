package com.graduate.mapper;

import cn.hutool.json.JSONObject;
import com.graduate.bean.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select count(*) from book where bclass1 = #{bclass1} and bclass2 = #{bclass2};")
    Integer CountBookByC1ANDC2(@Param("bclass1") int BClass1,@Param("bclass2") int BClass2);
    @Select("select * from book where bclass1 = #{bclass1} and bclass2 = #{bclass2} limit #{begin},#{pageSize};")
    List<Book> SelectBookByC1ANDC2(@Param("bclass1") int BClass1,@Param("bclass2") int BClass2,@Param("pageSize") int pageSize,@Param("begin") int begin);
    @Select("select * from book where id = #{bookId};")
    Book SelectBookById(@Param("bookId") int bookId);
    @Select("select * from book where name like #{search} limit #{begin},#{pageSize};")
    List<Book> SelectBook1(@Param("search") String search,@Param("begin") int begin,@Param("pageSize") int pageSize);
    @Select("select count(*) from book where name like #{search};")
    int CountBook1(@Param("search") String search);
    @Select("select * from book where bclass1 = #{bclass1} and name like #{search} limit #{begin},#{pageSize};")
    List<Book> SelectBook2(@Param("bclass1") int bclass1,@Param("search") String search,@Param("begin") int begin,@Param("pageSize") int pageSize);
    @Select("select count(*) from book where bclass1 = #{bclass1} and name like #{search};")
    int CountBook2(@Param("bclass1") int bclass1,@Param("search") String search);
    @Select("select * from book where bclass1 = #{bclass1} and bclass2 = #{bclass2} and name like #{search} limit #{begin},#{pageSize};")
    List<Book> SelectBook3(@Param("bclass1") int bclass1,@Param("bclass2") int bclass2,@Param("search") String search,@Param("begin") int begin,@Param("pageSize") int pageSize);
    @Select("select count(*) from book where bclass1 = #{bclass1} and bclass2 = #{bclass2} and name like #{search};")
    int CountBook3(@Param("bclass1") int bclass1,@Param("bclass2") int bclass2,@Param("search") String search);
    @Update("update book set picture_URL = #{picture} where id = #{id};")
    void UpdateBookPicture(@Param("picture") String picture,@Param("id") int id);
    @Update("update book set name = #{name},price = #{price},author = #{author},bclass1 = #{bclass1},bclass2 = #{bclass2},rate = #{rate},picture_URL = #{picture_URL},detail = #{detail},`left` = #{left} where id = #{Id};")
    void UpdateBook(JSONObject jsonObject);
    @Delete("delete from book where id = #{id};")
    void DeleteBook(@Param("id") int id);
    @Select("select a.* from book as a,(select count(*) as cnt,bookId from history where userId = #{userId} group by bookId order by cnt desc) as b where a.id not in (select bookId from collection where userId = #{userId}) and a.bclass1 = #{bclass1_2} and a.id = b.bookId limit #{begin},#{pageSize};")
    List<JSONObject> SearchBookANDGETONE(@Param("userId") int userId,@Param("bclass1_2") int bclass1_2,@Param("begin") int begin,@Param("pageSize") int pageSize);
    @Select("select a.* from book as a,(select count(*) as cnt,bookId from history where userId = #{userId} group by bookId order by cnt desc) as b where a.id not in (select bookId from collection where userId = #{userId}) and a.bclass1 = #{bclass1_2} and a.id = b.bookId limit #{begin},#{pageSize};")
    List<JSONObject> SearchBookANDGETONEHALF(@Param("userId") int userId,@Param("bclass1_2") int bclass1_2,@Param("begin") int begin,@Param("pageSize") int pageSize);
}
