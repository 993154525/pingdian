package com.st.dianping.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.st.dianping.aop.AdminPermission;
import com.st.dianping.common.CommonError;
import com.st.dianping.common.PageQuery;
import com.st.dianping.dto.ShopCreateReq;
import com.st.dianping.dto.ShopDto;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import com.st.dianping.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author ShaoTian
 * @date 2020/6/24 15:47
 */
@Controller
@RequestMapping("/admin/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPage(), pageQuery.getSize());

        PageInfo<ShopDto> shopDtoPageInfo = new PageInfo<>(shopService.selectAllShop());

        ModelAndView modelAndView = new ModelAndView("/admin/shop/index.html");
        modelAndView.addObject("data", shopDtoPageInfo);
        modelAndView.addObject("CONTROLLER_NAME", "shop");
        modelAndView.addObject("ACTION_NAME", "index");

        return modelAndView;
    }

    @RequestMapping("/createPage")
    @AdminPermission
    public ModelAndView createPage() {
        ModelAndView modelAndView = new ModelAndView("/admin/shop/create.html");
        modelAndView.addObject("CONTROLLER_NAME", "shop");
        modelAndView.addObject("ACTION_NAME", "create");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @AdminPermission
    public String create(@Valid ShopCreateReq shopCreateReq, BindingResult bindingResult) throws SocketException {
        if (bindingResult.hasErrors()) {
            throw new SocketException(new CommonError(ErrorEnum.PARAMETER_VALIDATION_ERROR));
        }

        ShopDto shopDto = new ShopDto();
        shopDto.setName(shopCreateReq.getName());
        shopDto.setIconUrl(shopCreateReq.getIconUrl());
        shopDto.setPricePerMan(shopCreateReq.getPricePerMan());
        shopDto.setLatitude(shopCreateReq.getLatitude());
        shopDto.setLongitude(shopCreateReq.getLongitude());
        shopDto.setSellerId(shopCreateReq.getSellerId());
        shopDto.setCategoryId(shopCreateReq.getCategoryId());
        shopDto.setAddress(shopCreateReq.getAddress());
        shopDto.setTags(shopCreateReq.getTags());
        shopDto.setStartTime(shopCreateReq.getStartTime());
        shopDto.setEndTime(shopCreateReq.getEndTime());

        shopService.create(shopDto);
        return "redirect:/admin/shop/index";
    }

}
