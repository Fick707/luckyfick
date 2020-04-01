package com.fick.twocolorball.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fick.twocolorball.biz.TwoColorBallHistoryManage;
import com.fick.twocolorball.model.Bet;
import com.fick.twocolorball.model.NumberCount;
import com.fick.twocolorball.service.HistoryAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: twocolorball
 * @description: 历史中奖号码分析服务
 * @author: figo.song
 * @create: 2020/3/29
 **/
@Service
@Slf4j
public class HistoryAnalysisServiceImpl implements HistoryAnalysisService {

    @Autowired
    TwoColorBallHistoryManage historyManage;

    public static List<Integer> topRed = new ArrayList<>(33);

    public static List<Integer> topBlue = new ArrayList<>(16);

    @Override
    public List<Integer> getTopRed() {
        if(CollectionUtils.isEmpty(topRed)){
            List<Bet> betList = historyManage.getBetHistory();
            List<NumberCount> numberCounts = generateTopRed(betList);
            log.info("red top:{}.", JSON.toJSON(numberCounts));
            topRed = numberCounts.stream().map(NumberCount::getNumber).collect(Collectors.toList());
        }
        return topRed;
    }

    @Override
    public List<Integer> getTopBlue() {
        if(CollectionUtils.isEmpty(topBlue)){
            List<Bet> betList = historyManage.getBetHistory();
            List<NumberCount> numberCounts = generateTopBlue(betList);
            log.info("blue top:{}.", JSON.toJSON(numberCounts));
            topBlue = numberCounts.stream().map(NumberCount::getNumber).collect(Collectors.toList());
        }
        return topBlue;
    }

    @Override
    public String generateRedTrendChart(int step) {
        // 将生成的数据，放到echart上运行，看效果图：https://www.echartsjs.com/examples/zh/editor.html?c=dataset-link
        int from = 0;
        int to = from + step;
        List<Bet> bets = historyManage.getBetHistory();
        List<String> counts = new ArrayList<>();
        Map<Integer,List<Integer>> numberCountsMap = new HashMap<>();
        while (to < bets.size()){
            List<NumberCount> numberCounts = generateTopRed(bets.subList(from,to));
            for(NumberCount numberCount : numberCounts){
                Integer number = numberCount.getNumber();
                List<Integer> countList = numberCountsMap.get(number);
                if(countList == null){
                    countList = new ArrayList<>();
                    numberCountsMap.put(number,countList);
                }
                countList.add(numberCount.getCount());
            }
            counts.add(to+"");
            from += step;
            to = from + step;
        }

        JSONObject option = new JSONObject();
        option.put("title",new JSONObject().fluentPut("text","top red 趋势"));
        option.put("tooltip",new JSONObject().fluentPut("trigger","axis").fluentPut("showContent",false));
        option.put("legend",new JSONObject());
        option.put("xAxis",new JSONObject().fluentPut("type","category"));
        option.put("yAxis",new JSONObject().fluentPut("gridIndex",0));
        option.put("grid",new JSONObject().fluentPut("top","55%"));
        JSONArray sources = new JSONArray();
        JSONArray product = new JSONArray();
        product.add("product");
        product.addAll(counts);
        sources.add(product);
        for(Integer number : numberCountsMap.keySet()){
            JSONArray nc = new JSONArray();
            nc.add(number+"");
            nc.addAll(numberCountsMap.get(number));
            sources.add(nc);
        }
        option.put("dataset",new JSONObject().fluentPut("source",sources));

        JSONArray series = new JSONArray();
        for(Integer number : numberCountsMap.keySet()){
            series.add(new JSONObject()
                    .fluentPut("smooth",true)
                    .fluentPut("type","line")
                    .fluentPut("seriesLayoutBy","row")
            );
        }
        series.add(new JSONObject()
                .fluentPut("type","pie")
                .fluentPut("id","pie")
                .fluentPut("radius","30%")
                .fluentPut("center",new JSONArray().fluentAdd("50%").fluentAdd("25%"))
                .fluentPut("label",new JSONObject().fluentPut("formatter","{b}: {@100} ({d}%)"))
                .fluentPut("encode",new JSONObject().fluentPut("itemName","product").fluentPut("value",100).fluentPut("tooltip",100))
        );
        option.put("series",series);
        log.info("generate red trend chart done.");
//        SerializerFeature.config()
        String result = option.toString(SerializerFeature.UseSingleQuotes,SerializerFeature.PrettyFormat);

        result = result.replaceAll("'(\\w+)'(\\s*:\\s*)", "$1$2");
        log.info("{}",result);
        return result;
    }

