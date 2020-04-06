package com.fick.luckyfick.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @name: BallCountTrend
 * @program: luckyfick
 * @description: 球号历史上缺失次数对象
 * @author: figo.song
 * @created: 2020/4/2
 **/
@Data
@ToString
public class BallMissCountTrend implements Serializable {

    private static final long serialVersionUID = -7588787160315775245L;

    /**
     * 球号
     */
    private List<Integer> ballNumbers;

    /**
     * 球号-缺失次数(s)映射
     */
    private Map<Integer,List<Integer>> ballNumberMissCountsMap;
}
