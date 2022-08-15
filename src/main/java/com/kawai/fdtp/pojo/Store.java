package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("store")
public class Store {

    private String id;

    private String name;

    private String detail;

    private String mainPicture;

    private String connect;

    private String address;

    private Integer grade;

    private String target;

    private String workTime;

    public static Store defaultConstruct(){
        return new Store("1","1","1","1","1","1",1,"1","1");
    }

}
