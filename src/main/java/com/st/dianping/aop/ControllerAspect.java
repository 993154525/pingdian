package com.st.dianping.aop;

import com.st.dianping.common.CommonError;
import com.st.dianping.common.CommonRes;
import com.st.dianping.controller.admin.AdminController;
import com.st.dianping.eu.ErrorEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author ShaoTian
 * @date 2020/6/22 17:30
 */
@Aspect
@Configuration
public class ControllerAspect {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Around("execution(* com.st.dianping.controller.admin.*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object adminControllerBeforeValidation(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        AdminPermission adminPermission = method.getAnnotation(AdminPermission.class);

        if (adminPermission == null) {
            //公共方法
            Object resultObject = joinPoint.proceed();
            return resultObject;
        }
        String email = (String) httpServletRequest.getSession().getAttribute(AdminController.ADMIN_SESSION_CURRENT);
        if (email == null) {
            if (adminPermission.produceType().equals("text/html")) {
                httpServletResponse.sendRedirect("/admin/admin/loginPage");
                return null;
            } else {
                return new CommonRes("fail", new CommonError(ErrorEnum.ADMIN_LOGIN_MISS));
            }
        } else {
            Object resultObject = joinPoint.proceed();
            return resultObject;
        }
    }

}
