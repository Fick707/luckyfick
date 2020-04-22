package com.fick.luckyfick.common;

import com.fick.luckyfick.BaseTest;
import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.service.TcbBetHistoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/22
 **/
public class TcbBetHistoryServiceImplTest extends BaseTest {
    @Autowired
    TcbBetHistoryService tcbBetHistoryService;

    @Test
    public void testAdd(){
        Bet bet = betOne(1,2,5,8,23,32,13);
        bet.setCode(2020028);
        bet.setWeek(2);
        bet.setDate(System.currentTimeMillis());
        tcbBetHistoryService.addBetHistory(bet);
    }
}
