package com.yyj.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.yyj.blog.dao.pojo.SysUser;
import com.yyj.blog.service.LoginService;
import com.yyj.blog.service.SysUserService;
import com.yyj.blog.util.JWTUtils;
import com.yyj.blog.vo.ErrorCode;
import com.yyj.blog.vo.Result;
import com.yyj.blog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private  static final String salt= "yyyj!@#123";
    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 检查参数是否合法
         * 根据用户名和密码去user表中查询是否存在
         * 如果不存在 登录失败
         * 如果存在,使用jwt生成token返回给前段
         * token放入redis当中, redis  token:user 信息 设置过期时间
         * (登录认证的时候,先认证token字符串是否合法,去redis认证是否存在)
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());

        }
        password = DigestUtils.md5Hex(password+salt);
        //System.out.println("密文(仅供测试):"+password);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }
}
