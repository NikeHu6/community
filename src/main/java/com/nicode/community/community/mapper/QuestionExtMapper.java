package com.nicode.community.community.mapper;

import com.nicode.community.community.model.Question;
import com.nicode.community.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question question);
    int incComment(Question question);
    List<Question> selectRelated(Question question);
}