package com.st.dianping.eu;

/**
 * @author ShaoTian
 * @date 2020/6/19 16:16
 */
public enum ErrorEnum {

    NO_OBJECT_FOUND(10001, "对象不存在"),
    UNKNOWN_ERROR(10002, "未知的错误"),
    NO_HANDLER_FOUND(10003, "找不到执行的路径操作"),
    UNBIND_EXCEPTION_ERROR(10004, "请求参数错误"),
    PARAMETER_VALIDATION_ERROR(1005, "注册参数错误"),

    REGISTER_DUP_FAIL(20001, "用户已存在");

    public Integer errCode;

    public String errMsg;

    ErrorEnum(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
