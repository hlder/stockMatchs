package com.hld.stockmanagerbusiness.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface MineMapper {
    @Select("select id,nicke_name,head_url,def_account_id from user_info where id=#{userId}")
    Map<String,Object> queryMineInfo(@Param("userId") String userId);

//    @Select("select * from match_info where id in (select match_id from user_info_account where user_id =#{userId}) and status=1")
    @Select("select mi.logo logo,mi.id match_id,mi.match_name match_name,mi.start_date start_date,mi.end_date end_date," +
            "ua.id account_id,ua.total_income total_income,ua.total_income_rate total_income_rate " +
            "from match_info mi INNER join user_info_account ua on mi.id=ua.match_id and ua.user_id=#{userId} and mi.status=1")
    List<Map<String,Object>> queryJoinMatchs(@Param("userId") String userId);

//    @Select("select * from match_info where id in (select match_id from user_info_account where user_id =#{userId}) and status=2  order by end_date desc limit 0,20")
    @Select("select mi.logo logo,mi.id match_id,mi.match_name match_name,mi.start_date start_date,mi.end_date end_date," +
            "ua.id account_id,ua.total_income total_income,ua.total_income_rate total_income_rate " +
            "from match_info mi INNER join user_info_account ua on mi.id=ua.match_id and ua.user_id=#{userId} and mi.status=2 order by mi.end_date desc limit 0,20")
    List<Map<String,Object>> queryHisMatchs(@Param("userId") String userId);

    @Update("update user_info set def_account_id=#{accountId} where id =#{userId}")
    void checkMyMatch(@Param("userId") String userId,@Param("accountId") String accountId);
}
