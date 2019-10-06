package com.nicode.community.community.enums;

import com.nicode.community.community.model.Comment;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);


    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
