package com.yyj.blog.service;

import com.yyj.blog.dao.pojo.SysUser;
import com.yyj.blog.vo.Result;

public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token来查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

}
