package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("food")
public class Food {

    private String id;

    private String name;

    private String detail;

    private String mainPicture;

    private Integer grade;

    private String address;
    //美食历史
    private String history;
    //美食做法
    private String practice;
    //配料
    private String ingredients;

    // 0 表示 未 , 1 表示 过 , 2 表示 不过
    private Integer isCheck;

    public static Food defaultConstruct(){
        return new Food("1","1","1","1",1,"1","1","1","1",2);
    }
}
