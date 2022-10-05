package com.zerobase.zerolms.admin.controller;

import com.zerobase.zerolms.admin.dto.CategoryDto;
import com.zerobase.zerolms.admin.model.CategoryInput;
import com.zerobase.zerolms.admin.model.MemberParam;
import com.zerobase.zerolms.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin/category/list")
    public String Categorylist(Model model, MemberParam param){

        List<CategoryDto> list= categoryService.selectList();

       model.addAttribute("list",list);
        return"admin/category/categoryList";
    }

    @PostMapping("/admin/category/add")
    public String add(Model model, CategoryInput param){


      boolean result = categoryService.add(param.getCategoryName());


            return"redirect:/admin/category/list";

    }
}
