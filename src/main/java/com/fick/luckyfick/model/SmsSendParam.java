package com.fick.luckyfick.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/2/24
 **/
@Data
@ToString
public class SmsSendParam implements Serializable {
    private static final long serialVersionUID = -571739566726640804L;
    /**
     * 手机号(s)
     */
    private List<String> phoneNumbers;

    /**
     * 短信内容的参数
     */
    private List<String> contentParams;

    /**
     * 短信类型，1：投注通知；2：中奖通知
     */
    private Integer msgType;
}
