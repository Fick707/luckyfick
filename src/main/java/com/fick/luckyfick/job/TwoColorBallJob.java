package com.fick.luckyfick.job;

import com.fick.luckyfick.service.HistoryAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/3/29
 **/
@Component
@Slf4j
public class TwoColorBallJob {

    @Autowired
    HistoryAnalysisService historyAnalysisService;

    @PostConstruct
    public void run(){
        log.info("run two color ball job ...");

        log.info("run two color ball job done.");
    }
}
