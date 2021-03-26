package com.st.dianping.controller;

import com.st.dianping.common.CommonError;
import com.st.dianping.common.CommonRes;
import com.st.dianping.dto.CategoryDto;
import com.st.dianping.dto.ShopDto;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import com.st.dianping.service.CategoryService;
import com.st.dianping.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shaotian
 * @create 2020-06-25 15:59
 */
@Controller("/shop")
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/recommend")
    @ResponseBody
    public CommonRes recommend(@RequestParam(value = "longitude") BigDecimal longitude,
                               @RequestParam(value = "latitude") BigDecimal latitude) throws SocketException {

        if (longitude == null || latitude == null) {
            throw new SocketException(new CommonError(ErrorEnum.PARAMETER_VALIDATION_ERROR));
        }


        return new CommonRes(shopService.recommendShop(longitude, latitude));
    }

    @RequestMapping("/search")
    @ResponseBody
    public CommonRes search(@RequestParam(value = "longitude") BigDecimal longitude,
                            @RequestParam(value = "latitude") BigDecimal latitude,
                            @RequestParam(value = "keyword") String keyword,
                            @RequestParam(value = "orderby", required = false) Integer orderby,
                            @RequestParam(value = "categoryId", required = false) Integer categoryId,
                            @RequestParam(value = "tags", required = false) String tags) throws IOException {


        List<ShopDto> shopDtoList = (List<ShopDto>) shopService.searchEs(longitude, latitude, keyword, categoryId, orderby, tags).get("shop");
        //List<ShopDto> shopDtoList = shopService.search(longitude, latitude, keyword, categoryId, orderby, tags);

        List<CategoryDto> categoryDtoList = categoryService.selectAllCategory();
        List<Map<String, Object>> groupByTags = shopService.searchGroupByTags(categoryId, keyword, tags);
        HashMap<String, Object> resMap = new HashMap<>();

        resMap.put("shop", shopDtoList);
        resMap.put("category", categoryDtoList);
        resMap.put("tags", groupByTags);

        return new CommonRes(resMap);

    }

}
