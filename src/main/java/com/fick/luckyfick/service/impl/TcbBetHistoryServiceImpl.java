package com.fick.luckyfick.service.impl;

import com.fick.luckyfick.biz.TcbHistoryManage;
import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.service.TcbBetHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/22
 **/
@Service
@Slf4j
public class TcbBetHistoryServiceImpl implements TcbBetHistoryService {

    @Autowired
    TcbHistoryManage tcbHistoryManage;

    @Override
    public Integer addBetHistory(Bet bet) {
        return tcbHistoryManage.addBetHistory(bet);
    }
}
