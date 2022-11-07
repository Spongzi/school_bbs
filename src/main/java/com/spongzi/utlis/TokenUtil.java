package com.spongzi.utlis;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author spongzi
 */
public class TokenUtil {

    /**
     * TOKEN的有效期1小时（S）
     */
    private static final int TOKEN_TIME_OUT = 3_600 * 24 * 7 * 1000;

    /**
     * 加密KEY
     */
    private static final String TOKEN_SECRET = "spongzi";


    /**
     * 生成Token
     *
     * @param params 需要传递的参数
     * @return 返回生成的token
     */
    public static String getToken(Map<String, Object> params) {
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                //加密方式
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                //过期时间戳
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT))
                .addClaims(params)
                .compact();
    }


    /**
     * 获取Token中的claims信息
     */
    public static Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
    }


    /**
     * 是否有效 true-有效，false-失效
     */
    public static boolean verifyToken(String token) {

        if (StringUtils.isEmpty(token)) {
            return false;
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}