package com.fick.luckyfick.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: WebResult.java
 * Description: rpc结果对象
 *
 * @author figo.song
 * @version 1.0.0
 * @date 2019/12/06
 */
public final class WebResult<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4567878339529495850L;

    private static final int DEFAULT_SUCCESS_CODE = 0;
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    /**
     * code
     */
    private Integer code;

    /**
     * msg
     */
    private String msg;

    /**
     * 内容
     */
    private T data;

    /**
     * error
     */
    private List<JSONObject> errors;

    /**
     * code
     * @return code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * message
     * @return message
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 内容
     * @return 内容
     */
    public T getData() {
        return data;
    }

    /**
     * 是否成功
     * @return 是否成功
     */
    public Boolean isSuccess() {
        return this.code == DEFAULT_SUCCESS_CODE;
    }

    /**
     * code
     * @param code code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * message
     * @param msg message
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 内容
     * @param data 内容
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * error
     * @return
     */
    public List<JSONObject> getErrors() {
        return errors;
    }

    /**
     * 参数校验error
     * @param errors
     */
    public void setErrors(List<JSONObject> errors) {
        this.errors = errors;
    }

    /**
     * construction
     */
    private WebResult(){
    }

    /**
     * 返回result 成功对象
     * @param data 数据
     * @param <T> T
     * @return WebResult
     */
    public static <T> WebResult<T> success(T data) {
        WebResult<T> result = new WebResult();
        result.setCode(DEFAULT_SUCCESS_CODE);
        result.setMsg(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
//        result.setError(new ArrayList<>());
        return result;
    }

    /**
     * 构造failure result 对象
     * @param code code
     * @param message message
     * @param data data
     * @param <T> T
     * @return WebResult
     */
    public static <T> WebResult<T> failure(int code, String message, T data) {
        WebResult<T> result = new WebResult();
        result.setCode(code);
        result.setData(data);
        result.setMsg(message);
//        result.setError(new ArrayList<>());
        return result;
    }

    /**
     * 构造failure result 对象
     * @param code code
     * @param message message
     * @return WebResult
     */
    public static <T> WebResult<T> failure(int code, String message) {
        WebResult<T> result = new WebResult();
        result.setCode(code);
        result.setMsg(message);
        result.setData(null);
//        result.setError(new ArrayList<>());
        return result;
    }

    /**
     * 构造failure result 对象
     * @param code code
     * @param message message
     * @return WebResult
     */
    public static <T> WebResult<T> failure(int code, String message, Throwable detail) {
        WebResult<T> result = new WebResult();
        result.setCode(code);
        result.setMsg(message);
        result.setData(null);
//        result.setError(new ArrayList<>());
        return result;
    }

    /**
     * 构造failure result 对象
     * @param code code
     * @param message message
     * @param args T
     * @return WebResult
     */
    public static <T> WebResult<T> failure(int code, String message, List<JSONObject> args) {
        WebResult<T> result = new WebResult();
        result.setCode(code);
        result.setMsg(message);
        result.setData(null);
        if (args != null) {
            result.setErrors(args);
        }
        return result;
    }

    /**
     * 构造failure result 对象
     * @param code code
     * @param message message
     * @param args T
     * @return WebResult
     */
    public static <T> WebResult<T> failure(int code, String message, T data, List<JSONObject> args) {
        WebResult<T> result = new WebResult();
        result.setCode(code);
        result.setMsg(message);
        result.setData(data);
        if (args != null) {
            result.setErrors(args);
        }
        return result;
    }

    private String errorToString() {
        if (errors == null || errors.size() == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (JSONObject result : errors) {
            sb.append(result);
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "WebResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", error=" + errors +
                '}';
    }

}
