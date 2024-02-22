package com.graduate.mapper;

import com.graduate.bean.Recommend;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface RecommendMapper {
    @Insert("insert into recommend(userId,sequence1,bclass1,sequence2,date) values (#{userId},#{sequence1},#{bclass1},#{sequence2},#{date});")
    void InsertRecommend(Recommend recommend);
    @Select("select * from recommend where userId = #{userId} and bclass1 = #{bclass1} and date >= #{BeforeDate} and date <= #{afterDate};")
    Recommend SearchRecommend(@Param("userId") int userId, @Param("bclass1") int bclass1, @Param("BeforeDate") Date BeforeDate,@Param("afterDate") Date afterDate);
}
