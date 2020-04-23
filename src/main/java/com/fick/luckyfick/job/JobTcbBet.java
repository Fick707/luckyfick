package com.fick.luckyfick.job;

import com.fick.luckyfick.service.TcbBetHistoryService;
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
    TcbBetHistoryService tcbBetHistoryService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("do tcb bet job.");
//        GinaLogUtils.info(log,"clear expired car.");
//        try {
//            carService.clearExpiredCarsSchedule();
//        } catch (Exception e){
//            GinaLogUtils.error(log,"clear expired job execute error.",e);
//        }
//        GinaLogUtils.info(log,"clear expired car done.");
    }
}