    @Override
    public String generateBlueTrendChart(int step) {
        // 将生成的数据，放到echart上运行，看效果图：https://www.echartsjs.com/examples/zh/editor.html?c=dataset-link
        int from = 0;
        int to = from + step;
        List<Bet> bets = historyManage.getBetHistory();
        List<String> counts = new ArrayList<>();
        Map<Integer,List<Integer>> numberCountsMap = new HashMap<>();
        while (to < bets.size()){
            List<NumberCount> numberCounts = generateTopBlue(bets.subList(from,to));
            for(NumberCount numberCount : numberCounts){
                Integer number = numberCount.getNumber();
                List<Integer> countList = numberCountsMap.get(number);
                if(countList == null){
                    countList = new ArrayList<>();
                    numberCountsMap.put(number,countList);
                }
                countList.add(numberCount.getCount());
            }
            counts.add(to+"");
            from += step;
            to = from + step;
        }

        JSONObject option = new JSONObject();
        option.put("title",new JSONObject().fluentPut("text","top red 趋势"));
        option.put("tooltip",new JSONObject().fluentPut("trigger","axis").fluentPut("showContent",false));
        option.put("legend",new JSONObject());
        option.put("xAxis",new JSONObject().fluentPut("type","category"));
        option.put("yAxis",new JSONObject().fluentPut("gridIndex",0));
        option.put("grid",new JSONObject().fluentPut("top","55%"));
        JSONArray sources = new JSONArray();
        JSONArray product = new JSONArray();
        product.add("product");
        product.addAll(counts);
        sources.add(product);
        for(Integer number : numberCountsMap.keySet()){
            JSONArray nc = new JSONArray();
            nc.add(number+"");
            nc.addAll(numberCountsMap.get(number));
            sources.add(nc);
        }
        option.put("dataset",new JSONObject().fluentPut("source",sources));

        JSONArray series = new JSONArray();
        for(Integer number : numberCountsMap.keySet()){
            series.add(new JSONObject()
                    .fluentPut("smooth",true)
                    .fluentPut("type","line")
                    .fluentPut("seriesLayoutBy","row")
            );
        }
        series.add(new JSONObject()
                .fluentPut("type","pie")
                .fluentPut("id","pie")
                .fluentPut("radius","30%")
                .fluentPut("center",new JSONArray().fluentAdd("50%").fluentAdd("25%"))
                .fluentPut("label",new JSONObject().fluentPut("formatter","{b}: {@100} ({d}%)"))
                .fluentPut("encode",new JSONObject().fluentPut("itemName","product").fluentPut("value",100).fluentPut("tooltip",100))
        );
        option.put("series",series);
        log.info("generate blue trend chart done.");
//        SerializerFeature.config()
        String result = option.toString(SerializerFeature.UseSingleQuotes,SerializerFeature.PrettyFormat);

        result = result.replaceAll("'(\\w+)'(\\s*:\\s*)", "$1$2");
        log.info("{}",result);
        return result;
    }


    private List<NumberCount> generateTopRed(List<Bet> betList) {
        Map<Integer,Integer> countMap = new HashMap<Integer,Integer>();
        if(CollectionUtils.isNotEmpty(betList)){
            betList.stream().forEach(item -> {
                increase(item.getRed1(),countMap);
                increase(item.getRed2(),countMap);
                increase(item.getRed3(),countMap);
                increase(item.getRed4(),countMap);
                increase(item.getRed5(),countMap);
                increase(item.getRed6(),countMap);
            });
        }
        List<NumberCount> numberCounts = new ArrayList<>();
        for(Integer number : countMap.keySet()){
            NumberCount numberCount = new NumberCount();
            numberCount.setNumber(number);
            numberCount.setCount(countMap.get(number));
            numberCounts.add(numberCount);
        }
        Collections.sort(numberCounts, Comparator.comparing(NumberCount::getCount).reversed());
        return numberCounts;
    }

    private List<NumberCount> generateTopBlue(List<Bet> betList) {
        Map<Integer,Integer> countMap = new HashMap<Integer,Integer>();
        if(CollectionUtils.isNotEmpty(betList)){
            betList.stream().forEach(item -> {
                increase(item.getBlue1(),countMap);
            });
        }
        List<NumberCount> numberCounts = new ArrayList<>();
        for(Integer number : countMap.keySet()){
            NumberCount numberCount = new NumberCount();
            numberCount.setNumber(number);
            numberCount.setCount(countMap.get(number));
            numberCounts.add(numberCount);
        }
        Collections.sort(numberCounts, Comparator.comparing(NumberCount::getCount).reversed());
        return numberCounts;
    }

    private void increase(Integer number,Map<Integer,Integer> countMap){
        Integer count = countMap.get(number);
        if(count == null){
            count = 0;
        }
        count ++;
        countMap.put(number,count);
    }

}
