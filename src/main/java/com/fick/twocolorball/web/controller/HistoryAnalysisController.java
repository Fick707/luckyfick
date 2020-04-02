package com.fick.twocolorball.web.controller;

import com.fick.twocolorball.service.HistoryAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @name: HistoryAnalysisController
 * @program: twocolorball
 * @description:
 * @author: figo.song
 * @created: 2020/4/1
 **/
@Slf4j
@RestController
@RequestMapping("/api/history")
public class HistoryAnalysisController {

    @Autowired
    HistoryAnalysisService historyAnalysisService;

    @RequestMapping(value = "/topRed", method = {RequestMethod.POST,RequestMethod.GET})
    public String topRed(HttpServletRequest request, @RequestParam(value = "step",required = true) Integer step ) throws IOException {
        return historyAnalysisService.generateRedTrendChart(step);
    }

    @RequestMapping(value = "/topBlue", method = {RequestMethod.POST,RequestMethod.GET})
    public String topBlue(HttpServletRequest request, @RequestParam(value = "step",required = true) Integer step ) throws IOException {
        return historyAnalysisService.generateBlueTrendChart(step);
    }
}
