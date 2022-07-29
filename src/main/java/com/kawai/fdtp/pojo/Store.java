package com.kawai.fdtp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    private Long id;

    private String name;

    private String detail;

    private String mainPicture;

    private String connect;

    private String address;

    private Integer grade;

}
