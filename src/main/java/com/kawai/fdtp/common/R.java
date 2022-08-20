package com.kawai.fdtp.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {

    private Integer code;

    private String msg;

    private T data;

    private Map<String,Object> map = new HashMap<>();


    public static <T> R<T> success(T object){
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        r.msg = "success";
        return r;
    }

    public static <T> R<T> success(String msg,T object){
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        r.msg = msg;
        return r;
    }

    public static <T> R<T> error(String msg,T object){
        R<T> r = new R<>();
        r.code = 0;
        r.msg = msg;
        r.data = object;

        return r;
    }

    public static <T> R<T> error(T object){
        R<T> r = new R<>();
        r.code = 0;
        r.msg = "error";
        r.data = object;
        return r;
    }

    public static R<String> noAuth(String msg){
        R<String> r = new R<>();
        r.code = 403;
        r.msg = msg;
        r.data = "";

        return r;
    }

    public R<T> add(String key,Object value){
        this.map.put(key,value);
        return this;
    }

}
