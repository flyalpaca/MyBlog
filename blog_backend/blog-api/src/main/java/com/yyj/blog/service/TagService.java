package com.yyj.blog.service;

import com.yyj.blog.vo.Result;
import com.yyj.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);
}
