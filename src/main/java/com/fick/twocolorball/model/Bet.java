package com.fick.twocolorball.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: twocolorball
 * @description:
 * @author: figo.song
 * @create: 2020/3/29
 **/
@Data
@ToString
public class Bet implements Serializable {
    private static final long serialVersionUID = 4029730139318973678L;

    /**
     * 全局索引
     */
    private Integer index;

    /**
     * 编码，年份+期码，例如2020001
     */
    private Integer code;

    /**
     * 开奖日期，时间戳
     */
    private Long date;

    /**
     * 周
     */
    private Integer week;

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
}
