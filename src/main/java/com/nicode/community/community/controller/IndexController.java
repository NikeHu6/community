package com.nicode.community.community.controller;

import com.nicode.community.community.dto.PaginationDTO;
import com.nicode.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletResponse response, Model model,
        @RequestParam(name="page",defaultValue = "1") Integer page,
        @RequestParam(name="size",defaultValue = "6") Integer size) {

        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
