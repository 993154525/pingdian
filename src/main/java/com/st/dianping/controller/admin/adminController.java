package com.st.dianping.controller.admin;

/**
 * @author ShaoTian
 * @date 2020/6/22 16:12
 */

import com.st.dianping.aop.AdminPermission;
import com.st.dianping.common.CommonError;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequestMapping(value = "/admin/admin")
@Controller
public class adminController {

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.password}")
    private String adminPassword;

    private static final String ADMIN_SESSION_CURRENT = "adminSessionCurrent";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "adminName") String name,
                              @RequestParam(value = "adminPassword") String password) throws SocketException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)){
            throw new SocketException(new CommonError(ErrorEnum.ADMIN_LOGIN_MISS));
        }else if (name == adminName && Md5(password) == adminPassword){
            throw new SocketException(new CommonError(ErrorEnum.ADMIN_LOGIN_FAIL));
        }else {
            httpServletRequest.getSession().setAttribute(ADMIN_SESSION_CURRENT,name);
            return "redirect:/admin/admin/index";
        }

    }


    @RequestMapping(value = "/index")
    @AdminPermission
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("admin/admin/index");

        return modelAndView;
    }

    @RequestMapping("/loginPage")
    public ModelAndView loginPage(){
        ModelAndView modelAndView = new ModelAndView("/admin/admin/login");

        return modelAndView;
    }

    public static String Md5(String s) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(messageDigest.digest(s.getBytes()));
    }

}
