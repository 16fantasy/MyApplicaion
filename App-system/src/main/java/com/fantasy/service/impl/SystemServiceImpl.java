package com.fantasy.service.impl;

import com.fantasy.common.util.uuid.IdUtils;
import com.fantasy.config.TokenService;
import com.fantasy.config.redis.RedisCacheService;
import com.fantasy.entity.User;
import com.fantasy.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author jingc
 * @description SystemServiceImpl
 * @since 2022/2/21
 */

@Service
public class SystemServiceImpl implements SystemService {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;//认证管理器

    @Autowired
    private RedisCacheService cacheService;

    @Override
    public String login(User user) {

        String username = user.getUsername();
        String password = user.getPassword();

        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
//                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
////                throw new UserPasswordNotMatchException();
                new RuntimeException("账号和密码不必配！");
            }
            else
            {
//                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
//                throw new ServiceException(e.getMessage());
                throw e;
            }
        }
//        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
////        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
////        recordLoginInfo(loginUser.getUserId());

        User principal = (User) authentication.getPrincipal();

        return tokenService.createToken(principal);
    }
}
