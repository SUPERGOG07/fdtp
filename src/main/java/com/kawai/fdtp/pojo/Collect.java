package com.kawai.fdtp.pojo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collect {

    private String userName;

    private Integer flag;

    private Long objId;

}
