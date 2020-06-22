package com.st.dianping.controller;

import com.st.dianping.common.BingingResultUtils;
import com.st.dianping.common.CommonError;
import com.st.dianping.common.CommonRes;
import com.st.dianping.dto.LoginDto;
import com.st.dianping.dto.RegisterDto;
import com.st.dianping.dto.UserDto;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import com.st.dianping.repository.UserDtoMapper;
import com.st.dianping.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

/**
 * @author ShaoTian
 * @date 2020/6/19 15:28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    public static final String CURRENT_USER_SEESION = "currentUserSession";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/index")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView("index.html");
        modelAndView.addObject("name", "ShaoTian");

        return modelAndView;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public CommonRes user(@RequestParam(value = "id") Integer id) throws SocketException {

        if (userDtoMapper.selectByPrimaryKey(id) == null) {
            throw new SocketException(new CommonError(ErrorEnum.NO_OBJECT_FOUND));
        }

        return new CommonRes(userService.getUser(id));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonRes register(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) throws SocketException, NoSuchAlgorithmException {

        if (bindingResult.hasErrors()) {
            return new CommonRes("fail", BingingResultUtils.processBinding(bindingResult));
        }

        UserDto userDto = new UserDto();

        userDto.setTelPhone(registerDto.getTelPhone());
        userDto.setPassword(registerDto.getPassword());
        userDto.setGender(registerDto.getGender());
        userDto.setNickName(registerDto.getNickName());

        UserDto registerUser = userService.registerUser(userDto);

        return new CommonRes(registerUser);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonRes login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) throws SocketException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            return new CommonRes("fail", BingingResultUtils.processBinding(bindingResult));
        }

        UserDto userDto = userService.LoginUser(loginDto.getTelPhone(), loginDto.getPassword());

        if (userDto == null) {
            return new CommonRes("fail", userDto);
        }
        httpServletRequest.getSession().setAttribute(CURRENT_USER_SEESION, userService.LoginUser(loginDto.getTelPhone(), loginDto.getPassword()));

        return new CommonRes(userDto);
    }

    @RequestMapping(value = "/logout")
    public CommonRes logout() {

        httpServletRequest.getSession().invalidate();

        return new CommonRes("success", (UserDto) httpServletRequest.getSession().getAttribute(CURRENT_USER_SEESION));
    }

    @RequestMapping(value = "/getCurrentUser")
    public CommonRes getCurrentUser() {
        String msg = "success";
        if ((UserDto) httpServletRequest.getSession().getAttribute(CURRENT_USER_SEESION) == null) {
            msg = "fail";
        }

        return new CommonRes(msg, (UserDto) httpServletRequest.getSession().getAttribute(CURRENT_USER_SEESION));
    }

}
