package com.fick.luckyfick.common;

import com.fick.luckyfick.BaseTest;
import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.MyBet;
import com.fick.luckyfick.model.PrizeType;
import com.fick.luckyfick.service.BetService;
import com.fick.luckyfick.service.HistoryAnalysisService;
import com.fick.luckyfick.service.TcbBetHistoryService;
import com.fick.luckyfick.utils.BetUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/22
 **/
@Slf4j
public class TcbBetHistoryServiceImplTest extends BaseTest {
    @Autowired
    TcbBetHistoryService tcbBetHistoryService;

    @Autowired
    HistoryAnalysisService historyAnalysisService;

    @Autowired
    BetService betService;

    private static int testCount = 1;

    @Test
    public void test(){
        List<Bet> last10 = historyAnalysisService.getLastBetHistory(10);
        for(Bet bet : last10){
            // 用3倍规则尝试，看多少次可以
            testTripleLucky(bet);
        }
    }

    private void testTripleLucky(Bet bet) {
        long bt = System.currentTimeMillis();
        MyBet myBet = betService.tripleLucky();
        PrizeType prizeType = BetUtils.getPrizeType(bet,myBet);
        while (prizeType != PrizeType.First && prizeType != PrizeType.Second){
            testCount ++;
            if(testCount % 10000 == 0){
                log.warn("test count:{},cost {} s.",testCount,(System.currentTimeMillis() - bt) / 1000);
            }
            myBet = betService.tripleLucky();
            prizeType = BetUtils.getPrizeType(bet,myBet);
        }
        log.warn(String.format("bing go,%s,%d,testCount:%d",prizeType.getName(),prizeType.getAmount(),testCount));
    }

}
