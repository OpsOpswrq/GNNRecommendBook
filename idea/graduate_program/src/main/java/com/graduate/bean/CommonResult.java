package com.graduate.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {
    private int code;
    private String msg;
    private Object data;
    public CommonResult(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}