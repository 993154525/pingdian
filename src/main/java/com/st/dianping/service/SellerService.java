package com.st.dianping.service;

import com.st.dianping.dto.SellerDto;
import com.st.dianping.exception.SocketException;

import java.util.List;

/**
 * @author ShaoTian
 * @date 2020/6/23 14:33
 */
public interface SellerService {

    SellerDto create(SellerDto sellerDto);

    SellerDto get(Integer id);

    List<SellerDto> selectSellerAll();

    SellerDto changeStatus(Integer id, Integer disabledFlag) throws SocketException;

}
