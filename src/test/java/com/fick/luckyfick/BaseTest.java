package com.fick.luckyfick;

import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.MyBet;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Author: fick
 * @CreatedAt: 2019-05-24 23:16
 */
@SpringBootTest(classes={Application.class})
@RunWith(SpringRunner.class)
@Slf4j
public class BaseTest {

    @Test
    public void test(){
        log.info("shah");
    }

    protected Bet betOne(Integer red1, Integer red2, Integer red3, Integer red4, Integer red5, Integer red6, Integer blue1){
        Bet bet = new Bet();
        bet.setRed1(red1);
        bet.setRed2(red2);
        bet.setRed3(red3);
        bet.setRed4(red4);
        bet.setRed5(red5);
        bet.setRed6(red6);
        bet.setBlue1(blue1);
        return bet;
    }

    protected MyBet betOneMy(Integer red1, Integer red2, Integer red3, Integer red4, Integer red5, Integer red6, Integer blue1,Integer multiple){
        MyBet bet = new MyBet();
        bet.setRed1(red1);
        bet.setRed2(red2);
        bet.setRed3(red3);
        bet.setRed4(red4);
        bet.setRed5(red5);
        bet.setRed6(red6);
        bet.setBlue1(blue1);
        bet.setMultiple(multiple);
        return bet;
    }

}
