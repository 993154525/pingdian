package com.st.dianping.service;

import com.st.dianping.dto.UserDto;
import com.st.dianping.exception.SocketException;

import java.security.NoSuchAlgorithmException;

/**
 * @author ShaoTian
 * @date 2020/6/20 11:50
 */
public interface UserService {

    UserDto getUser(Integer id);

    UserDto registerUser(UserDto registerDto) throws SocketException, NoSuchAlgorithmException;

    UserDto LoginUser(String telPhone,String password) throws SocketException, NoSuchAlgorithmException;

    Integer countAllUser();
}
