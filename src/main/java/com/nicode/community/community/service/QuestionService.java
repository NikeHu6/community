package com.nicode.community.community.service;

import com.nicode.community.community.dto.PaginationDTO;
import com.nicode.community.community.dto.QuestionDTO;
import com.nicode.community.community.exception.CustomizeErrorCode;
import com.nicode.community.community.exception.CustomizeException;
import com.nicode.community.community.mapper.QuestionExtMapper;
import com.nicode.community.community.mapper.QuestionMapper;
import com.nicode.community.community.mapper.UserMapper;
import com.nicode.community.community.model.Question;
import com.nicode.community.community.model.QuestionExample;
import com.nicode.community.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            question.setCommentCount(0L);
            question.setViewCount(0L);
            question.setLikeCount(0L);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else{
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());

            Question record = new Question();
            record.setTitle(question.getTitle());
            record.setDescription(question.getDescription());
            record.setGmtModified(System.currentTimeMillis());
            record.setTag(question.getTag());
            int update = questionMapper.updateByExampleSelective(record, questionExample);
            if (update != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public PaginationDTO list(Integer page, Integer size) {
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        Integer totalPage = (totalCount+size-1) / size;
        if (page<1){
            page = 1;
        }
        if (page>totalPage){
            page = totalPage;
        }

        Integer offset = size*(page-1);
        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOS);


        paginationDTO.setPagination(totalPage,page,size);
        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(example);
        Integer totalPage = (totalCount+size-1) / size;
        if (page<1){
            page = 1;
        }
        if (page>totalPage){
            page = totalPage;
        }

        Integer offset = size*(page-1);
        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOS);


        paginationDTO.setPagination(totalPage,page,size);
        return paginationDTO;
    }

    public QuestionDTO getByID(Long id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void incView(Long id) {
        //累加问题的浏览次数
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1L);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexTag);
        List<Question> related = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = related.stream().map(q -> {
            QuestionDTO DTO = new QuestionDTO();
            BeanUtils.copyProperties(q,DTO);
            return DTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
