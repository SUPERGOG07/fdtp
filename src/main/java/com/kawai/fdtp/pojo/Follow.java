package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("follow")
public class Follow {

    private String id;

    private String userName;

    private String myFollow;

    public static Follow defaultConstruct(){
        return new Follow("1","1","1");
    }

}
