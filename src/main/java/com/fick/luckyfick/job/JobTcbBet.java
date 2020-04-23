package com.fick.luckyfick.job;

import com.fick.luckyfick.service.MyTcbBetService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

/**
 * @program: luckyfick
 * @description: 投注下一期
 * @author: figo.song
 * @create: 2020/2/5
 **/
@Slf4j
public class JobTcbBet implements Job {

    @Resource
    MyTcbBetService myTcbBetService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("do tcb bet job.");
        myTcbBetService.generateMyBet();
    }
}
