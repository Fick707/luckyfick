package com.fick.luckyfick.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @name: BetHistoryDTO
 * @program: luckyfick
 * @description:
 * @author: figo.song@carloha.com
 * @created: 2020/4/23
 **/
@Data
@ToString
public class BetHistoryDTO implements Serializable {
    private static final long serialVersionUID = 322484034606093609L;

    /**
     * 列表
     */
    private List<Bet> bets;
}
