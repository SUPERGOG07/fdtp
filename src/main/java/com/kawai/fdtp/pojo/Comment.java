package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment")
public class Comment {

    private String id;

    private String userName;

    /**
     * 0:store  1:food  2:attraction
     */
    private Integer type;

    private String word;

    private String target;

    private Integer level;

    private Integer grade;

    private Long time;

}
