package com.fick.luckyfick.common;

import com.fick.luckyfick.BaseTest;
import com.fick.luckyfick.constants.LuckyFickConstants;
import com.fick.luckyfick.model.SmsSendParam;
import com.fick.luckyfick.service.SmsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @create: 2020/4/23
 **/
public class SmsServiceImplTest extends BaseTest {
    @Autowired
    SmsService smsService;

    @Test
    public void testSend(){
        SmsSendParam smsSendParam = new SmsSendParam();
        smsSendParam.setContentParams(Arrays.asList("test"));
        smsSendParam.setMsgType(LuckyFickConstants.SMSMsgType.BET_NOTIFICATION);
        smsSendParam.setPhoneNumbers(Arrays.asList("18516240550"));
        smsService.sendSms(smsSendParam);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
