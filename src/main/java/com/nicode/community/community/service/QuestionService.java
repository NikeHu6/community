package com.nicode.community.community.service;

import com.nicode.community.community.dto.PaginationDTO;
import com.nicode.community.community.dto.QuestionDTO;
import com.nicode.community.community.mapper.QuestionMapper;
import com.nicode.community.community.mapper.UserMapper;
import com.nicode.community.community.model.Question;
import com.nicode.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }

    public PaginationDTO list(Integer page, Integer size) {
        Integer totalCount = questionMapper.count();
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
        List<Question> questionList = questionMapper.list(offset,size);
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

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer totalCount = questionMapper.countByUserId(userId);
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
        List<Question> questionList = questionMapper.listByUserId(userId,offset,size);
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

    public QuestionDTO getByID(Integer id) {

        Question question = questionMapper.findById(id);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

}
