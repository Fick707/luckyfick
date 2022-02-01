package com.fick.luckyfick.job;

import com.fick.luckyfick.service.TcbBetHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

/**
 * @program: luckyfick
 * @description: 从双色球官网获取最新的开奖记录
 * @author: figo.song
 * @create: 2020/2/5
 **/
@Slf4j
public class JobTcbFetchHis implements Job {

    @Resource
    private TcbBetHistoryService tcbBetHistoryService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("do tcb fetch his job.");
        try {
            tcbBetHistoryService.mergeTcbBetHistoryFromOfficial();
        } catch (Exception e){
            log.error("merge tcb bet history from official job error.",e);
        }
    }
}
