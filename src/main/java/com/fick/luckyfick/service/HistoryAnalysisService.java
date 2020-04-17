package com.fick.luckyfick.service;

import com.fick.luckyfick.model.BallCount;
import com.fick.luckyfick.model.BallCountTrend;
import com.fick.luckyfick.model.BallMissCountTrend;

import java.util.List;

/**
 * @program: luckyfick
 * @description: 历史分析服务
 * @author: figo.song
 * @create: 2020/3/29
 **/
public interface HistoryAnalysisService {

    /**
     * 根据历史所有开奖结果，按出现次数排序红球
     * @return
     */
    List<Integer> getTopRed();

    /**
     * 根据历史所有开奖结果，按出现次数排序蓝球
     * @return
     */
    List<Integer> getTopBlue();

    /**
     * 根据指定历史期数步长，生成红球Top的趋势图，看看趋势图是否有变化
     * @param step
     * @return
     */
    BallCountTrend getRedBallCountTrend(int step);

    /**
     * 根据指定历史期数步长，生成蓝球Top的趋势图，看看趋势图是否有变化
     * @param step
     * @return
     */
    BallCountTrend getBlueBallCountTrend(int step);

    /**
     * 根据最近N次开奖结果，获取各个红色球号的出现次数
     * @param last
     * @return
     */
    List<BallCount> getTopRedInLast(int last);

    /**
     * 根据最近N次开奖结果，获取各个蓝色球号的出现次数
     * @param last
     * @return
     */
    List<BallCount> getTopBlueInLast(int last);

    /**
     * 根据最近N次开奖结果，获取指定红色球号的出现次数
     * @param last
     * @param ballNumber
     * @return
     */
    Integer getRedBallCountInLast(int last,Integer ballNumber);

    /**
     * 根据最近N次开奖结果，获取指定蓝色球号的出现次数
     * @param last
     * @param ballNumber
     * @return
     */
    Integer getBlueBallCountInLast(int last,Integer ballNumber);

    /**
     * 获取指定红色球号最近多少次未出现
     * @param ballNumber
     * @return
     */
    Integer getRedBallMissCountInLast(Integer ballNumber);

    /**
     * 获取指定蓝色球号最近多少次未出现
     * @param ballNumber
     * @return
     */
    Integer getBlueBallMissCountInLast(Integer ballNumber);

    /**
     * 获取红色球号最近缺失的次数
     * @return
     */
    List<BallCount> getRedBallMissCounts();

    /**
     * 获取蓝色球号最近缺失的次数
     * @return
     */
    List<BallCount> getBlueBallMissCounts();

    /**
     * 获取开奖历史中，红色球号最大的连续出现次数
     * @return
     */
    List<BallCount> getRedBallHisMaxAppearCounts();

    /**
     * 获取开奖历史中，蓝色球号最大的连续出现次数
     * @return
     */
    List<BallCount> getBlueBallHisMaxAppearCounts();

    /**
     * 获取开奖历史中，红色球号最大的缺失次数
     * @return
     */
    List<BallCount> getRedBallHisMaxMissCounts();

    /**
     * 获取开奖历史中，蓝色球号最大的缺失次数
     * @return
     */
    List<BallCount> getBlueBallHisMaxMissCounts();

    /**
     * 获取开奖历史中，红色球号最大的缺失次数
     * @return
     */
    BallMissCountTrend getRedBallHisMissCounts();

    /**
     * 获取开奖历史中，蓝色球号最大的缺失次数
     * @return
     */
    BallMissCountTrend getBlueBallHisMissCounts();
}
