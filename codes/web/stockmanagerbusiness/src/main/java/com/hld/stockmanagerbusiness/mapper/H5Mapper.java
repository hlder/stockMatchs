package com.hld.stockmanagerbusiness.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface H5Mapper {
    @Select("SELECT h5_value FROM h5_info where id=#{id}")
    String queryHtmlById(@Param("id") long id);
}
