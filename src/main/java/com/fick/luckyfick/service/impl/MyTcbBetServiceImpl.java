package com.fick.luckyfick.service.impl;

import com.alibaba.fastjson.JSON;
import com.fick.luckyfick.biz.MyTcbManage;
import com.fick.luckyfick.biz.TcbHistoryManage;
import com.fick.luckyfick.constants.LuckyFickConstants;
import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.MyBet;
import com.fick.luckyfick.model.PrizeType;
import com.fick.luckyfick.model.SmsSendParam;
import com.fick.luckyfick.service.BetService;
import com.fick.luckyfick.service.MyTcbBetService;
import com.fick.luckyfick.service.SmsService;
import com.fick.luckyfick.utils.BetUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/22
 **/
@Service
@Slf4j
public class MyTcbBetServiceImpl implements MyTcbBetService {

    @Value("${com.fick.luckyfick.bet.notification.phone.numbers}")
    private String betNotificationNumbers;

    @Value("${com.fick.luckyfick.bet.lucky.notification.phone.numbers}")
    private String betLuckyNotificationNumbers;

    @Autowired
    MyTcbManage myTcbManage;

    @Autowired
    TcbHistoryManage tcbHistoryManage;

    @Autowired
    BetService betService;

    @Autowired
    SmsService smsService;

    /**
     * 今日投注历史
     */
    List<MyBet> todayBetHistory = new ArrayList<>();

    boolean isTripleLucky = false;

    boolean isDoubleLucky = false;

    boolean isLucky = false;

    @Override
    public Integer addMyBet(MyBet myBet) {
        return myTcbManage.addMyBet(myBet);
    }

    private MyBet getTempBet(int type){
        Random random = new Random();
        int testNumber = random.nextInt(50000);
        MyBet tempLucky = null;
        for(int i = 0 ; i < testNumber ; i ++) {
            long bt = System.currentTimeMillis();
            switch (type){
                case 3: tempLucky = betService.tripleLucky();
                break;
                case 2: tempLucky = betService.doubleLucky();
                break;
                case 1:
                default: tempLucky = betService.lucky();
                break;
            }
            if(BetUtils.isIn(tempLucky,todayBetHistory)){
                continue;
            }
            todayBetHistory.add(tempLucky);
            long et = System.currentTimeMillis();
            if((bt - et ) % 8 == 0 && bt % 8 == 0 && et % 8 == 0){
                break;
            }
        }
        return tempLucky;
    }

    private MyBet getFinalTempBet(int type){
        Random random = new Random();
        int num = random.nextInt(100);
        List<Integer> reds = new ArrayList<>();
        List<Integer> blues = new ArrayList<>();
        for(int i = 0 ; i < num ; i ++){
            MyBet temp = getTempBet(type);
            reds.add(temp.getRed1());
            reds.add(temp.getRed2());
            reds.add(temp.getRed3());
            reds.add(temp.getRed4());
            reds.add(temp.getRed5());
            reds.add(temp.getRed6());
            blues.add(temp.getBlue1());
        }
        MyBet retValue = new MyBet();
        List<Integer> finalReds = new ArrayList<>(6);
        for(int i = 0 ; i < 6 ; i ++){
            Integer tempRed = getRandomOne(reds);
            finalReds.add(tempRed);
            reds = reds.stream().filter(item -> item.intValue() != tempRed).collect(Collectors.toList());
        }
        Collections.sort(finalReds);
        retValue.setRed1(finalReds.get(0));
        retValue.setRed2(finalReds.get(1));
        retValue.setRed3(finalReds.get(2));
        retValue.setRed4(finalReds.get(3));
        retValue.setRed5(finalReds.get(4));
        retValue.setRed6(finalReds.get(5));
        retValue.setBlue1(getRandomOne(blues));
        return retValue;
    }

    private Integer getRandomOne(List<Integer> pool){
        Random random = new Random();
        Integer t1 = pool.get(random.nextInt(pool.size()));
        return t1;
    }


