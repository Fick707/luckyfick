package com.fick.luckyfick.dao.impl.file;

import com.fick.common.utils.file.CommonFileReader;
import com.fick.luckyfick.dao.TwoColorBallHistoryDAO;
import com.fick.luckyfick.model.Bet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/7
 **/
@Component
@Slf4j
public class TwoColorBallHistoryDAOImpl implements TwoColorBallHistoryDAO {

    private static final String historyFileName = "/twocolorball/twocolorball.rec";

    @Override
    public List<Bet> loadHistory() {
        log.info("load bet history from file");
        CommonFileReader commonFileReader = new CommonFileReader(historyFileName,null,0,null);
        List<String> lines = commonFileReader.readLines();
        if(CollectionUtils.isNotEmpty(lines)){
            List<Bet> his = new ArrayList<>();
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
                his.add(bet);
            }
            return his;
        }
        return new ArrayList<>();
    }

    @Override
    public void addHistory(Bet bet) {

    }

    @Override
    public Bet getLastBet() {
        List<Bet> his = loadHistory();
        return his.get(his.size() - 1);
    }

    @Override
    public Integer generateIndex() {
        return getLastBet().getIndex() + 1;
    }
}
