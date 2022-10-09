package com.zerobase.zerolms.course.dto;

import com.zerobase.zerolms.course.entity.TakeCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TakeCourseDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    long courseId;
    String userId;

    long payPrice;
    String status; //상태(수강신청, 결제완료 , 수강취소 )

    Long totalCount;
    long seq;


    LocalDateTime regDt; //등록일

    String userName;
    String phone;
    String subject;

    public String getRegDtText(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd MM:mm");
        return regDt != null ? regDt.format(formatter) : "";
    }

    public static TakeCourseDto of(TakeCourse x){

        return TakeCourseDto.builder()
                .id(x.getId())
                .courseId(x.getCourseId())
                .userId(x.getUserId())
                .payPrice(x.getPayPrice())
                .status(x.getStatus())
                .regDt(x.getRegDt())
                .build();
    }

}
