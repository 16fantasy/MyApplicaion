package com.fantasy.config;

import com.fantasy.common.constant.Constants;
import com.fantasy.common.util.uuid.IdUtils;
import com.fantasy.config.redis.RedisCacheService;
import com.fantasy.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author jingc
 * @description TokenServie
 * @since 2022/2/21
 */

@Service
public class TokenService {

    @Value("${token.header}")
    private String AuthenticationHeader;

    @Value("${token.secret}")
    private String  secret;

    @Autowired
    private RedisCacheService cacheService;

    /**
     * @param user 用户信息
     * @return string token令牌
     */
    public String createToken(User user) {
        String token = IdUtils.fastUUID();
//        loginUser.setToken(token);
//        setUserAgent(loginUser);
//        refreshToken(loginUser);
        user.setTokenKey(token);
        String userKey = getTokenKey(user.getTokenKey());
        cacheService.setCacheObject(userKey, user, 200, TimeUnit.MINUTES);
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }


    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token)
    {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 从请求中获取token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(AuthenticationHeader);
        String token = "";
        if (StringUtils.isNotBlank(header) && header.startsWith(Constants.TOKEN_PREFIX)) {
            token = header.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     *  根据请求token从 redis中获取用户
     * @param request
     * @return
     */
    public User getUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.isNotBlank(token)) {
            try {
                Claims claims = parseToken(token);
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String tokenKey = getTokenKey(uuid);
                User user = cacheService.getCacheValue(tokenKey);
                return user;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public String getTokenKey(String uuid)
    {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }
}
