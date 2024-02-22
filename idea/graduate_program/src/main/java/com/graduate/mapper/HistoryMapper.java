package com.graduate.mapper;

import cn.hutool.json.JSONObject;
import com.graduate.bean.History;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface HistoryMapper {
    @Select("select count(*) as cnt,userId from history where bclass1 = #{bclass1} and userId = #{userId} and date >= #{beforeDate} and date <= #{nowDate};")
    JSONObject CountByBClass1(@Param("bclass1") int bclass1,@Param("userId") int userId,@Param("beforeDate") Date beforeDate,@Param("nowDate") Date nowDate);
    @Select("select count(*) as cnt,userId from history where bclass1 = #{bclass1} and bclass2 = #{bclass2} and userId = #{userId} and date >= #{beforeDate} and date <= #{nowDate};")
    JSONObject CountByBClass1ANDBClass2(@Param("bclass1") int bclass1,@Param("bclass2") int bclass2,@Param("userId") int userId,@Param("beforeDate") Date beforeDate,@Param("nowDate") Date nowDate);
    @Insert("insert into history(userId,bclass1,bclass2,date) values (#{userId},#{bclass1},#{bclass2},#{date});")
    void InsertHistory(History history);
    @Delete("delete from history where bookId = #{bookId};")
    void DeleteBookByBookId(@Param("bookId") int bookId);
    @Delete("delete from history where userId = #{userId};")
    void DeleteBookByUserId(@Param("userId") int userId);
    @Select("select count(*) as cnt,bclass1 from history group by bclass1 order by cnt desc;")
    List<JSONObject> CountHotClass1();
    @Select("select count(*) as cnt,bclass2 from history where bclass1 = #{bclass1} group by bclass2 order by cnt desc;")
    List<JSONObject> CountHotClass2(@Param("bclass1") int bclass1);
    @Select("select count(*) as cnt from history where date >= #{begin} and date <= #{end};")
    int CountHistoryByDay(@Param("begin") Date begin,@Param("end") Date end);
    @Select("select count(*) as cnt,bclass1 from history where userId = #{userId} and date >= #{begin} and date <= #{end} group by bclass1 order by cnt desc;")
    List<JSONObject> CountHistoryUser(@Param("userId") int userId,@Param("begin") Date begin,@Param("end") Date end);
    @Select("select count(*) as cnt,bclass1 from history where userId = #{userId} and date >= #{begin} and date <= #{end} group by bclass1 order by cnt desc limit 0,2;")
    List<JSONObject> CountHistoryUserGETTWO(@Param("userId") int userId,@Param("begin") Date begin,@Param("end") Date end);
    @Select("select a.`name`,b.cnt from book as a,(select count(*) as cnt,bookId from history where bookId is not null and date >= #{begin} and date <= #{end} group by bookId order by cnt desc limit 0,10) as b where a.id = b.bookId;")
    List<JSONObject> CountHistoryHOT(@Param("begin") Date begin,@Param("end") Date end);
}
