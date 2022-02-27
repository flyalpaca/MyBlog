package com.yyj.blog.service;

import com.yyj.blog.dao.pojo.SysUser;
import com.yyj.blog.vo.Result;
import com.yyj.blog.vo.params.LoginParams;

public interface LoginService {
    /**
     * 登录功能
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);
}
