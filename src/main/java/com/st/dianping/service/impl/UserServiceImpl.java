package com.st.dianping.service.impl;

import com.st.dianping.common.CommonError;
import com.st.dianping.dto.UserDto;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import com.st.dianping.repository.UserDtoMapper;
import com.st.dianping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ShaoTian
 * @date 2020/6/20 11:52
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Override
    public UserDto getUser(Integer id) {
        return userDtoMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserDto registerUser(UserDto registerDto) throws SocketException {

        try {
            registerDto.setPassword(Md5(registerDto.getPassword()));
            userDtoMapper.insertSelective(registerDto);
        } catch (Exception e) {
            throw new SocketException(new CommonError(ErrorEnum.REGISTER_DUP_FAIL));
        }

        return getUser(registerDto.getId());

    }

    @Override
    public UserDto LoginUser(String telPhone, String password) throws SocketException, NoSuchAlgorithmException {
        UserDto userDto = userDtoMapper.selectByTelPhoneAndPassword(telPhone, Md5(password));

        if (userDto == null) {
            throw new SocketException(new CommonError(ErrorEnum.Login_FAIL));
        }

        return userDto;
    }

    public static String Md5(String s) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(messageDigest.digest(s.getBytes()));
    }
}
