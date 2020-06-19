package com.st.dianping.controller;

import com.st.dianping.common.CommonError;
import com.st.dianping.common.CommonRes;
import com.st.dianping.dto.UserDto;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import com.st.dianping.repository.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ShaoTian
 * @date 2020/6/19 15:28
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDtoMapper userDtoMapper;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public CommonRes user(@RequestParam(value = "id") Integer id) throws SocketException {

        if (userDtoMapper.selectByPrimaryKey(id) == null) {
            throw new SocketException(new CommonError(ErrorEnum.NO_OBJECT_FOUND));
        }

        return new CommonRes(userDtoMapper.selectByPrimaryKey(id));
    }

}
