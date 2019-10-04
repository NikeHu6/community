package com.nicode.community.community.controller;

import com.nicode.community.community.dto.QuestionDTO;
import com.nicode.community.community.mapper.QuestionMapper;
import com.nicode.community.community.model.Question;
import com.nicode.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id,
        Model model) {
        QuestionDTO questionDTO = questionService.getByID(id);
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
