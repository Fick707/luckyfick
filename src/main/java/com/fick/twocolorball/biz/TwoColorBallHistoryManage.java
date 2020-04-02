package com.fick.twocolorball.biz;

import com.fick.common.utils.file.CommonFileReader;
import com.fick.twocolorball.model.Bet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: twocolorball
 * @description:
 * @author: figo.song
 * @create: 2020/3/29
 **/
@Component
@Slf4j
public class TwoColorBallHistoryManage {

    private static final String historyFileName = "/twocolorball/twocolorball.rec";
    /**
     * 历史中奖记录
     */
    private List<Bet> betHistory = new ArrayList<>();

    @PostConstruct
    public void init(){
        log.info("init two color ball history ...");
        loadHistory();
        log.info("init two color ball history done.");
    }

    public List<Bet> getBetHistory(){
        return betHistory;
    }

    /**
     * 从历史文件中加载历史
     */
    public void loadHistory() {
        CommonFileReader commonFileReader = new CommonFileReader(historyFileName,null,0,null);
        List<String> lines = commonFileReader.readLines();
        if(CollectionUtils.isNotEmpty(lines)){
            for(String line : lines){
                String[] vs = line.split(",");
                Bet bet = new Bet();
                bet.setIndex(Integer.parseInt(vs[0]));
                bet.setCode(Integer.parseInt(vs[1]));
                bet.setDate(Long.parseLong(vs[2]));
                bet.setWeek(Integer.parseInt(vs[3]));
                bet.setRed1(Integer.parseInt(vs[4]));
                bet.setRed2(Integer.parseInt(vs[5]));
                bet.setRed3(Integer.parseInt(vs[6]));
                bet.setRed4(Integer.parseInt(vs[7]));
                bet.setRed5(Integer.parseInt(vs[8]));
                bet.setRed6(Integer.parseInt(vs[9]));
                bet.setBlue1(Integer.parseInt(vs[10]));
                betHistory.add(bet);
            }
        }
    }
}
