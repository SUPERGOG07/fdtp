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

    private Long id;

    private String userName;

    private Integer type;

    private Long target;

    private Long time;
}
