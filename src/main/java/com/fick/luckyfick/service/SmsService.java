package com.fick.luckyfick.service;

import com.fick.luckyfick.model.SmsSendParam;

/**
 * @program: luckyfick
 * @description: 短信服务，短信网关功能
 * @author: figo.song
 * @create: 2020/2/24
 **/
public interface SmsService {

    /**
     * 发送短信,暂不用消息队列缓存，因为消息量不大，该接口不对外部开放
     * @param param
     * @return
     */
    void sendSms(SmsSendParam param);
}
