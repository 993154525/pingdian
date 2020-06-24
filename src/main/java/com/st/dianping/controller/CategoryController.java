package com.st.dianping.controller;

import com.st.dianping.common.CommonRes;
import com.st.dianping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ShaoTian
 * @date 2020/6/24 11:24
 */
@Controller("/category")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonRes list() {

        return new CommonRes(categoryService.selectAllCategory());
    }

}
