package com.hld.stockmanagerbusiness.scheduled;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledUpdateUserInfo {
    private Logger logger = Logger.getLogger(ScheduledUpdateUserInfo.class);


    //每日凌晨1:10分进行更新所有人的信息
    @Scheduled(cron="0 10 01 ? * *")
    public void updateAllUserInfo() {
        //更新我的收益，收益率(验算一遍)

    }



}
