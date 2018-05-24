package com.hld.stockmanagerbusiness.mapper;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import com.hld.stockmanagerbusiness.bean.MatchInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface MathMapper {

    //根据ID查询比赛信息
//    @Select("select id,match_name,banners,buttons,match_note,max_people_num,now_people_num,creator_user_id,is_need_profession,is_need_stu_class,is_need_stu_num from match_info where id=#{matchId}")
    @Select("select * from match_info where id=#{matchId}")
    MatchInfo queryApplyMatchInfo(@Param("matchId") String matchId);


    //查询所有的比赛
    @Select("select id from match_info")
    List<Long> queryAllMatchId();

    //每次有人新增，加1
    @Update("update match_info set add_user_all=add_user_all+1 where id=#{matchId}")
    void addMatchUserNum(@Param("matchId") String matchId);
}
