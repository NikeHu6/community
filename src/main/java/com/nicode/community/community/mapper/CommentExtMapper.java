package com.nicode.community.community.mapper;

import com.nicode.community.community.model.Comment;
import com.nicode.community.community.model.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incComment(Comment comment);
}