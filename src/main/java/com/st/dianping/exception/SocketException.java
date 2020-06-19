package com.st.dianping.exception;

import com.st.dianping.common.CommonError;

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

    public CommonError getCommonError() {
        return commonError;
    }

    public void setCommonError(CommonError commonError) {
        this.commonError = commonError;
    }
}
