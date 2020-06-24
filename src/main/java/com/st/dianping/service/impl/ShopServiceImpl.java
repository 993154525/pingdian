package com.st.dianping.service.impl;

import com.st.dianping.dto.CategoryDto;
import com.st.dianping.dto.SellerDto;
import com.st.dianping.dto.ShopDto;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import com.st.dianping.repository.ShopDtoMapper;
import com.st.dianping.service.CategoryService;
import com.st.dianping.service.SellerService;
import com.st.dianping.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ShaoTian
 * @date 2020/6/24 15:22
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDtoMapper shopDtoMapper;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public ShopDto create(ShopDto shopDto) throws SocketException {
        SellerDto sellerDto = sellerService.get(shopDto.getSellerId());

        if (sellerDto == null) {
            throw new SocketException(ErrorEnum.PARAMETER_VALIDATION_ERROR, "商户不存在");
        }

        if (sellerDto.getDisabledFlag() == 1) {
            throw new SocketException(ErrorEnum.PARAMETER_VALIDATION_ERROR, "商户已被禁用");
        }

        CategoryDto categoryDto = categoryService.get(shopDto.getCategoryId());
        if (categoryDto == null) {
            throw new SocketException(ErrorEnum.PARAMETER_VALIDATION_ERROR, "品类不存在");
        }
        shopDtoMapper.insertSelective(shopDto);

        return get(shopDto.getId());
    }

    @Override
    public ShopDto get(Integer id) {
        ShopDto shopDto = shopDtoMapper.selectByPrimaryKey(id);
        shopDto.setCategoryDto(categoryService.get(shopDto.getCategoryId()));
        shopDto.setSellerDto(sellerService.get(shopDto.getSellerId()));

        return shopDto;
    }

    @Override
    public List<ShopDto> selectAllShop() {
        List<ShopDto> shopDtos = shopDtoMapper.selectAllShop();
        shopDtos.forEach(shopDto -> {
            shopDto.setCategoryDto(categoryService.get(shopDto.getCategoryId()));
            shopDto.setSellerDto(sellerService.get(shopDto.getSellerId()));
        });

        return shopDtos;
    }

    @Override
    public Integer countAllShop() {
        return shopDtoMapper.countAllShop();
    }
}
