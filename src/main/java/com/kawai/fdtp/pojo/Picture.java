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

    private String id;
    /**
     * 0:store  1:food  2:attraction 3:posts 4:comment
     */
    private Integer type;

    private String target;

    private String url;

    private Long time;

    public static Picture defaultConstruct(){
        return new Picture("1",1,"1","1",System.currentTimeMillis());
    }

}
