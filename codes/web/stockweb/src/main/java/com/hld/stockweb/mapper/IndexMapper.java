package com.hld.stockweb.mapper;

import com.hld.stockweb.bean.UserShopHisBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface IndexMapper {

    @Select("select income_yestoday,income_all,add_user_yestoday,add_user_all from match_info where id=#{matchId}")
    Map<String,Object> queryMatchIncomeInfo(@Param("matchId") String matchId);

//    @Select("select * from user_shop_his where match_id=#{matchId} order by id desc limit 0,20")
    @Select("select ui.nicke_name,his.* from user_info ui RIGHT JOIN (select * from user_shop_his where match_id=#{matchId} order by id desc limit 0,20) his on his.user_id=ui.id")
    List<UserShopHisBean> queryMathcShopList(@Param("matchId") String matchId);


}
