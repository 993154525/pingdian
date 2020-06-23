package com.st.dianping.service.impl;

import com.st.dianping.common.CommonError;
import com.st.dianping.dto.SellerDto;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import com.st.dianping.repository.SellerDtoMapper;
import com.st.dianping.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ShaoTian
 * @date 2020/6/23 14:40
 */
@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerDtoMapper sellerDtoMapper;

    @Override
    public SellerDto create(SellerDto sellerDto) {
        sellerDto.setDisabledFlag(0);
        sellerDto.setRemarkScore(new BigDecimal(0));

        sellerDtoMapper.insertSelective(sellerDto);

        return get(sellerDto.getId());
    }

    @Override
    public SellerDto get(Integer id) {
        return sellerDtoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SellerDto> selectSellerAll() {
        return sellerDtoMapper.selectSellerAll();
    }

    @Override
    public SellerDto changeStatus(Integer id, Integer disabledFlag) throws SocketException {
        SellerDto sellerDto = get(id);
        if (sellerDto == null) {
            throw new SocketException(new CommonError(ErrorEnum.PARAMETER_VALIDATION_ERROR));
        }

        sellerDto.setDisabledFlag(disabledFlag);
        sellerDtoMapper.updateByPrimaryKeySelective(sellerDto);

        return sellerDto;
    }

}
