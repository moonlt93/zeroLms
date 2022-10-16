package com.zerobase.zerolms.admin.service;

import com.zerobase.zerolms.admin.dto.CategoryDto;
import com.zerobase.zerolms.admin.model.CategoryInput;

import java.util.List;


public interface CategoryService {


    List<CategoryDto> selectList();


   /* 추가*/
    boolean add(String categoryName);

   /*수정*/
    boolean update(CategoryInput parameter);



    /*삭제*/
    boolean del(long id);
    
    /*front 정보*/
    List<CategoryDto> frontList(CategoryDto param);
    

}
