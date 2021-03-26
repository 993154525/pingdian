package com.st.dianping.service;

import com.st.dianping.dto.ShopDto;
import com.st.dianping.exception.SocketException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author ShaoTian
 * @date 2020/6/24 15:21
 */
public interface ShopService {

    ShopDto create(ShopDto shopDto) throws SocketException;

    ShopDto get(Integer id);

    List<ShopDto> selectAllShop();

    List<ShopDto> recommendShop(BigDecimal longitude, BigDecimal latitude);

    List<ShopDto> search(BigDecimal longitude, BigDecimal latitude, String keyword, Integer categoryId, Integer pricePerMan, String tags);

    Map<String,Object> searchEs(BigDecimal longitude, BigDecimal latitude, String keyword, Integer categoryId, Integer pricePerMan, String tags) throws IOException;

    List<Map<String, Object>> searchGroupByTags(Integer categoryId, String keyword, String tags);

    Integer countAllShop();
}
