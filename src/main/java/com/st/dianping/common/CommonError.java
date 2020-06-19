package com.st.dianping.common;

import com.st.dianping.eu.ErrorEnum;

/**
 * @author ShaoTian
 * @date 2020/6/19 15:50
 */
public class CommonError {

    public Integer code;

    public String msg;

    public CommonError() {
    }

    public CommonError(ErrorEnum errorEnum) {
        this.code = errorEnum.errCode;
        this.msg = errorEnum.errMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
