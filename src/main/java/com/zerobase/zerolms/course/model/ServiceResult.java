package com.zerobase.zerolms.course.model;


import lombok.Data;

@Data
public class ServiceResult {


    boolean result;
    String message;
        public ServiceResult(){
        this.result=true;
        }
    public ServiceResult(boolean b, String s) {
    this.result =b;
    this.message= s;
    }

    public ServiceResult(boolean b) {
    this.result =b;
    }
}
