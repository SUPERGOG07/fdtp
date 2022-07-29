package com.kawai.fdtp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    private String userName;

    private Integer flag;

    private Long objId;

}
