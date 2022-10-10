package com.zerobase.zerolms.course.entity;

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
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    String imagePath;
    String keyword;
    String subject;
    long categoryId;

    @Column(length=1000)
    String summary;

    @Lob
    String contents;
    long price;
    long salePrice;
    LocalDate saleEndDt;
    LocalDateTime regDt; //등록일
    LocalDateTime udtDt;// 수정일

    String fileName;
    String urlFileName;



}
