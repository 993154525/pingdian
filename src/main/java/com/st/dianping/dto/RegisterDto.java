package com.st.dianping.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ShaoTian
 * @date 2020/6/20 11:51
 */
public class RegisterDto {

    @NotBlank(message = "手机号不能为空")
    private String telPhone;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @NotNull(message = "性别不能为空")
    private Integer gender;

    public RegisterDto() {
    }

    public RegisterDto(String telPhone, String password, String nickName, Integer gender) {
        this.telPhone = telPhone;
        this.password = password;
        this.nickName = nickName;
        this.gender = gender;
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "RegisterDto{" +
                "telPhone='" + telPhone + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender=" + gender +
                '}';
    }
}
