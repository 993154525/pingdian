package com.st.dianping.common;

/**
 * @author ShaoTian
 * @date 2020/6/19 15:43
 */
public class CommonRes {
    public String msg;

    public Object data;

    public CommonRes() {
    }

    public CommonRes(Object data) {
        this.msg = "success";
        this.data = data;
    }

    public CommonRes(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonRes{" +
            ", msg='" + msg + '\'' +
            ", data=" + data +
            '}';
    }
}
