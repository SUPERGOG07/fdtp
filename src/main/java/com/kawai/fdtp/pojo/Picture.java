package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("picture")
public class Picture {

    private Long id;

    private Integer type;

    private Long target;

    private String url;


}
