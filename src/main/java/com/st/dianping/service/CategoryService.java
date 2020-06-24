package com.st.dianping.service;

import com.st.dianping.dto.CategoryDto;
import com.st.dianping.exception.SocketException;

import java.util.List;

/**
 * @author ShaoTian
 * @date 2020/6/24 10:51
 */
public interface CategoryService {

    List<CategoryDto> selectAllCategory();

    CategoryDto get(Integer id);

    CategoryDto create(CategoryDto categoryDto) throws SocketException;

    Integer countAllCategory();

}
