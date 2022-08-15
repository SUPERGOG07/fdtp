package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("collect")
public class Collect {

    private String id;

    private String userName;

    private Integer type;

    private String target;

    private Long time;

    public static Collect defaultConstruct(){
        return new Collect("1","1",1,"1",System.currentTimeMillis());
    }
}
