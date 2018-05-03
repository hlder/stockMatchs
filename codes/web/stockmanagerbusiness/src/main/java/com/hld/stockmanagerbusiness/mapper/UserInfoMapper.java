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


    @Select("select ua.*,ui.head_url head_url from user_info ui RIGHT JOIN (" +
            "select * from user_info_account where match_id=#{matchId} order by total_assets desc limit #{page},#{pageSize}" +
            ") ua on ui.id=ua.user_id")
    List<Map<String,String>> queryMatchUsers(@Param("matchId") String matchId,@Param("page") String page,@Param("pageSize") String pageSize);
}
