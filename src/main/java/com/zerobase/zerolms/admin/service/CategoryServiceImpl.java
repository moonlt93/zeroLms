package com.zerobase.zerolms.admin.service;


import com.zerobase.zerolms.admin.dto.CategoryDto;
import com.zerobase.zerolms.admin.entity.Category;
import com.zerobase.zerolms.admin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public List<CategoryDto> selectList() {
        List<Category> categories = categoryRepository.findAll();
        return CategoryDto.of(categories);
    }

    @Override
    public boolean add(String categoryName) {

        //카테고리 명 중복

       Category category= Category.builder()
               .categoryName(categoryName)
               .usingYn(true)
               .sortValue(0)

               .build();
        categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean update(CategoryDto parameter) {
        return false;
    }

    @Override
    public boolean del(long id) {
        return false;
    }
}
