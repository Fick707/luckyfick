package com.fick.luckyfick.job;

import com.fick.luckyfick.service.MyTcbBetService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

/**
 * @program: luckyfick
 * @description: 检查上一期是否中奖
 * @author: figo.song
 * @create: 2020/2/5
 **/
@Slf4j
public class JobTcbCheck implements Job {

    @Resource
    private MyTcbBetService myTcbBetService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("do tcb check job.");
        try {
            myTcbBetService.checkResult();
        } catch (Exception e){
            log.error("check result job error.",e);
        }
    }
}
