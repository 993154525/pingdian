package com.st.dianping.service.impl;

import com.st.dianping.common.CommonError;
import com.st.dianping.dto.CategoryDto;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import com.st.dianping.repository.CategoryDtoMapper;
import com.st.dianping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ShaoTian
 * @date 2020/6/24 10:52
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDtoMapper categoryDtoMapper;

    @Override
    public List<CategoryDto> selectAllCategory() {
        return categoryDtoMapper.selectAllCategory();
    }

    @Override
    public CategoryDto get(Integer id) {
        return categoryDtoMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public CategoryDto create(CategoryDto categoryDto) throws SocketException {

        try {
            categoryDtoMapper.insertSelective(categoryDto);
        } catch (Exception e) {
            throw new SocketException(new CommonError(ErrorEnum.CATEGORY_FAIL_MISS));
        }

        return get(categoryDto.getId());
    }

    @Override
    public Integer countAllCategory() {
        return categoryDtoMapper.countAllCategory();
    }
}
