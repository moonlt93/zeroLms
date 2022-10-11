package com.zerobase.zerolms.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable=false)
    private Long id;

    String bannerName;
    String bannerFile; //fileName
    String bannerFileLink;
    String bannerLink; //url
    String bannerStatus;
    String sortValues;
    boolean bannerYn;
    LocalDateTime regDt;


}
