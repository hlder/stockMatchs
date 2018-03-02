package com.hld.stockweb.mapper;

import com.hld.stockweb.bean.UserInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    @Select("select * from match_creator where user_name=#{userName} and pass_word=#{passWord}")
    UserInfoBean login(@Param("userName") String userName,@Param("passWord") String passWord);
}
