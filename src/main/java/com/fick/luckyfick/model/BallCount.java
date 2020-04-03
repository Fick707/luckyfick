package com.fick.luckyfick.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: luckyfick
 * @description: 球号-出现次数映射对象
 * @author: figo.song
 * @create: 2020/3/29
 **/
@Data
public class BallCount implements Serializable {
    private static final long serialVersionUID = 4031005334449328552L;
    /**
     * 球号
     */
    private Integer ballNumber;

    /**
     * 出现次数
     */
    private Integer count;
}
