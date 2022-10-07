package com.zerobase.zerolms.course.controller;

import com.zerobase.zerolms.util.PageUtil;

public class BaseController {

    public String getPagerHtml(long totalCount, long pageSize, long pageIndex,String  query ){

        PageUtil pageUtil =new PageUtil(totalCount, pageSize,pageIndex , query);

    return pageUtil.pager();

    }
}
