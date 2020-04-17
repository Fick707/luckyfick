package com.fick.luckyfick.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @name: BallCountTrend
 * @program: luckyfick
 * @description: 球号出现频次对象
 * @author: figo.song
 * @created: 2020/4/2
 **/
@Data
@ToString
public class BallCountTrend implements Serializable {

    private static final long serialVersionUID = -7588787160315775245L;

    /**
     * 历史期数
     */
    private List<Integer> counts;

    /**
     * 球号-出现频次(s)映射
     */
    private Map<Integer,List<Integer>> ballNumberCountsMap;
}
