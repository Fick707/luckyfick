package com.fick.luckyfick.biz;

import com.alibaba.fastjson.JSON;
import com.fick.luckyfick.dao.MyTcbDAO;
import com.fick.luckyfick.model.MyBet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: luckyfick
 * @description: 我的双色球投注历史
 * @author: figo.song
 * @create: 2020/4/22
 **/
@Component
@Slf4j
public class MyTcbManage {

    @Autowired
    MyTcbDAO myTcbDAO;

    /**
     * 历史中奖记录
     */
    private List<MyBet> myBetHistory = new ArrayList<>();

    @PostConstruct
    public void init(){
        log.info("init my two color ball history ...");
        loadHistory();
        log.info("init my two color ball history done.");
    }

    /**
     * 从历史文件中加载历史
     */
    public void loadHistory() {
        log.info("loading my tcb lucky bets history ...");
        myBetHistory = myTcbDAO.loadMyHistory();
        log.info("load my tcb lucky bets history done,size:{}.",myBetHistory.size());
    }

    public List<MyBet> getMyBetHistory(){
        return myBetHistory;
    }

    /**
     * 添加我的抽注记录
     * @param myBet
     * @return
     */
    public Integer addMyBet(MyBet myBet){
        log.info("add my bet {}.", JSON.toJSON(myBet));
        Integer index = myTcbDAO.addMyBet(myBet);
        loadHistory();
        return index;
    }

    public List<MyBet> getMyLatestBet(){
        return getByCode(getMyLatestBetCode());
    }

    public Integer getMyLatestBetCode(){
        return myTcbDAO.getMyLastBetCode();
    }

    /**
     * 根据期号，获取我的投注列表
     * @param code
     * @return
     */
    public List<MyBet> getByCode(Integer code){
        List<MyBet> his = getMyBetHistory();
        List<MyBet> myBets = new ArrayList<>();
        if(CollectionUtils.isEmpty(his)){
            return myBets;
        }
        // 从最新往前找
        for(int i = his.size() - 1 ; i >= 0 ; i --){
            MyBet luckyBet = his.get(i);
            if(code.intValue() == luckyBet.getCode()){
                myBets.add(luckyBet);
            }
            // 如果期号已经很小了，直接返回null;
            if(luckyBet.getCode() < code){
                break;
            }
        }
        return myBets;
    }
}
