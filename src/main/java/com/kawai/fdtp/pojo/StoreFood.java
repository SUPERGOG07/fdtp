package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("store_food")
public class StoreFood {

    private String id;
    //美食
    private String name;
    //店铺
    private String target;

    private String url;

    private String price;

    public static StoreFood defaultConstruct(){
        return new StoreFood("1","1","1","1","1");
    }
}
