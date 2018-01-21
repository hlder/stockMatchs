package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountMapper {
    @Select("SELECT * FROM lhy_sotck_match.user_info_account where user_id=#{userId} and match_id=#{matchId}")
    List<AccountInfo> queryAccountByUserId(@Param("userId") String userId, @Param("matchId") String matchId);

}
