package com.fick.twocolorball.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: twocolorball
 * @description:
 * @author: figo.song
 * @create: 2020/3/29
 **/
@Data
public class NumberCount implements Serializable {
    private static final long serialVersionUID = 4031005334449328552L;
    private Integer number;
    private Integer count;
}
