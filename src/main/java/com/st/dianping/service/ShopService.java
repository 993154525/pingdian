package com.st.dianping.service;

import com.st.dianping.dto.ShopDto;
import com.st.dianping.exception.SocketException;

import java.util.List;

/**
 * @author ShaoTian
 * @date 2020/6/24 15:21
 */
public interface ShopService {

    ShopDto create(ShopDto shopDto) throws SocketException;

    ShopDto get(Integer id);

    List<ShopDto> selectAllShop();

    Integer countAllShop();
}
