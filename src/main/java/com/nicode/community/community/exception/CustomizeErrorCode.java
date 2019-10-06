package com.nicode.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题不存在，要不换个问题试试！！！"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复！！！"),
    NO_LOGIN(2003,"当前操作需要登陆，请登陆后重试！！！"),
    SYSTEM_ERROR(2004,"网页醉了，在光滑的地板上摩擦摩擦不见了！！！"),
    TYPE_PARAM_WRONG(2005,"评论类型不存在！！！"),
    COMMENT_NOT_FOUND(2006,"你操作的评论不存在！！！"),
    COMMENT_IS_EMPTY(2007,"评论内容不能为空！！！");



    private Integer code;
    private String message;
    CustomizeErrorCode(Integer code,String message){

        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
