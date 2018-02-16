package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.HolderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockInfoMapper {

    @Select("SELECT * FROM user_holder_stock where account_id=#{accountId}")
    List<HolderInfo> queryMyHolderByAccountId(@Param("accountId") String accountId);

    @Select("SELECT * FROM lhy_sotck_match.user_holder_stock where account_id=#{accountId} and stock_code_str='#{stockCodeStr}'")
    List<HolderInfo> queryMyHolderWithStock(@Param("accountId") String accountId,@Param("stockCodeStr") String stockCodeStr);


//    @Select("select id,stock_name,stock_code,stock_code_str,now_price,last_price,stock_status from stock_info where
// search_str like concat(concat('%',#{queryStr}),'%') limit 0,10")

    @Select("select stock_name,stock_code,stock_code_str from stock_info where search_str like concat(concat('%',#{searchStr}),'%') limit 0,10")
    List<Map<String,Object>> queryStockFuzzy(@Param("searchStr") String searchStr);
}
