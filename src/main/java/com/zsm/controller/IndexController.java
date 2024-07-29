package com.zsm.controller;

import com.zsm.bean.Article;
import com.zsm.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author : ZSM
 * Time :  2024/06/28
 */
@Controller
public class IndexController {
    @Autowired
    private ArticleService articleService;

    @GetMapping({"/", "/note"})
    public String note(ModelMap modelMap) {
        List<Article> articles = getAll("note");
        modelMap.addAttribute("datas", articles);
        return "index";
    }

    @GetMapping("/project")
    public String project(ModelMap modelMap) {
        List<Article> projects = getAll("project");
        modelMap.addAttribute("datas", projects);
        return "index";
    }

    @GetMapping({"/management", "/management/note"})
    public String backstageNote(ModelMap modelMap) {
        List<Article> articles = getAll("note");
        modelMap.addAttribute("datas", articles);
        modelMap.addAttribute("currentLink", "note");
        return "management";
    }

    @GetMapping("/management/project")
    public String backstageProject(ModelMap modelMap) {
        List<Article> articles = getAll("project");
        modelMap.addAttribute("datas", articles);
        modelMap.addAttribute("currentLink", "project");
        return "management";
    }

    @GetMapping("/list/{type}")
    @ResponseBody
    public List<Article> getAll(@PathVariable("type") String type) {
        return articleService.getAll(type);
    }

    @GetMapping("/detail/{id}")
    public String getById(@PathVariable("id") Integer id, ModelMap modelMap) {
        Article article = articleService.getById(id);
        modelMap.addAttribute("article", article);
        return "detail";
    }
}
