package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountMapper {
    //通过userid和matchid查询账户，是否此人已经报名
    @Select("SELECT * FROM lhy_sotck_match.user_info_account where user_id=#{userId} and match_id=#{matchId}")
    List<AccountInfo> queryAccountByUserId(@Param("userId") String userId, @Param("matchId") String matchId);

    @Insert("insert into user_info_account (user_id,match_id,account_name,phone_num,profession,stu_class,stu_num) values(#{userId},#{matchId},#{name},#{phoneNum},#{profession},#{stuClass},#{stuNum})")
    void insertAccount(@Param("userId") String userId,@Param("matchId") String matchId,@Param("name") String name,@Param("phoneNum") String phoneNum,@Param("profession") String profession,@Param("stuClass") String stuClass,@Param("stuNum") String stuNum);
}
