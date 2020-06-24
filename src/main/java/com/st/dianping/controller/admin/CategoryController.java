package com.st.dianping.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.st.dianping.aop.AdminPermission;
import com.st.dianping.common.CommonError;
import com.st.dianping.common.PageQuery;
import com.st.dianping.dto.CategoryDto;
import com.st.dianping.dto.CategoryDtoRes;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import com.st.dianping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author ShaoTian
 * @date 2020/6/24 11:00
 */
@Controller("/admin/category")
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPage(), pageQuery.getSize());

        PageInfo<CategoryDto> categoryDtoPageInfo = new PageInfo<>(categoryService.selectAllCategory());

        ModelAndView modelAndView = new ModelAndView("/admin/category/index.html");
        modelAndView.addObject("data", categoryDtoPageInfo);
        modelAndView.addObject("CONTROLLER_NAME", "category");
        modelAndView.addObject("ACTION_NAME", "index");

        return modelAndView;
    }

    @RequestMapping("/createPage")
    @AdminPermission
    public ModelAndView createPage() {
        ModelAndView modelAndView = new ModelAndView("/admin/category/create.html");
        modelAndView.addObject("CONTROLLER_NAME", "category");
        modelAndView.addObject("ACTION_NAME", "create");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @AdminPermission
    public String create(@Valid CategoryDtoRes categoryDtoRes, BindingResult bindingResult) throws SocketException {
        if (bindingResult.hasErrors()) {
            throw new SocketException(new CommonError(ErrorEnum.PARAMETER_VALIDATION_ERROR));
        }

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(categoryDtoRes.getName());
        categoryDto.setIconUrl(categoryDtoRes.getIconUrl());
        categoryDto.setSort(categoryDtoRes.getSort());

        categoryService.create(categoryDto);
        return "redirect:/admin/category/index";
    }

}
