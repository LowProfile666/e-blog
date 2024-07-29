package com.zsm.mapper;

import com.zsm.bean.Article;

import java.util.List;

/**
 * Author : ZSM
 * Time :  2024/06/28
 */
public interface ArticleMapper {
    Article getById(Integer id);
    List<Article> getAll(String type);
    Integer delete(Integer id);
    Integer add(Article article);

    Integer edit(Article article);
}
