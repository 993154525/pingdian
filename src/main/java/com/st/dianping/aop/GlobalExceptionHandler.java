package com.st.dianping.aop;

import com.st.dianping.common.CommonError;
import com.st.dianping.common.CommonRes;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ShaoTian
 * @date 2020/6/19 18:00
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonRes doError(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        if (ex instanceof SocketException) {
            return new CommonRes("fail", ((SocketException) ex).getCommonError());
        } else if (ex instanceof NoHandlerFoundException) {
            return new CommonRes("fail", new CommonError(ErrorEnum.NO_HANDLER_FOUND));
        } else if (ex instanceof ServletRequestBindingException) {
            return new CommonRes("fail", new CommonError(ErrorEnum.UNBIND_EXCEPTION_ERROR));
        } else {
            return new CommonRes("fail", ex.getMessage());
        }
    }

}
