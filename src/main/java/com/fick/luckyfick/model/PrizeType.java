package com.fick.luckyfick.model;

/**
 * @program: luckyfick
 * @description: 中奖等级
 * @author: figo.song
 * @create: 2020/4/6
 **/
public enum PrizeType {

    Missed(0L,"未中奖","未中任何奖级"),
    First(5000000L,"一等奖","6+1,红球全中&蓝球全中,一般高于500万元,1/17721088=0.0000056%"),
    Second(5000000L,"二等奖","6+0,红球全中&蓝球未中,最高500万元,15/17721088=0.0000846%"),
    Third(3000L,"三等奖","5+1,红球中5枚,蓝球中1枚,3000元,162/17721088=0.000914%"),
    Fourth(200L,"四等奖","5+0|4+1,红球中5枚&蓝球未中 或者 红球中4枚&蓝球中1枚,200元,7695/17721088=0.0434%"),
    Fifth(10L,"五等奖","4+0|3+1,红球中4枚&蓝球未中 或者 红球中3枚&蓝球中1枚,10元,137475/17721088=0.7758%"),
    Sixth(5L,"六等奖","2+1|1+1|0+1,红求中3枚以下&蓝球中1,5元,1043640/17721088=5.889%")
    ;


    /**
     * 中奖金额
     */
    private Long amount;
    /**
     * 奖级名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;
    PrizeType(Long amount,String name,String desc){
        this.amount = amount;
        this.name = name;
        this.desc = desc;
    }

    /**
     * 获取中奖金额
     * @return
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * 获取中奖名称
     * @return
     */
    public String getName() {
        return name;
    }
}
