package com.kawai.fdtp.dto;

import com.kawai.fdtp.pojo.Comment;
import lombok.Data;

@Data
public class CommentDto extends Comment {

    private String id;

    private String userName;

    /**
     * 0:store  1:food  2:attraction
     */
    private Integer type;

    private String word;

    private String target;

    private Integer level;

    private Integer grade;

    private Long time;

    private String head;
}
