package com.hld.stockmanagerbusiness.scheduled;

import com.hld.stockmanagerbusiness.mapper.SMSMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledOther {
    @Autowired
    SMSMapper smsMapper;


    @Scheduled(cron="0 2 0 * * ?")
    public void scranAuthSMS(){
        smsMapper.deleteOverSMS();
    }
}
