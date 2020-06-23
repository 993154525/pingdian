package com.st.dianping.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.st.dianping.aop.AdminPermission;
import com.st.dianping.common.CommonError;
import com.st.dianping.common.CommonRes;
import com.st.dianping.common.PageQuery;
import com.st.dianping.dto.SellerCreateReq;
import com.st.dianping.dto.SellerDto;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import com.st.dianping.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author ShaoTian
 * @date 2020/6/23 14:44
 */
@Controller
@RequestMapping("/admin/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPage(), pageQuery.getSize());

        PageInfo<SellerDto> sellerDtoPageInfo = new PageInfo<>(sellerService.selectSellerAll());

        ModelAndView modelAndView = new ModelAndView("/admin/seller/index.html");
        modelAndView.addObject("data", sellerDtoPageInfo);
        modelAndView.addObject("CONTROLLER_NAME", "seller");
        modelAndView.addObject("ACTION_NAME", "index");

        return modelAndView;
    }

    @RequestMapping("/createPage")
    @AdminPermission
    public ModelAndView createPage() {
        ModelAndView modelAndView = new ModelAndView("/admin/seller/create.html");
        modelAndView.addObject("CONTROLLER_NAME", "seller");
        modelAndView.addObject("ACTION_NAME", "create");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @AdminPermission
    public String create(@Valid SellerCreateReq sellerCreateReq, BindingResult bindingResult) throws SocketException {
        if (bindingResult.hasErrors()) {
            throw new SocketException(new CommonError(ErrorEnum.PARAMETER_VALIDATION_ERROR));
        }

        SellerDto sellerDto = new SellerDto();
        sellerDto.setName(sellerCreateReq.getName());
        sellerService.create(sellerDto);

        return "redirect:/admin/seller/index";
    }

    @RequestMapping(value = "/up", method = RequestMethod.POST)
    @AdminPermission
    @ResponseBody
    public CommonRes up(@RequestParam(value = "id") Integer id) throws SocketException {
        SellerDto sellerDto = sellerService.changeStatus(id, 0);

        return new CommonRes(sellerDto);
    }

    @RequestMapping(value = "/down", method = RequestMethod.POST)
    @AdminPermission
    @ResponseBody
    public CommonRes down(@RequestParam(value = "id") Integer id) throws SocketException {
        SellerDto sellerDto = sellerService.changeStatus(id, 1);

        return new CommonRes(sellerDto);
    }

}
