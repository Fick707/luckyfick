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
public class MyBetResult implements Serializable {
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
     * 下注倍数
     */
    private Integer multiple;

    private String prizeName;

    private Long prizeAmount;

    public String getResult(){
        return red1+","+red2+","+red3+","+red4+","+red5+","+red6+","+blue1+","+multiple+".";
    }
}
