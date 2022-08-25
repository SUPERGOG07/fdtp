package com.kawai.fdtp.pojo;














import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Attr;

import java.util.List;

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

    public static void check(Attraction attraction){
        if (attraction.getDetail()==null){
            attraction.setDetail("unknown");
        }
        if (attraction.getAddress()==null){
            attraction.setAddress("unknown");
        }
    }

    public static void check(List<Attraction> attractions){
        if(!attractions.isEmpty()){
            attractions.forEach(Attraction::check);
        }
    }
}
