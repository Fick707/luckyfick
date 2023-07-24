package com.fick.luckyfick.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: luckyfick
 * @description: 我的投注记录
 * @author: figo.song
 * @create: 2020/4/22
 **/
@Data
@ToString
public class MyBet implements Serializable {
    private static final long serialVersionUID = 7729342849746628453L;

    /**
     * 全局索引
     */
    private Integer index;

    /**
     * 编码，年份+期码，例如2020001
     */
    private Integer code;

    /**
     * 抽注时间，时间戳
     */
    private Long date;

    /**
     * 红1
     */
    private Integer red1;

    /**
     * 红2
     */
    private Integer red2;

    /**
     * 红3
     */
    private Integer red3;

    /**
     * 红4
     */
    private Integer red4;

    /**
     * 红5
     */
    private Integer red5;

    /**
     * 红6
     */
    private Integer red6;

    /**
     * 蓝1
     */
    private Integer blue1;

    /**
     * 蓝球连续缺失足够多了不？
     */
    private boolean isBlueAbsenceEnough = false;

    /**
     * 下注倍数
     */
    private Integer multiple;
    public String getResult(){
        return red1+","+red2+","+red3+","+red4+","+red5+","+red6+","+blue1+","+multiple+".";
    }
}
