package com.kawai.fdtp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("food")
public class Food {

    private String id;

    private String name;

    private String detail;

    private String mainPicture;

    private Integer grade;

    private String address;
    //美食历史
    private String history;
    //美食做法
    private String practice;
    //配料
    private String ingredients;

    // 0 表示 未 , 1 表示 过 , 2 表示 不过
    private Integer isCheck;

    public static Food defaultConstruct(){
        return new Food("1","1","1","1",1,"1","1","1","1",2);
    }

    public static void check(Food food){
        String unknown = "unknown";
        if (food.getDetail()==null){
            food.setDetail(unknown);
        }
        if (food.getMainPicture()==null){
            food.setMainPicture(unknown);
        }
        if (food.getAddress()==null){
            food.setAddress(unknown);
        }
        if (food.getHistory()==null){
            food.setHistory(unknown);
        }
        if (food.getPractice()==null){
            food.setPractice(unknown);
        }
        if (food.getIngredients()==null){
            food.setIngredients(unknown);
        }
        if (food.getGrade()==null){
            food.setGrade(0);
        }
        if (food.getIsCheck()==null){
            food.setIsCheck(0);
        }
    }

    public static void check(List<Food> foods){
        if(!foods.isEmpty()){
            foods.forEach(Food::check);
        }
    }
}
