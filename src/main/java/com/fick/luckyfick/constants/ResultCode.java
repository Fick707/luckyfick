package com.fick.luckyfick.constants;

/**
 * @name: ResultCode
 * @program: common
 * @description: 结果码
 * @author: figo.song
 * @created: 2019/12/17
 **/
public enum ResultCode {
    PARAM_ERROR_CODE(10400,"请求参数有误"),
    PARAM_ERROR_UNAUTHORIZED(10401,"未登录"),
    PARAM_ERROR_NO_PERMISSION(10403,"无访问权限"),
    PARAM_DUBBOCONTEXT_ERROR_CODE(10400,"请求参数有误，dubbo上下文字段有误"),
    PARAM_ERROR_USER_STATUS_EXP(10405,"user status is inactive."),
    SERVER_ERROR_CODE(10500,"服务异常"),

    // business error
    SERVER_BUSINESS_ERROR_NO_RECORD(10601,"无记录"),
    SERVER_BUSINESS_ERROR_DUPLICATE_RECORD(10602,"已有记录"),

    // SERVER errors
    SERVER_SERVICE_ERROR_FILESTORE_EXP(10701,"文件存储异常"),
    SERVER_SERVICE_ERROR_SMS_SEND_EXP(10702,"短信发送异常"),
    SERVER_SERVICE_ERROR_EMAIL_SEND_EXP(10703,"邮件发送异常"),

    SERVER_SUCCESS_CODE(0,"操作成功");

    private int code;
    private String value;
    ResultCode(int code, String value){
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
