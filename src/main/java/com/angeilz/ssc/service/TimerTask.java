package com.angeilz.ssc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhanglei
 * @version 2018/6/25
 */
@Component
public class TimerTask {

    @Autowired
    StageInfoService service;
    @Scheduled(cron = "0/15 * * * * ?")
    public void autoInsert() {
    }
}
