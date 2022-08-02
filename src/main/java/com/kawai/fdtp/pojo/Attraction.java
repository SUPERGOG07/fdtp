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

    private Long id;

    private String name;

    private String detail;

    private String mainPicture;

    private Integer grade;

    private String address;

}
