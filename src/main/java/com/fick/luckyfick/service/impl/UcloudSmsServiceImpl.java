package com.fick.luckyfick.service.impl;

import cn.ucloud.common.pojo.Account;
import cn.ucloud.usms.client.DefaultUSMSClient;
import cn.ucloud.usms.client.USMSClient;
import cn.ucloud.usms.model.SendUSMSMessageParam;
import cn.ucloud.usms.model.SendUSMSMessageResult;
import cn.ucloud.usms.pojo.USMSConfig;
import com.alibaba.fastjson.JSON;
import com.fick.common.utils.good.ThreadUtils;
import com.fick.luckyfick.constants.LuckyFickConstants;
import com.fick.luckyfick.model.SmsSendParam;
import com.fick.luckyfick.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @program: luckyfick
 * @description: 短信网的ucloud实现
 * @author: figo.song
 * @create: 2020/2/24
 **/
@Service
@Slf4j
public class UcloudSmsServiceImpl implements SmsService {

    @Value("${com.fick.luckyfick.ucloud.public.key}")
    private String publicKey;

    @Value("${com.fick.luckyfick.ucloud.private.key}")
    private String privateKey;

    @Value("${com.fick.luckyfick.ucloud.project.id}")
    private String projectId;

    /**
     * 投注通知短信模板id
     */
    @Value("${com.fick.luckyfick.ucloud.usms.bet.template.id}")
    private String betTemplateId;

    /**
     * 投注中奖通知短信模板id
     */
    @Value("${com.fick.luckyfick.ucloud.usms.check.template.id}")
    private String checkTemplateId;

    /**
     * 短信网关签名内容
     */
    @Value("${com.fick.luckyfick.ucloud.usms.sig.content}")
    private String sigContent;

    private static USMSClient client;

    /**
     * usms client初始化
     */
    @PostConstruct
    public void init(){
        log.info("initial ucloud sms service client ...");
        client = new DefaultUSMSClient(new USMSConfig(
                new Account(privateKey,publicKey)));
        log.info("initial ucloud sms service client done.");
    }

    @Override
    public void sendSms(SmsSendParam param){
        log.info("send sms {}.",JSON.toJSONString(param));
        if(param == null){
            log.error("send sms,param is null.");
            return;
        }
        if(CollectionUtils.isEmpty(param.getPhoneNumbers())){
            log.error("send sms,phone numbers is empty.");
            return;
        }
        ThreadUtils.executeTask(() -> doSendSms(param));
        return;
    }

    private void doSendSms(SmsSendParam param){
        log.info("do send sms : {}.", JSON.toJSONString(param));
        SendUSMSMessageParam sendUSMSMessageParam = new SendUSMSMessageParam(param.getPhoneNumbers(), getTemplateId(param.getMsgType()));
        sendUSMSMessageParam.setProjectId(projectId);
        sendUSMSMessageParam.setTemplateParams(param.getContentParams());
        try {
            SendUSMSMessageResult result = client.sendUSMSMessage(sendUSMSMessageParam);
            log.info("send sms result {}.",JSON.toJSONString(result));
        } catch (Exception e) {
            log.error("send sms via ucloud error.",e);
        }
    }

    /**
     * 根据短信类型找到短信模板id
     * @param msgType
     * @return
     */
    private String getTemplateId(Integer msgType){
        switch (msgType){
            case LuckyFickConstants
                    .SMSMsgType.BET_NOTIFICATION:return betTemplateId;
            case LuckyFickConstants
                    .SMSMsgType.BET_RESULT_NOTIFICATION:
            default:return checkTemplateId;
        }
    }
}
