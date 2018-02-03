package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.HolderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockInfoMapper {

    @Select("SELECT * FROM user_holder_stock where account_id=#{accountId}")
    List<HolderInfo> queryMyHolderByAccountId(@Param("accountId") String accountId);
}
