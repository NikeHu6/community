package com.nicode.community.community.dto;

import com.nicode.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer ViewCount;
    private Integer LikeCount;
    private String tag;
    private User user;
}
