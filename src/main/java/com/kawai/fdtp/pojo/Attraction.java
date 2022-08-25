package com.kawai.fdtp.pojo;














import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("attraction")
public class Attraction {

    private String id;

    private String name;

    private String detail;

    private String mainPicture;

    private Integer grade;

    private String address;

    public static Attraction defaultConstruct(){
        return new Attraction("1","1","1","1",1,"1");
    }

}
