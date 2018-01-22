package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.MatchInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface MathMapper {

//    @Select("select id,match_name,banners,buttons,match_note,max_people_num,now_people_num,creator_user_id,is_need_profession,is_need_stu_class,is_need_stu_num from match_info where id=#{matchId}")
    @Select("select * from match_info where id=#{matchId}")
    MatchInfo queryApplyMatchInfo(@Param("matchId") String matchId);

}
