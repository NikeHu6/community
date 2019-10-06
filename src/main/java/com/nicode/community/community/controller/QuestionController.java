package com.nicode.community.community.controller;

import com.nicode.community.community.dto.CommentDTO;
import com.nicode.community.community.dto.QuestionDTO;
import com.nicode.community.community.enums.CommentTypeEnum;
import com.nicode.community.community.service.CommentService;
import com.nicode.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id,
        Model model) {
        QuestionDTO questionDTO = questionService.getByID(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        questionService.incView(id);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
