package com.yyj.blog.controller;

import com.yyj.blog.service.ArticleService;
import com.yyj.blog.vo.Result;
import com.yyj.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//json数据交互
@RestController
@RequestMapping("/articles")
public class ArticleController {
    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @Autowired
    private ArticleService articleService;
    @PostMapping
    public Result ListArticle(@RequestBody PageParams pageParams){

        return articleService.listArticle(pageParams);
    }

    /**
     * 首页最热文章
     * @return
     */
    @PostMapping("/hot")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页最新文章
     * @return
     */
    @PostMapping("/new")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页最新文章
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives(){

        return articleService.listArchives();
    }
}
