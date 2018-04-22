package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserInfoMapper {


    @Select("select * from user_info where id=#{userId}")
    UserInfo queryUserInfoByUserId(@Param("userId") String userId);


    @Select("select (total_assets-${initTotal})/${initTotal} income,create_time from user_info_account_his where account_id=#{accountId}")
    List<Map<String,String>> queryIncomeArr(@Param("accountId") String accountId, @Param("initTotal") String initTotal);
}
