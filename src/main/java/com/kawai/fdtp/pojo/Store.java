package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("store")
public class Store {

    private String id;

    private String name;

    private String detail;
    @TableField("main_picture")
    private String picture;
    //联系电话
    private String connect;

    private String address;

    private Integer grade;

    private String workTime;

    // 0 表示 未 , 1 表示 过 , 2 表示 不过
    private Integer isCheck;

    public static Store defaultConstruct(){
        return new Store("1","1","1","1","1","1",1,"1",2);
    }

    public static void check(Store store){
        if(store.getAddress()==null){
            store.setAddress("unknown address");
        }
        if (store.getPicture()==null){
            store.setPicture("unknown picture");
        }
        if(store.getWorkTime()==null){
            store.setWorkTime("unknown work time");
        }
    }

    public static void check(List<Store> stores){
        if (!stores.isEmpty()){
            stores.forEach(Store::check);
        }

    }
}
