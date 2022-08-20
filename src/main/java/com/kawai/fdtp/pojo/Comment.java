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
     * 0:store  1:food  2:attraction 3:posts 4:comment
     */
    private Integer type;

    private String word;

    private String target;

    private Integer level;

    private Integer grade;

    private Long time;
    // 0 表示 未 , 1 表示 过 , 2 表示 不过
    private Integer isCheck;

    public static Comment defaultConstruct(){
        return new Comment("1","1",1,"1","1",1,1,System.currentTimeMillis(),2);
    }

}
