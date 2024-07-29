package com.zsm.service.impl;

import com.zsm.bean.Article;
import com.zsm.mapper.ArticleMapper;
import com.zsm.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author : ZSM
 * Time :  2024/06/28
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public Article getById(Integer id) {
        return articleMapper.getById(id);
    }

    @Override
    public List<Article> getAll(String type) {
        return articleMapper.getAll(type);
    }

    @Override
    public Integer delete(Integer id) {
        return articleMapper.delete(id);
    }

    @Override
    public Integer add(Article article) {
        if (!article.check()) return -1;
        return articleMapper.add(article);
    }

    @Override
    public Integer edit(Article article) {
        if (!article.check()) return -1;
        return articleMapper.edit(article);
    }
}
