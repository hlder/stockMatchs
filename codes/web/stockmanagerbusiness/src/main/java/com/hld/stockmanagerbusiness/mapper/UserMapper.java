package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    //根据unionid查询userId，如果存在就证明已经有了
    @Select("select id,nicke_name,head_url,wx_union_id,wx_open_id,def_account_id,sex,province,city,privilege from user_info WHERE wx_union_id=#{0}")
    UserInfo queryUserByUnionId(String unionid);

    @Select("select def_account_id from user_info WHERE id=#{userId}")
    String queryDefAccountId(@Param("userId") String userId);

    //添加一条用户信息
    @Insert("insert into user_info(nicke_name,head_url,wx_union_id,wx_open_id,sex,province,city,privilege,register_time)" +
            " values(#{nicke_name},#{head_url},#{wx_union_id},#{wx_open_id},#{sex},#{province},#{city},#{privilege},now())")
    int registerUser(@Param("nicke_name") String nicke_name, @Param("head_url") String head_url, @Param("wx_union_id") String wx_union_id,@Param("wx_open_id") String wx_open_id,@Param("sex") String sex,@Param("province") String province,@Param("city") String city,@Param("privilege") String privilege);

    //更新用户信息
    @Update("update user_info set nicke_name=#{nicke_name},head_url=#{head_url},wx_union_id=#{wx_union_id},wx_open_id=#{wx_open_id},sex=#{sex},province=#{province},city=#{city},privilege=#{privilege},last_login_time=now() where wx_union_id=#{wx_union_id}")
    int updateUserInfo(@Param("nicke_name") String nicke_name, @Param("head_url") String head_url, @Param("wx_union_id") String wx_union_id,@Param("wx_open_id") String wx_open_id,@Param("sex") String sex,@Param("province") String province,@Param("city") String city,@Param("privilege") String privilege);


}
