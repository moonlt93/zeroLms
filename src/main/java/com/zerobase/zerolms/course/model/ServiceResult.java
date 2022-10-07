package com.zerobase.zerolms.course.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ServiceResult {


    boolean result;
    String message;
        public ServiceResult(){

        }
    public ServiceResult(boolean b, String s) {
    this.result =b;
    this.message= s;
    }

    public ServiceResult(boolean b) {
    this.result =b;
    }
}
