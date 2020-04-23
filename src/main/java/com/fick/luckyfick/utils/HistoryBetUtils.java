package com.fick.luckyfick.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fick.common.utils.file.CommonFileReader;
import com.fick.common.utils.file.CommonFileWriter;
import com.fick.luckyfick.model.Bet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/3/29
 **/
@Slf4j
public class HistoryBetUtils {
    public static void main(String[] args) {
        List<Bet> allBets = new ArrayList<>();
        List<String> fileNames = Arrays.asList(
                "/his/201301.json",
                "/his/201302.json",
                "/his/201401.json",
                "/his/201402.json",
                "/his/201501.json",
                "/his/201502.json",
                "/his/201601.json",
                "/his/201602.json",
                "/his/201701.json",
                "/his/201702.json",
                "/his/201801.json",
                "/his/201802.json",
                "/his/201901.json",
                "/his/201902.json",
                "/his/202001.json"
                );

        for(String fileName : fileNames){
            allBets.addAll(load(fileName));
        }
        allBets.addAll(load0312());
        if(CollectionUtils.isNotEmpty(allBets)){
            // 去重，排序
            Set<Integer> codes = new HashSet<>();
            allBets = allBets.stream().filter(item -> {
                if(codes.contains(item.getCode())){
                    return false;
                } else {
                    codes.add(item.getCode());
                    return true;
                }
            }).collect(Collectors.toList());
            Collections.sort(allBets, Comparator.comparing(Bet::getCode));
            System.out.println("bet size :"+allBets.size());
            StringBuffer sb = new StringBuffer("");
            int index = 1;
            for(Bet bet : allBets){
                sb.append(String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",
                        index ++,
                        bet.getCode(),
                        bet.getDate(),
                        bet.getWeek(),
                        bet.getRed1(),
                        bet.getRed2(),
                        bet.getRed3(),
                        bet.getRed4(),
                        bet.getRed5(),
                        bet.getRed6(),
                        bet.getBlue1()));
            }
            CommonFileWriter fileWriter = new CommonFileWriter("twocolorball.rec");
            fileWriter.write(sb.toString());
        }
    }

    private static List<Bet> load(String fileName){
        List<Bet> betList = new ArrayList<>();
        CommonFileReader commonFileReader = new CommonFileReader(fileName,null,0,null);
        String content = commonFileReader.getFileContent();
        if(StringUtils.isNotBlank(content)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            JSONObject result = JSON.parseObject(content);
            JSONArray results = result.getJSONArray("result");
            if(results != null && !results.isEmpty()){
                for(int i = 0 ; i < results.size() ; i ++){
                    JSONObject b = results.getJSONObject(i);
                    Bet bet = new Bet();
                    Integer code = Integer.parseInt(b.getString("code"));
                    bet.setCode(code);
                    bet.setBlue1(Integer.parseInt(b.getString("blue")));
                    String date = b.getString("date");
                    date = StringUtils.substringBefore(date,"(");
                    try {
                        bet.setDate(sdf.parse(date).getTime());
                    } catch (ParseException e) {
                        System.out.println("parse date error.");
                    }
                    String red = b.getString("red");
                    String[] reds = red.split(",");
                    bet.setRed1(Integer.parseInt(reds[0]));
                    bet.setRed2(Integer.parseInt(reds[1]));
                    bet.setRed3(Integer.parseInt(reds[2]));
                    bet.setRed4(Integer.parseInt(reds[3]));
                    bet.setRed5(Integer.parseInt(reds[4]));
                    bet.setRed6(Integer.parseInt(reds[5]));
                    bet.setWeek(getWeek(b.getString("week")));
                    betList.add(bet);
                }
            }
        }
//        Collections.reverse(betList);
        return betList;
    }

    private static List<Bet> load0312(){
        List<Bet> betList = new ArrayList<>();
        CommonFileReader commonFileReader = new CommonFileReader("/his/03-12.log","",0,null);
        List<String> lines = commonFileReader.readLines();
        if(CollectionUtils.isNotEmpty(lines)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            for(String line : lines){
                Bet bet = new Bet();
                String[] vs = StringUtils.split(line,",");
                String code = "20"+vs[0];
                bet.setCode(Integer.parseInt(code));
                String dateStr = vs[9];
                try {
                    Date date = sdf.parse(dateStr);
                    c.setTime(date);
                    bet.setDate(date.getTime());
                    bet.setWeek(c.get(Calendar.DAY_OF_WEEK)+1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                bet.setRed1(Integer.parseInt(vs[1]));
                bet.setRed2(Integer.parseInt(vs[2]));
                bet.setRed3(Integer.parseInt(vs[3]));
                bet.setRed4(Integer.parseInt(vs[4]));
                bet.setRed5(Integer.parseInt(vs[5]));
                bet.setRed6(Integer.parseInt(vs[6]));
                bet.setBlue1(Integer.parseInt(vs[7]));
                betList.add(bet);
            }
        }

        return betList;
    }

    public static Integer getWeek(String week){
        switch (week){
            case "二":return 2;
            case "四":return 4;
            case "日":return 7;
            default:return 0;
        }
    }
}
