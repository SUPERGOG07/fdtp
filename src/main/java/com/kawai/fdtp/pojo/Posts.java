package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("posts")
public class Posts {

    private String id;

    private String userName;

    private String title;

    private String detail;

    private Integer grade;

    private Integer collect;

    private Long time;

    private String picture;

    // 0 表示 未 , 1 表示 过 , 2 表示 不过
    private Integer isCheck;

    public static Posts defaultConstruct(){
        return new Posts("1","1","1","1",1,1,1L,"1",2);
    }

}
