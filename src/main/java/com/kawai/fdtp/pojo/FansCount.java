package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("fans_count")
public class FansCount {

    private String userName;

    private String myFollow;

    private String myFans;

    public static FansCount defaultConstruct(){
        return new FansCount("1","1","1");
    }

}
