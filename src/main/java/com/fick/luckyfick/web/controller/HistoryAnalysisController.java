package com.fick.luckyfick.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fick.luckyfick.model.BallCount;
import com.fick.luckyfick.model.BallCountTrend;
import com.fick.luckyfick.model.BallMissCountTrend;
import com.fick.luckyfick.service.HistoryAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public String redBallCountTrend(HttpServletRequest request, @RequestParam(value = "step") Integer step ) throws IOException {
        return generateTrend(historyAnalysisService.getRedBallCountTrend(step));
    }

    /**
     * 根据指定历史步长，获取蓝球出现频次趋势图数据
     * @param request
     * @param step
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/blueBallCountTrend", method = {RequestMethod.POST,RequestMethod.GET})
    public String blueBallCountTrend(HttpServletRequest request, @RequestParam(value = "step") Integer step ) throws IOException {
        return generateTrend(historyAnalysisService.getBlueBallCountTrend(step));
    }

    /**
     * 根据历史记录，获取红球缺失次数趋势图数据
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/redBallHisMissCountTrend", method = {RequestMethod.POST,RequestMethod.GET})
    public String redBallHisMissCountTrend() throws IOException {
        return generateMissCountTrend(historyAnalysisService.getRedBallHisMissCounts());
    }

    /**
     * 根据历史记录，获取蓝球缺失次数趋势图数据
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/blueBallHisMissCountTrend", method = {RequestMethod.POST,RequestMethod.GET})
    public String blueBallHisMissCountTrend() throws IOException {
        return generateMissCountTrend(historyAnalysisService.getBlueBallHisMissCounts());
    }

    /**
     * 生成最近last次开奖结果中，各红球出现次数
     * @param request
     * @param last
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/redBallCountInLastBar",method = {RequestMethod.POST,RequestMethod.GET})
    public String redBallCountInLastBar(HttpServletRequest request, @RequestParam(value = "last") Integer last){
        return generateBallCountBar(historyAnalysisService.getTopRedInLast(last),"红球近"+last+"期出现次数");
    }

    /**
     * 生成最近last次开奖结果中，各蓝球出现次数
     * @param request
     * @param last
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blueBallCountInLastBar",method = {RequestMethod.POST,RequestMethod.GET})
    public String blueBallCountInLastBar(HttpServletRequest request, @RequestParam(value = "last") Integer last){
        return generateBallCountBar(historyAnalysisService.getTopBlueInLast(last),"蓝球近"+last+"期出现次数");
    }

    /**
     * 生成最近开奖结果中，各红球缺失次数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/redBallMissCountBar",method = {RequestMethod.POST,RequestMethod.GET})
    public String redBallMissCountBar(){
        return generateBallCountBar(historyAnalysisService.getRedBallMissCounts(),"红球缺失次数");
    }

    /**
     * 生成最近开奖结果中，各蓝球缺失次数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blueBallMissCountBar",method = {RequestMethod.POST,RequestMethod.GET})
    public String blueBallMissCountBar(){
        return generateBallCountBar(historyAnalysisService.getBlueBallMissCounts(),"蓝球缺失次数");
    }

    /**
     * 生成历史开奖结果中，各红球最大缺失次数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/redBallMaxMissCountBar",method = {RequestMethod.POST,RequestMethod.GET})
    public String redBallMaxMissCountBar(){
        return generateBallCountBar(historyAnalysisService.getRedBallHisMaxMissCounts(),"红球历史最大缺失次数");
    }

    /**
     * 生成最近开奖结果中，各蓝球缺失次数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blueBallMaxMissCountBar",method = {RequestMethod.POST,RequestMethod.GET})
    public String blueBallMaxMissCountBar(){
        return generateBallCountBar(historyAnalysisService.getBlueBallHisMaxMissCounts(),"蓝球历史最大缺失次数");
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
     * 趋势数据可以放到该页面展示：
     * https://www.echartsjs.com/examples/zh/editor.html?c=dataset-link
     * @param ballCountTrend
     * @return
     */
    private String generateTrend(BallCountTrend ballCountTrend) {
        JSONObject option = new JSONObject();
        option.put("title", new JSONObject().fluentPut("text", "top red 趋势"));
        option.put("tooltip", new JSONObject().fluentPut("trigger", "axis").fluentPut("showContent", false));
        option.put("legend", new JSONObject());
        option.put("xAxis", new JSONObject().fluentPut("type", "category"));
        option.put("yAxis", new JSONObject().fluentPut("gridIndex", 0));
        option.put("grid", new JSONObject().fluentPut("top", "55%"));
        JSONArray sources = new JSONArray();
        JSONArray product = new JSONArray();
        product.add("product");
        product.addAll(ballCountTrend.getCounts());
        sources.add(product);
        for (Integer number : ballCountTrend.getBallNumberCountsMap().keySet()) {
            JSONArray nc = new JSONArray();
            nc.add(number + "");
            nc.addAll(ballCountTrend.getBallNumberCountsMap().get(number));
            sources.add(nc);
        }
        option.put("dataset", new JSONObject().fluentPut("source", sources));

        JSONArray series = new JSONArray();
        for (int i = 0 ; i < ballCountTrend.getBallNumberCountsMap().size() ; i ++) {
            series.add(new JSONObject()
                    .fluentPut("smooth", true)
                    .fluentPut("type", "line")
                    .fluentPut("seriesLayoutBy", "row")
            );
        }
        series.add(new JSONObject()
                .fluentPut("type", "pie")
                .fluentPut("id", "pie")
                .fluentPut("radius", "30%")
                .fluentPut("center", new JSONArray().fluentAdd("50%").fluentAdd("25%"))
                .fluentPut("label", new JSONObject().fluentPut("formatter", "{b}: {@100} ({d}%)"))
                .fluentPut("encode", new JSONObject().fluentPut("itemName", "product").fluentPut("value", 100).fluentPut("tooltip", 100))
        );
        option.put("series", series);
        String result = option.toString(SerializerFeature.UseSingleQuotes, SerializerFeature.PrettyFormat);
        result = result.replaceAll("'(\\w+)'(\\s*:\\s*)", "$1$2");
        return result;
    }

    /**
     * 根据历史统计结果，拼装出趋势图所需要的数据
     * 趋势数据可以放到该页面展示：
     * https://www.echartsjs.com/examples/zh/editor.html?c=dataset-link
     * @param ballMissCountTrend
     * @return
     */
    private String generateMissCountTrend(BallMissCountTrend ballMissCountTrend) {
        JSONObject option = new JSONObject();
        option.put("title", new JSONObject().fluentPut("text", "历史缺失次数趋势"));
        option.put("tooltip", new JSONObject().fluentPut("trigger", "axis").fluentPut("showContent", false));
        option.put("legend", new JSONObject());
        option.put("xAxis", new JSONObject().fluentPut("type", "category"));
        option.put("yAxis", new JSONObject().fluentPut("gridIndex", 0));
        option.put("grid", new JSONObject().fluentPut("top", "55%"));
        JSONArray sources = new JSONArray();
        JSONArray product = new JSONArray();
        product.add("product");
        Collection<List<Integer>> valuess = ballMissCountTrend.getBallNumberMissCountsMap().values();
        int max = 0;
        for(List<Integer> values : valuess){
            if(values.size() > max){
                max = values.size();
            }
        }
        for(int i = 1 ; i <= max ; i ++){
            product.add(i+"");
        }
        sources.add(product);
        for (Integer number : ballMissCountTrend.getBallNumberMissCountsMap().keySet()) {
            JSONArray nc = new JSONArray();
            nc.add(number + "");
            nc.addAll(ballMissCountTrend.getBallNumberMissCountsMap().get(number));
            sources.add(nc);
        }
        option.put("dataset", new JSONObject().fluentPut("source", sources));

        JSONArray series = new JSONArray();
        for (int i = 0 ; i < ballMissCountTrend.getBallNumberMissCountsMap().size() ; i ++) {
            series.add(new JSONObject()
                    .fluentPut("smooth", true)
                    .fluentPut("type", "line")
                    .fluentPut("seriesLayoutBy", "row")
            );
        }
        series.add(new JSONObject()
                .fluentPut("type", "pie")
                .fluentPut("id", "pie")
                .fluentPut("radius", "30%")
                .fluentPut("center", new JSONArray().fluentAdd("50%").fluentAdd("25%"))
                .fluentPut("label", new JSONObject().fluentPut("formatter", "{b}: {@100} ({d}%)"))
                .fluentPut("encode", new JSONObject().fluentPut("itemName", "product").fluentPut("value", 100).fluentPut("tooltip", 100))
        );
        option.put("series", series);
        String result = option.toString(SerializerFeature.UseSingleQuotes, SerializerFeature.PrettyFormat);
        result = result.replaceAll("'(\\w+)'(\\s*:\\s*)", "$1$2");
        return result;
    }
}
