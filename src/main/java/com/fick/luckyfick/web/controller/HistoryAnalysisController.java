package com.fick.luckyfick.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fick.luckyfick.model.BallCount;
import com.fick.luckyfick.model.BallCountTrend;
import com.fick.luckyfick.model.BallMissCountTrend;
import com.fick.luckyfick.model.WebResult;
import com.fick.luckyfick.service.HistoryAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @name: HistoryAnalysisController
 * @program: luckyfick
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

    /**
     * 根据指定历史步长，获取红球出现频次趋势图数据
     * @param request
     * @param step
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/redBallCountTrend", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<JSONArray> redBallCountTrend(HttpServletRequest request, @RequestParam(value = "step") Integer step ) throws IOException {
        return WebResult.success(generateAppearTrendData(historyAnalysisService.getRedBallCountTrend(step)));
    }

    /**
     * 根据指定历史步长，获取蓝球出现频次趋势图数据
     * @param request
     * @param step
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/blueBallCountTrend", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<JSONArray> blueBallCountTrend(HttpServletRequest request, @RequestParam(value = "step") Integer step ) throws IOException {
        return WebResult.success(generateAppearTrendData(historyAnalysisService.getBlueBallCountTrend(step)));
    }

    /**
     * 根据历史记录，获取红球缺失次数趋势图数据
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/redBallHisMissCountTrend", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<JSONArray> redBallHisMissCountTrend() throws IOException {
        return WebResult.success(generateAbsenceTrendData(historyAnalysisService.getRedBallHisMissCounts()));
    }

    /**
     * 根据历史记录，获取蓝球缺失次数趋势图数据
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/blueBallHisMissCountTrend", method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<JSONArray> blueBallHisMissCountTrend() throws IOException {
        return WebResult.success(generateAbsenceTrendData(historyAnalysisService.getBlueBallHisMissCounts()));
    }

    /**
     * 生成最近last次开奖结果中，各红球出现次数
     * @param request
     * @param last
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/redBallCountInLastBar",method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<JSONArray> redBallCountInLastBar(HttpServletRequest request, @RequestParam(value = "last") Integer last){
        return WebResult.success(generateBallCount(historyAnalysisService.getTopRedInLast(last)));
    }

    /**
     * 生成最近last次开奖结果中，各蓝球出现次数
     * @param request
     * @param last
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blueBallCountInLastBar",method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<JSONArray> blueBallCountInLastBar(HttpServletRequest request, @RequestParam(value = "last") Integer last){
        return WebResult.success(generateBallCount(historyAnalysisService.getTopBlueInLast(last)));
    }

    /**
     * 生成最近开奖结果中，各红球缺失次数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/redBallMissCountBar",method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<JSONArray> redBallMissCountBar(){
        return WebResult.success(generateBallCount(historyAnalysisService.getRedBallMissCounts()));
    }

    /**
     * 生成最近开奖结果中，各蓝球缺失次数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blueBallMissCountBar",method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<JSONArray> blueBallMissCountBar(){
        return WebResult.success(generateBallCount(historyAnalysisService.getBlueBallMissCounts()));
    }

    /**
     * 生成历史开奖结果中，各红球最大缺失次数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/redBallMaxMissCountBar",method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<JSONArray> redBallMaxMissCountBar(){
        return WebResult.success(generateBallCount(historyAnalysisService.getRedBallHisMaxMissCounts()));
    }

    /**
     * 生成最近开奖结果中，各蓝球缺失次数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blueBallMaxMissCountBar",method = {RequestMethod.POST,RequestMethod.GET})
    public WebResult<JSONArray> blueBallMaxMissCountBar(){
        return WebResult.success(generateBallCount(historyAnalysisService.getBlueBallHisMaxMissCounts()));
    }

    /**
     * 生成次数柱状图数据
     * @param ballCounts
     * @return
     */
    private JSONArray generateBallCount(List<BallCount> ballCounts){
        JSONArray jsonArray = new JSONArray();
        for(BallCount ballCount : ballCounts){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("x",ballCount.getBallNumber()+"");
            jsonObject.put("y",ballCount.getCount());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 拼装成球号，出现次数柱状图
     * 可以放到该页面展示：
     * https://www.echartsjs.com/examples/zh/editor.html?c=bar-simple
     * @param ballCounts
     * @return
     */
    private String generateBallCountBar(List<BallCount> ballCounts,String title){
        List<String> xData = ballCounts.stream().map(item -> item.getBallNumber()+"").collect(Collectors.toList());
        List<Integer> yData = ballCounts.stream().map(BallCount::getCount).collect(Collectors.toList());
        JSONObject option = new JSONObject();
        option.put("title",new JSONObject().fluentPut("text",title));
        option.put("tooltip",new JSONObject().fluentPut("trigger","axis"));
        option.put("xAxis", new JSONObject().fluentPut("type", "category").fluentPut("data",xData));
        option.put("yAxis", new JSONObject().fluentPut("type","value"));
        option.put("series", new JSONArray().fluentAdd(new JSONObject().fluentPut("type","bar").fluentPut("data",yData)));
        String result = option.toString(SerializerFeature.UseSingleQuotes, SerializerFeature.PrettyFormat);
        result = result.replaceAll("'(\\w+)'(\\s*:\\s*)", "$1$2");
        return result;
    }

    /**
     * 根据历史统计结果，拼装出趋势图所需要的数据
     * @param ballCountTrend
     * @return
     */
    private JSONArray generateAppearTrendData(BallCountTrend ballCountTrend){
        JSONArray jsonArray = new JSONArray();
        List<Integer> counts = ballCountTrend.getCounts();
        for(Integer key : ballCountTrend.getBallNumberCountsMap().keySet()){
            List<Integer> ballCounts = ballCountTrend.getBallNumberCountsMap().get(key);
            for(int i = 0 ; i < ballCounts.size() ; i ++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("key", key + "");
                jsonObject.put("x", counts.get(i));
                jsonObject.put("y",ballCounts.get(i));
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 根据历史统计结果，拼装出趋势图所需要的数据
     * @param ballMissCountTrend
     * @return
     */
    private JSONArray generateAbsenceTrendData(BallMissCountTrend ballMissCountTrend){
        JSONArray jsonArray = new JSONArray();
        int maxSize = 0;
        for(List<Integer> ballCounts : ballMissCountTrend.getBallNumberMissCountsMap().values()){
            if(ballCounts.size() > maxSize){
                maxSize = ballCounts.size();
            }
        }
        List<Integer> counts = new ArrayList<>();
        for(int i = 1 ; i <= maxSize ; i ++){
            counts.add(i);
        }
        for(Integer key : ballMissCountTrend.getBallNumberMissCountsMap().keySet()){
            List<Integer> ballCounts = ballMissCountTrend.getBallNumberMissCountsMap().get(key);
            for(int i = 0 ; i < maxSize ; i ++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("key", key + "");
                jsonObject.put("x", counts.get(i));
                jsonObject.put("y",ballCounts.size()<(i + 1) ? 0: ballCounts.get(i));
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }
}
