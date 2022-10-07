package com.zerobase.zerolms.common.model;

import lombok.Data;

@Data
public class ResponseResultHeader {
    boolean result;
    String msg;

    public ResponseResultHeader(boolean result, String message) {
        this.result= result;
        this.msg= message;
    }

    public ResponseResultHeader(boolean result) {
        this.result=result;
    }
}
