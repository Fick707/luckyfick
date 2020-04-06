package com.fick.luckyfick.service.impl;

import com.fick.luckyfick.model.Bet;
import com.fick.luckyfick.model.PrizeType;
import com.fick.luckyfick.service.BetService;
import com.fick.luckyfick.utils.BetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: luckyfick
 * @description: 投注相关的服务；
 * @author: figo.song
 * @create: 2020/4/6
 **/
@Service
@Slf4j
public class BetServiceImpl implements BetService {

    @Override
    public boolean isRedBallIn(Bet bet, Integer ballNumber) {
        return BetUtils.isRedBallIn(bet,ballNumber);
    }

    @Override
    public boolean isBlueBallIn(Bet bet, Integer ballNumber) {
        return BetUtils.isBlueBallIn(bet,ballNumber);
    }

    @Override
    public PrizeType getPrizeType(Bet bet, Bet checkBet) {
        return BetUtils.getPrizeType(bet,checkBet);
    }
}
