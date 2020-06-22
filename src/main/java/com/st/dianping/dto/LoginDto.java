package com.st.dianping.dto;

import javax.validation.constraints.NotBlank;

/**
 * Created by hzllb on 2019/7/13.
 */
public class LoginDto {
    @NotBlank(message = "手机号不能为空")
    private String telPhone;
    @NotBlank(message = "密码不能为空")
    private String password;

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
