package com.st.dianping.common;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author ShaoTian
 * @date 2020/6/20 14:57
 */
public class BingingResultUtils {

    public static String processBinding(BindingResult bindingResult) {

        StringBuilder stringBuilder = new StringBuilder();
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage() + ",");
            }
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        }

        return "未知错误";
    }

}
