package com.hld.stockmanagerbusiness.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WeChatMapper {

    @Insert("insert into wechat_formIds(user_id,create_date,form_id) values(#{userId},now(),#{formId});")
    void insertFormId(@Param("userId") String userId,@Param("formId") String formId);
}
