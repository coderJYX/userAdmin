package com.xjaxl.user.common.result;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回对象封装类
 *
 * @author JYX
 * @version 1.0.0
 * @date 2023-03-23
 */
@Setter
@Getter
public class ResultWrapper<T> {

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

    private ResultWrapper() {
        this.msg = "";
    }

    public static ResultWrapper success() {
        ResultWrapper resultWrapper = new ResultWrapper();
        resultWrapper.setCode(ResponseEnum.OK.getCode());
        resultWrapper.setSuccess(true);
        return resultWrapper;
    }

    public static <T> ResultWrapper<T> success(T data) {
        ResultWrapper<T> result = new ResultWrapper<>();
        result.setCode(ResponseEnum.OK.getCode());
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static <T> ResultWrapper<T> success(T data , String msg) {
        ResultWrapper<T> result = new ResultWrapper<>();
        result.setCode(ResponseEnum.OK.getCode());
        result.setData(data);
        result.setMsg(msg);
        result.setSuccess(true);
        return result;
    }

    public static ResultWrapper error(Integer code, String msg) {
        ResultWrapper res = new ResultWrapper();
        res.setCode(code);
        res.setMsg(msg);
        res.setSuccess(false);
        return res;
    }

    public static ResultWrapper error(ResponseEnumInterface responseEnum) {
        return error(responseEnum.getCode(), responseEnum.getMsg());
    }

    public ResultWrapper setMsg(String msg) {
        this.msg = msg;
        return this;
    }

}