    @Override
    public void generateMyBet() {
        long gbt = System.currentTimeMillis();
        // 每天清空
        todayBetHistory = new ArrayList<>();
        isTripleLucky = false;
        isDoubleLucky = false;
        isLucky = false;
        Integer maxCode = tcbHistoryManage.getLatestCode();
        Integer codeToBet = null;
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        // 如果明天(或后天）是第二年，则code从头算
        if(dayOfWeek == 5){
            c.add(Calendar.DAY_OF_YEAR,2);
        } else {
            c.add(Calendar.DAY_OF_YEAR, 1);
        }
        int year2 = c.get(Calendar.YEAR);
        if(year2 > year){
            codeToBet = year2 * 1000 + 1;
        } else {
            codeToBet = maxCode + 1;
        }

        MyBet tripleLucky = getFinalTempBet(3);
        tripleLucky.setCode(codeToBet);
        tripleLucky.setDate(System.currentTimeMillis());
        tripleLucky.setMultiple(3);
        log.info("generated triple lucky bet {}.", JSON.toJSONString(tripleLucky));
        myTcbManage.addMyBet(tripleLucky);

        // 2. 再生成一个1倍的
        MyBet doubleLucky = getFinalTempBet(2);
        doubleLucky.setCode(codeToBet);
        doubleLucky.setDate(System.currentTimeMillis());
        doubleLucky.setMultiple(1);
        log.info("generated double lucky bet {}.", JSON.toJSONString(doubleLucky));
        myTcbManage.addMyBet(doubleLucky);

        // 3. 再生成一个1倍的
        MyBet lucky = getFinalTempBet(1);
        lucky.setCode(codeToBet);
        lucky.setDate(System.currentTimeMillis());
        lucky.setMultiple(1);
        log.info("generated lucky bet {}.", JSON.toJSONString(lucky));
        myTcbManage.addMyBet(lucky);
        // 通过短信发送
        if(StringUtils.isNotBlank(betNotificationNumbers)) {
            SmsSendParam smsSendParam = new SmsSendParam();
            smsSendParam.setContentParams(Arrays.asList(getMsgContent(tripleLucky) + getMsgContent(doubleLucky) + getMsgContent(lucky)));
            smsSendParam.setMsgType(LuckyFickConstants.SMSMsgType.BET_NOTIFICATION);
            smsSendParam.setPhoneNumbers(Arrays.asList(betNotificationNumbers.split(",")));
            smsService.sendSms(smsSendParam);
        }
        todayBetHistory = null;
        log.info("generate lucky bets total cost {} ms.",System.currentTimeMillis() - gbt);
    }

    /**
     * 每一个投注的短信内容
     * @param myBet
     * @return
     */
    private String getMsgContent(MyBet myBet){
        StringBuilder sb = new StringBuilder("");
        sb.append(myBet.getRed1());
        sb.append(",");
        sb.append(myBet.getRed2());
        sb.append(",");
        sb.append(myBet.getRed3());
        sb.append(",");
        sb.append(myBet.getRed4());
        sb.append(",");
        sb.append(myBet.getRed5());
        sb.append(",");
        sb.append(myBet.getRed6());
        sb.append(":");
        sb.append(myBet.getBlue1());
        sb.append(";");
//        sb.append("-");
//        sb.append(myBet.getMultiple());
//        sb.append("倍;");
        return sb.toString();
    }

    @Override
    public void checkResult() {
        log.info("check my bet result.");
        Integer maxCode = tcbHistoryManage.getLatestCode();
        Bet luckyBet = tcbHistoryManage.getLuckyBetByCode(maxCode);
        List<MyBet> myLuckyBets = myTcbManage.getByCode(maxCode);
        if(CollectionUtils.isNotEmpty(myLuckyBets)){
            for(MyBet myLuckyBet : myLuckyBets){
                PrizeType prizeType = BetUtils.getPrizeType(luckyBet,myLuckyBet);
                // 中奖
                if(prizeType.ordinal() != PrizeType.Missed.ordinal()){
                    log.info("good,bingo.");
                    if(StringUtils.isNotBlank(betLuckyNotificationNumbers)){
                        SmsSendParam smsSendParam = new SmsSendParam();
                        smsSendParam.setPhoneNumbers(Arrays.asList(betLuckyNotificationNumbers.split(",")));
                        smsSendParam.setMsgType(LuckyFickConstants.SMSMsgType.BET_RESULT_NOTIFICATION);
                        smsSendParam.setContentParams(Arrays.asList(getLuckyNotificationMsg(myLuckyBet,luckyBet,prizeType)));
                        smsService.sendSms(smsSendParam);
                    }
                } else {
                    log.info("sorry,come on!");
                }
            }
        }
    }

    @Override
    public List<MyBet> getMyLatestBet() {
        return myTcbManage.getMyLatestBet();
    }

    @Override
    public List<MyBet> getMyBet(Integer code) {
        return myTcbManage.getByCode(code);
    }

    @Override
    public List<MyBet> getMyBetHistoryAll() {
        return myTcbManage.getMyBetHistory();
    }

    private String getLuckyNotificationMsg(MyBet bet,Bet luckyBet,PrizeType prizeType){
        StringBuilder sb = new StringBuilder("");
        sb.append("开:");
        sb.append(luckyBet.getResult());
        sb.append("我的:");
        sb.append(bet.getResult());
        sb.append("中:");
        sb.append(prizeType.getName());
        sb.append("约:");
        sb.append(prizeType.getAmount() * bet.getMultiple());
        return sb.toString();
    }
}
