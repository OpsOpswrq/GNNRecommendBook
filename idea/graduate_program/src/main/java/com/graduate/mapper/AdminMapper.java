package com.graduate.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("select count(*) from admin where userId = #{id};")
    int CountAdmin(@Param("id") int id);
}
