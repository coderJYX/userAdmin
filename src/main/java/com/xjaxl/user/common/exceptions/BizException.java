package com.xjaxl.user.common.exceptions;


import com.xjaxl.user.common.result.ResponseEnumInterface;

public class BizException extends RuntimeException {
    private static final long serialVersionUID = 3160241586346324994L;

    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public BizException() {}

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(ResponseEnumInterface responseEnum) {
        super(responseEnum.getMsg());
        this.code = responseEnum.getCode();
    }

    public BizException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

}
