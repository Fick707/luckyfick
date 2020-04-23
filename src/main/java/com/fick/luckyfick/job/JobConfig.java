package com.fick.luckyfick.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: luckyfick
 * @description: 定时任务配置
 * @author: figo.song
 * @create: 2020/2/5
 **/
@Configuration
public class JobConfig {

    @Value("${com.fick.luckyfick.quartz.cron.tcb.fetch.his}")
    private String quartzJobCronTcbFetchHis;

    @Value("${com.fick.luckyfick.quartz.cron.tcb.check}")
    private String quartzJobCronTcbCheck;

    @Value("${com.fick.luckyfick.quartz.cron.tcb.bet}")
    private String quartzJobCronTcbBet;

    /**
     * 获取历史
     * @return
     */
    @Bean("tcbFetchHisJobDetail")
    public JobDetail tcbFetchHisJobDetail() {
        return JobBuilder.newJob(JobTcbFetchHis.class).withIdentity("tcbFetchHisJob")
                .storeDurably().build();
    }

    @Bean
    public Trigger jobTcbFetchHisTrigger(JobDetail tcbFetchHisJobDetail) {
        return TriggerBuilder.newTrigger().forJob(tcbFetchHisJobDetail)
                .withIdentity("tcbFetchHisJob")
                .withSchedule(CronScheduleBuilder.cronSchedule(quartzJobCronTcbFetchHis))
                .build();
    }

    @Bean("tcbCheckJobDetail")
    public JobDetail tcbCheckJobDetail() {
        return JobBuilder.newJob(JobTcbCheck.class).withIdentity("tcbCheckJob")
                .storeDurably().build();
    }

    @Bean
    public Trigger jobTcbCheckTrigger(JobDetail tcbCheckJobDetail) {
        return TriggerBuilder.newTrigger().forJob(tcbCheckJobDetail)
                .withIdentity("tcbCheckJob")
                .withSchedule(CronScheduleBuilder.cronSchedule(quartzJobCronTcbCheck))
                .build();
    }

    @Bean("tcbBetJobDetail")
    public JobDetail tcbBetJobDetail() {
        return JobBuilder.newJob(JobTcbBet.class).withIdentity("tcbBetJob")
                .storeDurably().build();
    }

    @Bean
    public Trigger jobTcbBetTrigger(JobDetail tcbBetJobDetail) {
        return TriggerBuilder.newTrigger().forJob(tcbBetJobDetail)
                .withIdentity("tcbBetJob")
                .withSchedule(CronScheduleBuilder.cronSchedule(quartzJobCronTcbBet))
                .build();
    }

}
