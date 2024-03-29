package com.nicode.community.community.dto;

import com.nicode.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Long commentCount;
    private Long ViewCount;
    private Long LikeCount;
    private String tag;
    private User user;
}
