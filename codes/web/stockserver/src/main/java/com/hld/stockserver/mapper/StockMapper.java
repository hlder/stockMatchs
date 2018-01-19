package com.hld.stockserver.mapper;

import com.hld.stockserver.bean.StockBean;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface StockMapper {
    @Select("select id,stock_name,stock_code,stock_code_str,now_price,last_price,stock_status from stock_info where stock_name like concat(concat('%',#{queryStr}),'%') or stock_code like concat(concat('%',#{queryStr}),'%') limit 0,10")
    List<StockBean> queryStock(@Param("queryStr")String queryStr);

//    @Insert("insert into stock_info(stock_name,stock_code,stock_code_str,now_price,last_price,stock_status,open_price,max_price,min_price,total_value,tun_rate,vol,pe_rate) value (#{0},#{1},#{2},#{3},#{4},#{5},#{6},#{7},#{8},#{9},#{10},#{11},#{12})")
//    void insertOneStock(String stock_name, String stock_code, String stock_code_str, String now_price, String last_price, String stock_status, String open_price, String max_price, String min_price, String total_value, String tun_rate, String vol, String pe_rate);
    @Insert("insert into stock_info(stock_name,stock_code,stock_code_str,now_price,last_price,stock_status,open_price,max_price,min_price,total_value,tun_rate,vol,pe_rate) value (#{stock_name},#{stock_code},#{stock_code_str},#{now_price},#{last_price},#{stock_status},#{open_price},#{max_price},#{min_price},#{total_value},#{tun_rate},#{vol},#{pe_rate})")
    void insertOneStock(@Param("stock_name") String stock_name, @Param("stock_code") String stock_code, @Param("stock_code_str") String stock_code_str, @Param("now_price") String now_price, @Param("last_price") String last_price, @Param("stock_status") String stock_status, @Param("open_price") String open_price, @Param("max_price") String max_price, @Param("min_price") String min_price, @Param("total_value") String total_value, @Param("tun_rate") String tun_rate, @Param("vol") String vol, @Param("pe_rate") String pe_rate);

    @Update("update stock_info set stock_name=#{stock_name},stock_code=#{stock_code},stock_code_str=#{stock_code_str},now_price=#{now_price},last_price=#{last_price},stock_status=#{stock_status},open_price=#{open_price},max_price=#{max_price},min_price=#{min_price},total_value=#{total_value},tun_rate=#{tun_rate},vol=#{vol},pe_rate=#{pe_rate} where stock_code=#{stock_code}")
    void updateOneStock(@Param("stock_name") String stock_name, @Param("stock_code") String stock_code, @Param("stock_code_str") String stock_code_str, @Param("now_price") String now_price, @Param("last_price") String last_price, @Param("stock_status") String stock_status, @Param("open_price") String open_price, @Param("max_price") String max_price, @Param("min_price") String min_price, @Param("total_value") String total_value, @Param("tun_rate") String tun_rate, @Param("vol") String vol, @Param("pe_rate") String pe_rate);

    @Select("select count(1) FROM stock_info where stock_code=#{code};")
    int queryStockCountByCode(@Param("code")String code);


}
