package com.zerobase.zerolms.course.model;

import lombok.Data;

@Data
public class CourseInput {

   String Subject;
   String keyword;
   long categoryId;
   long Id;
   String contents;
   String summary;
   long price;
   long salePrice;
   String saleEndDtText;

   String idList;



}
