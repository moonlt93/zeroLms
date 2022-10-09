package com.zerobase.zerolms.course.model;

import com.zerobase.zerolms.admin.model.CommonParam;
import lombok.Data;

@Data
public class TakeCourseParam extends CommonParam {

long id;
String status;
String userId;

    long searchCourseId;

}
