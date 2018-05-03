package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountMapper {
    //通过userid和matchid查询账户，是否此人已经报名
    @Select("SELECT * FROM user_info_account where user_id=#{userId} and match_id=#{matchId}")
    List<AccountInfo> queryAccountByUserId(@Param("userId") String userId, @Param("matchId") String matchId);

//    @Select("select * from user_info_account where id in (${ids})")
    @Select("select ua.*,ui.head_url head_url from user_info ui RIGHT JOIN (select * from user_info_account where id in (${ids})) ua on ui.id=ua.user_id")
    List<Map<String,String>> queryAccountInIds(@Param("ids") String ids);


    @Select("select ua.*,ui.head_url head_url from user_info ui RIGHT JOIN (select * from user_info_account where id=#{accountId}) ua on ui.id=ua.user_id")
    Map<String,String> queryLeaderInfoByAccountId(@Param("accountId") String accountId);


    @Select("select leader from user_info_account where id=#{accountId}")
    String queryMyLeaders(@Param("accountId") String accountId);

    @Select("select * from user_info_account where id=#{id}")
    AccountInfo queryAccountById(@Param("id") String id);

    @Insert("insert into user_info_account (user_id,match_id,account_name,phone_num,profession,stu_class,stu_num,total_assets,init_total_assets,can_use_assets,create_time) values(#{userId},#{matchId},#{name},#{phoneNum},#{profession},#{stuClass},#{stuNum},#{total_assets},#{init_total_assets},#{can_use_assets},now())")
    void insertAccount(@Param("userId") String userId,@Param("matchId") String matchId,@Param("name") String name,@Param("phoneNum") String phoneNum,@Param("profession") String profession,@Param("stuClass") String stuClass,@Param("stuNum") String stuNum,@Param("total_assets") String total_assets,@Param("init_total_assets") String init_total_assets,@Param("can_use_assets") String can_use_assets);

    //修改账户的剩余的钱
    @Update("update user_info_account set can_use_assets=#{canUserAssets} where id=#{accountId}")
    int chanageCanUseMoney(@Param("accountId") String accountId,@Param("canUserAssets") String canUserAssets);


    @Update("update user_info set def_account_id=#{accountId} where id=#{userId}")
    void updateDefAccount(@Param("userId") String userId,@Param("accountId") String accountId);

    //将所有的账户信息都存到历史表中,每天一存
    @Insert("insert into user_info_account_his (account_id,user_id,total_assets,can_use_assets,deal_count) select id,user_id,total_assets,can_use_assets,deal_count from user_info_account")
    void addAccountToHis();

    @Select("SELECT total_assets FROM user_info_account_his where TO_DAYS(date_sub(curdate(),interval 1 day))=TO_DAYS(create_time) and account_id=#{accountId}")
    String queryYestodayTotalAssets(@Param("accountId") String accountId);

    @Select("SELECT total_assets FROM user_info_account_his where TO_DAYS(date_sub(curdate(),interval #{days} day))=TO_DAYS(create_time) and account_id=#{accountId}")
    String queryHisTotalAssets(@Param("accountId") String accountId,@Param("days") String days);

    //更新用户的总资产
    @Update("update user_info_account set total_assets=#{totalAssets} where id=#{id}")
    void updateTotalAssets(@Param("totalAssets") String totalAssets,@Param("id") String id);


    @Update("update user_info_account set total_assets=#{totalAssets},total_income=#{total_income},total_income_rate=#{total_income_rate},week_income=#{week_income},week_income_rate=#{week_income_rate},month_income=#{month_income},month_income_rate=#{month_income_rate} where id=#{id}")
    void updateAccountIncome(@Param("id") String id,@Param("totalAssets") String totalAssets,@Param("total_income") String total_income,@Param("total_income_rate") String total_income_rate,@Param("week_income") String week_income,@Param("week_income_rate") String week_income_rate,@Param("month_income") String month_income,@Param("month_income_rate") String month_income_rate);


    //更新所有人的交易数量
    @Update("update user_info_account ua set ua.deal_count=(select count(1) from user_entrust_stock_his where account_id=ua.id and vol_type=1)")
    void updateAllDealNum();

    @Select("select count(*) from user_entrust_stock_his where account_id=#{accountId} and and vol_type=1 and DATEDIFF(now(),vol_time)<=#{days}")
    int queryVolCount(@Param("accountId") String accountId,@Param("days") int days);
    @Select("select count(*) from user_entrust_stock_his where account_id=#{accountId} and and vol_type=1")
    int queryVolAllCount(@Param("accountId") String accountId);

    @Update("update user_info_account set week_vol_count=${week_vol_count},month_vol_count=${month_vol_count},total_vol_count=${total_vol_count}")
    void updateAccountVolCount(@Param("week_vol_count") String week_vol_count,@Param("month_vol_count") String month_vol_count,@Param("total_vol_count") String total_vol_count);


    @Select("select id from user_info_account where match_id=#{matchId} order by total_assets desc")
    List<Integer> queryRanking(@Param("matchId") String matchId);

    @Update("update user_info_account set rank=#{rank} where id=#{accountId}")
    void updateUserRanking(@Param("accountId") String accountId,@Param("rank") String rank);
}
