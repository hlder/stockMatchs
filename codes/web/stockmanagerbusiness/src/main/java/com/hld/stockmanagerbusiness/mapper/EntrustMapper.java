package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.EntrustStockInfo;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfoHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EntrustMapper {

    @Select("insert into user_entrust_stock(stock_code,stock_code_str,stock_name,entrust_price,type,entrust_num,account_id) " +
            "values(#{stockCode},#{stockCodeStr},#{stockName},#{entrustPrice},#{type},#{entrustNum},#{accountId})")
    void buyOrSellStock(@Param("accountId") String accountId,@Param("stockCode") String stockCode,@Param("stockCodeStr") String stockCodeStr,@Param("stockName") String stockName,
                 @Param("entrustPrice") String entrustPrice,@Param("type") String type,@Param("entrustNum") String entrustNum);//购买股票


    @Select("SELECT * FROM user_entrust_stock")
    List<EntrustStockInfo> queryAllEntrust();

    @Select("SELECT * FROM user_entrust_stock where account_id=#{accountId} order by id desc")
    List<EntrustStockInfo> queryMyEntrustById(@Param("accountId") String accountId);

    @Select("SELECT * FROM user_entrust_stock where id=#{id}")
    EntrustStockInfo queryMyEntrustOneById(@Param("id") String id);

//    @Select("SELECT * FROM user_entrust_stock_his where account_id=#{accountId} and entrust_time>='2018-02-03 16:43:26' and entrust_time<='2018-02-03 16:43:26' limit #{page},20")
    @Select("SELECT * FROM user_entrust_stock_his where account_id=#{accountId} and entrust_time>=#{startDate} and entrust_time<=#{endDate} order by id desc limit #{page},20")
    List<EntrustStockInfoHistory> queryMyEntrustHistoryById(@Param("accountId") String accountId, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("page") int page);

    //删除委托
    @Delete("delete from user_entrust_stock where id=#{id}")
    int deleteEntrust(@Param("id") String id);

    //添加委托记录
    @Insert("insert into user_entrust_stock_his(stock_code,stock_code_str,stock_name,entrust_price,type,entrust_num,vol_num,entrust_time,user_id,account_id,vol_type,vol_price) values " +
            "(#{bean.stock_code},#{bean.stock_code_str},#{bean.stock_name},#{bean.entrust_price},#{bean.type},#{bean.entrust_num}," +
            "#{bean.vol_num},#{bean.entrust_time},#{bean.user_id},#{bean.account_id},#{vol_type},#{bean.vol_price});")
    void insertEntrustHistory(@Param("bean") EntrustStockInfo bean,@Param("vol_type") String vol_type);

}