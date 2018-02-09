package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {
    //通过userid和matchid查询账户，是否此人已经报名
    @Select("SELECT * FROM user_info_account where user_id=#{userId} and match_id=#{matchId}")
    List<AccountInfo> queryAccountByUserId(@Param("userId") String userId, @Param("matchId") String matchId);

    @Select("select * from user_info_account where id=#{id}")
    AccountInfo queryAccountById(@Param("id") String id);

    @Insert("insert into user_info_account (user_id,match_id,account_name,phone_num,profession,stu_class,stu_num,total_assets,init_total_assets) values(#{userId},#{matchId},#{name},#{phoneNum},#{profession},#{stuClass},#{stuNum},#{total_assets},#{init_total_assets})")
    void insertAccount(@Param("userId") String userId,@Param("matchId") String matchId,@Param("name") String name,@Param("phoneNum") String phoneNum,@Param("profession") String profession,@Param("stuClass") String stuClass,@Param("stuNum") String stuNum,@Param("total_assets") String total_assets,@Param("init_total_assets") String init_total_assets);

    //修改账户的剩余的钱
    @Update("update user_info_account set can_use_assets=#{canUserAssets} where id=#{accountId}")
    int chanageCanUseMoney(@Param("accountId") String accountId,@Param("canUserAssets") String canUserAssets);


}
