package com.zerobase.zerolms.course.model;

import com.zerobase.zerolms.admin.model.CommonParam;
import lombok.Data;

@Data
public class TakeCourseInput extends CommonParam {

 long courseId;
 String userId;

 long  takeCourseId;

}
