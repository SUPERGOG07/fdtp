package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("address")
public class Address {

    private String id;
    //目标类型
    private Integer type;
    //目标id
    private String target;
    //是否农村
    private Integer isCountry;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //省、自治区
    private String province;
    //市、直辖市
    private String city;
    //区、县
    private String area;
    //街道
    private String street;
    //路
    private String road;
    //镇、乡
    private String town;
    //村
    private String village;
    //详细地址
    private String detail;

    public static Address defaultConstruct(){
        return new Address("1",1,"1",1,"1","1",
                "1","1","1","1","1","1","1","1");
    }

}
