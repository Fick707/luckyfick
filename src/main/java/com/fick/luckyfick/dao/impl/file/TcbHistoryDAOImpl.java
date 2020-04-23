package com.fick.luckyfick.dao.impl.file;

import com.alibaba.fastjson.JSON;
import com.fick.common.utils.file.AbsoluteFileReader;
import com.fick.common.utils.file.CommonFileWriter;
import com.fick.common.utils.http.HttpClient;
import com.fick.luckyfick.dao.TcbHistoryDAO;
import com.fick.luckyfick.dao.http.handler.BetHistoryResponseHandler;
import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.BetHistoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/7
 **/
@Component
@Slf4j
public class TcbHistoryDAOImpl implements TcbHistoryDAO {

    @Value("${com.fick.luckyfick.tcb.his.file}")
    private String historyFileName;

    @Value("${com.fick.luckyfick.tcb.his.query.url}")
    private String tcbHistoryFetchUrl;

    @Autowired
    BetHistoryResponseHandler betHistoryResponseHandler;

    @Override
    public List<Bet> loadHistory() {
        log.info("load bet history from file");
        AbsoluteFileReader commonFileReader = new AbsoluteFileReader(historyFileName,"#",0,null);
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
    public Integer addHistory(Bet bet) {
        log.info("add tcb bet history {}.", JSON.toJSONString(bet));
        if(bet.getIndex() == null) {
            bet.setIndex(generateIndex());
        }
        CommonFileWriter fileWriter = new CommonFileWriter(historyFileName);
        fileWriter.append(String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",
                bet.getIndex(),
                bet.getCode(),
                bet.getDate(),
                bet.getWeek(),
                bet.getRed1(),
                bet.getRed2(),
                bet.getRed3(),
                bet.getRed4(),
                bet.getRed5(),
                bet.getRed6(),
                bet.getBlue1()
        ));
        return bet.getIndex();
    }

    @Override
    public void fetchFromOfficial() {
        log.info("fetch tcb bet history from official.");
        Map<String,String> params = new HashMap<>(2);
        params.put("name","ssq");
        params.put("issueCount","50");
        Map<String,String> headers = new HashMap<>(6);
        headers.put("Content-Type","application/json");
        headers.put("Accept","application/json, text/javascript, */*; q=0.01");
        headers.put("Accept-Encoding","gzip, deflate");
        headers.put("Accept-Language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Host","www.cwl.gov.cn");
        headers.put("Referer","http://www.cwl.gov.cn/kjxx/ssq/kjgg/");
        try {
            BetHistoryDTO betHistoryDTO = HttpClient.doGet(tcbHistoryFetchUrl,headers,params,betHistoryResponseHandler);
            if(betHistoryDTO != null && CollectionUtils.isNotEmpty(betHistoryDTO.getBets())){
                Bet latestBet = getLastBet();
                int maxCode = latestBet.getCode();
                int firstId = generateIndex();
                for(Bet bet : betHistoryDTO.getBets()){
                    if(bet.getCode() <= maxCode){
                        log.info("code to old.ignore and break.");
                        break;
                    }
                    bet.setIndex(firstId ++);
                    addHistory(bet);
                }
            }
        } catch (Exception e) {
            log.error("fetch tcb bet history error.");
        }
    }

    /**
     * 从历史记录中获取最后一条，用于id自增
     * @return
     */
    private Bet getLastBet() {
        List<Bet> his = loadHistory();
        return his.get(his.size() - 1);
    }

    /**
     * 根据历史记录，生成自增id
     * @return
     */
    private Integer generateIndex() {
        return getLastBet().getIndex() + 1;
    }
}
