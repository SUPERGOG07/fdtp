package com.kawai.fdtp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    private Long id;

    private String name;

    private String detail;

    private String mainPicture;

    private Integer grade;

    private String address;

}
