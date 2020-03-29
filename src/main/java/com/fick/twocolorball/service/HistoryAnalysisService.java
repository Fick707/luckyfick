package com.fick.twocolorball.service;

import java.util.List;

/**
 * @program: twocolorball
 * @description: 历史分析服务
 * @author: figo.song
 * @create: 2020/3/29
 **/
public interface HistoryAnalysisService {

    /**
     * 按出现次数排序红球
     * @return
     */
    public List<Integer> getTopRed();

    /**
     * 按出现次数排序蓝球
     * @return
     */
    public List<Integer> getTopBlue();

    /**
     * 生成红球Top的趋势图，看看趋势图是否有变化
     */
    public void generateTrendChart();

    /**
     * 生成红球Top的趋势图，看看趋势图是否有变化
     */
    public void generateTrendChart2();


}
