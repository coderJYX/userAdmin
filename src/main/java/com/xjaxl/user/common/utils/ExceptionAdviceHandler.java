package com.xjaxl.user.common.utils;

import cn.hutool.core.util.StrUtil;

import com.xjaxl.user.common.exceptions.BizException;
import com.xjaxl.user.common.result.ResponseEnum;
import com.xjaxl.user.common.result.ResponseEnumInterface;
import com.xjaxl.user.common.result.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestControllerAdvice
public class ExceptionAdviceHandler {

    @ExceptionHandler({BindException.class})
    public ResultWrapper badParam(BindException e) {
        List<String> resultMsg = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(x -> resultMsg.add(x.getDefaultMessage()));
        return ResultWrapper.error(ResponseEnum.ERROR_REQUEST_BODY).setMsg(String.join(",", resultMsg));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultWrapper methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> resultMsg = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(x -> resultMsg.add(x.getDefaultMessage()));
        return ResultWrapper.error(ResponseEnum.ERROR_REQUEST_BODY).setMsg(String.join(",", resultMsg));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultWrapper badRequest(Exception e) {
        String format = String.format("请求参数有误，%s", e.getMessage());
        return ResultWrapper.error(ResponseEnum.ERROR_REQUEST_BODY).setMsg(format);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResultWrapper methodNotAllowed(Exception e) {
        return ResultWrapper.error(ResponseEnum.ERROR_REQUEST_METHOD);
    }

    @ExceptionHandler({SQLException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultWrapper sqlException(Exception e) {
        log.error("Sql语法错误", e);
        return ResultWrapper.error((ResponseEnumInterface)ResponseEnum.BAD_SQL);
    }

    @ExceptionHandler({BizException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResultWrapper bizException(BizException e) {
        log.error("{}", e.getMessage());
        int code;
        if ((code = e.getCode()) == 0)
            code = 9999;
        return ResultWrapper.error(code, e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultWrapper exception(Exception e) {
        log.error("错误", e);
        return ResultWrapper.error(ResponseEnum.INTERNAL_SERVER_ERROR).setMsg(e.getMessage());
    }


}
