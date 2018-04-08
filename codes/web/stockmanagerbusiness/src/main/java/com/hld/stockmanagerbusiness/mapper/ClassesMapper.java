package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.ClassInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassesMapper {

    @Select("select id,text_title from class_info where mold=#{mold}")
    List<ClassInfo> queryClassListByMold(@Param("mold") String mold);

    @Select("select * from class_info where id=#{id}")
    ClassInfo queryClassInfoById(@Param("id") String id);
}
