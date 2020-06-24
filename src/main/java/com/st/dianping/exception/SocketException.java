package com.st.dianping.exception;

import com.st.dianping.common.CommonError;
import com.st.dianping.eu.ErrorEnum;

/**
 * @author ShaoTian
 * @date 2020/6/19 17:38
 */
public class SocketException extends Exception {

    private CommonError commonError;

    public SocketException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    public SocketException(ErrorEnum errorEnum, String msg) {
        super();
        this.commonError = new CommonError(errorEnum);
        this.commonError.setMsg(msg);
    }

    public CommonError getCommonError() {
        return commonError;
    }

    public void setCommonError(CommonError commonError) {
        this.commonError = commonError;
    }
}
