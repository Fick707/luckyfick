package com.fick.twocolorball.web.constants;

/**
 * @name: WebConstants
 * @program: twocolorball
 * @description: 模块常量
 * @author: figo.song
 * @created: 2019/12/12
 **/
public interface WebConstants {

    /**
     * 默认服务端错误码
     */
    int DEFAULT_SERVER_ERROR_CODE = 10500;

    interface CommonConstants {
        String COMMA                                = ",";
        String QUESTION                             = "?";
        String FILTER_INIT_PARAM_EXCLUSIONS         = "exclusions";
    }

    interface CommonHttpHeader {
        String HTTP_HEADER_KEY_TOKEN = "Token";
    }

}
