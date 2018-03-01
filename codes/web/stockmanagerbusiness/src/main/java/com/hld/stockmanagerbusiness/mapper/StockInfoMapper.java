package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.HolderInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockInfoMapper {

    @Select("select DISTINCT stock_code_str from user_holder_stock")
    List<String> queryAllHolderStock();

    @Update("update user_holder_stock set usable_num=holder_num")
    void updateHolderCanUse();

    @Update("update user_holder_stock set now_price=#{nowPrice} where stock_code_str=#{stockCodeStr}")
    void updateHolderNowPrice(@Param("stockCodeStr") String stockCodeStr,@Param("nowPrice") String nowPrice);

    @Select("SELECT * FROM user_holder_stock where account_id=#{accountId}")
    List<HolderInfo> queryMyHolderByAccountId(@Param("accountId") String accountId);

    @Select("SELECT * FROM lhy_sotck_match.user_holder_stock where account_id=#{accountId} and stock_code_str=#{stockCodeStr}")
    List<HolderInfo> queryMyHolderWithStock(@Param("accountId") String accountId,@Param("stockCodeStr") String stockCodeStr);


    @Update("update user_holder_stock set holder_num=#{holderNum},cost_price=#{costPrice} where id=#{id}")
    void updateHolder(@Param("holderNum") String holderNum,@Param("costPrice") String costPrice,@Param("id") String id);

    @Delete("delete from user_holder_stock where id=#{id}")
    void deleteHolder(@Param("id") String id);
    @Insert("insert into user_holder_stock (user_id,account_id,stock_code,stock_code_str,stock_name,cost_price,now_price,holder_num,usable_num) " +
            "values(#{bean.user_id},#{bean.account_id},#{bean.stock_code},#{bean.stock_code_str},#{bean.stock_name},#{bean.cost_price},#{bean.now_price},#{bean.holder_num},#{bean.usable_num})")
    void addHolder(@Param("bean") HolderInfo bean);
//    @Select("select id,stock_name,stock_code,stock_code_str,now_price,last_price,stock_status from stock_info where
// search_str like concat(concat('%',#{queryStr}),'%') limit 0,10")

    @Select("select stock_name,stock_code,stock_code_str,now_price from stock_info where search_str like concat(concat('%',#{searchStr}),'%') limit 0,10")
    List<Map<String,Object>> queryStockFuzzy(@Param("searchStr") String searchStr);


    //查询持仓总市值
    @Select("SELECT sum(now_price*holder_num) FROM user_holder_stock where account_id=#{accountId}")
    String queryUserAllValue(@Param("accountId") String accountId);



    //查询出所有的账号ID
    @Select("select id from user_info_account")
    List<Long> queryAllAccountId();
}
