package com.zerobase.zerolms.course.entity;

import com.zerobase.zerolms.admin.model.CommonParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class TakeCourse implements TakeCourseCode  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    long courseId;
    String userId;

    long payPrice;
    String status; //상태(수강신청, 결제완료 , 수강취소 )


    LocalDateTime regDt; //등록일


}
