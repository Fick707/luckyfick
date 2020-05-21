package com.fick.luckyfick.web.model.param;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @name: HistoryParam
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @created: 2020/4/17
 **/
@Data
@ToString
public class BetParam implements Serializable {
    private static final long serialVersionUID = 4555736148815659713L;

    private Integer code;
}
