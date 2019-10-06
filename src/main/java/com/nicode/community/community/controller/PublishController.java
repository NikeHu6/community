package com.nicode.community.community.controller;

import com.nicode.community.community.cache.TagCache;
import com.nicode.community.community.dto.QuestionDTO;
import com.nicode.community.community.mapper.QuestionMapper;
import com.nicode.community.community.model.Question;
import com.nicode.community.community.model.User;
import com.nicode.community.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id") Long id,
                       Model model){
        QuestionDTO question = questionService.getByID(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getDescription());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.getCache());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.getCache());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(name="title",required = false) String title,
            @RequestParam(name="description",required = false) String description,
            @RequestParam(name="tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Long id,
            HttpServletRequest request,
            Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.getCache());

        if (title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description == ""){
            model.addAttribute("error","问题描述不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error","用户未登陆");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNoneBlank(invalid)){
            model.addAttribute("error","输入非法标签："+invalid);
            return "publish";
        }
        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());

        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
