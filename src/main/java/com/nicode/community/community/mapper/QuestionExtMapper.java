package com.nicode.community.community.mapper;

import com.nicode.community.community.model.Question;
import com.nicode.community.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question question);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION
     *
     * @mbg.generated Fri Oct 04 16:10:38 CST 2019
     */

}