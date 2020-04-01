package com.fick.twocolorball.job;

import com.alibaba.fastjson.JSON;
import com.fick.twocolorball.service.HistoryAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @program: twocolorball
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
        // 先获取红球top
//        List<Integer> topReds = historyAnalysisService.getTopRed();
//        log.info("get top red {}.", JSON.toJSON(topReds));
        // 获取蓝球top
//        List<Integer> topBlues = historyAnalysisService.getTopBlue();
//        log.info("get top blue {}.",JSON.toJSON(topBlues));

        // top red 变化趋势图
//        historyAnalysisService.generateRedTrendChart();

        // top blue 变化趋势图
//        historyAnalysisService.generateBlueTrendChart();
        log.info("run two color ball job done.");
    }
}
