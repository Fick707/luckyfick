package com.fick.luckyfick.constants;

/**
 * @name: LuckyFickConstants
 * @program: luckyfick
 * @description:
 * @author: figo.song
 * @created: 2020/4/23
 **/
public interface LuckyFickConstants {

    /**
     * 短信类型
     */
    interface SMSMsgType {
        /**
         * 投注通知
         */
        int BET_NOTIFICATION = 1;

        /**
         * 投注结果通知
         */
        int BET_RESULT_NOTIFICATION = 2;
    }
}
