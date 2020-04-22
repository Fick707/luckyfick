package com.fick.luckyfick.service.impl;

import com.fick.luckyfick.biz.MyTcbManage;
import com.fick.luckyfick.model.MyBet;
import com.fick.luckyfick.service.MyTcbBetHistoryService;
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
public class MyTcbBetHistoryServiceImpl implements MyTcbBetHistoryService {

    @Autowired
    MyTcbManage myTcbManage;

    @Override
    public Integer addMyBet(MyBet myBet) {
        return myTcbManage.addMyBet(myBet);
    }
}
