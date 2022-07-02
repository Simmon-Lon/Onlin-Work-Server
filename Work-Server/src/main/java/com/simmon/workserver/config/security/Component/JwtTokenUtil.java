package com.simmon.workserver.config.security.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simmon
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME="sub";
    private static final String CLAIM_KEY_CREATED="created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 创建token
     * @param userDetails 根据前端传回的用户名
     * @return 创建之后的token令牌
     */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 加盐并生成token令牌
     * @param claims
     * @return
     */
    private String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    /**
     * 设置过期时间
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }


    /**
     * 获取用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token){
        String username;
        try {
            Claims claims=getClaimsFromToken(token);
            username=claims.getSubject();
        }catch (Exception e){
            username=null;
        }
        return username;
    }

    /**
     * 解析token令牌获取subject
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims=null;
        try {
            claims=Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return claims;
    }


    /**
     * 判断token是否有效
     * 1,判断用户名与userdetails中的是否一致
     * 2,判断token时间是否过期
     * @param token
     * @param userdetails
     * @return
     */
    public boolean validateToken(String token,UserDetails userdetails){
        String username = getUserNameFromToken(token);
        return username.equals(userdetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断令牌时间是否失效
     * @param token
     * @return 当前时间与令牌中作比较
     *             if 令牌中的时间比当前时间晚,也就是大于当前时间返回<code>true</code>
     *             else <code>false</code>
     */
    private boolean isTokenExpired(String token) {
        Date expireDate=getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 获取令牌失效时间
     * @param token
     * @return 失效时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }


    /**
     * 刷新token令牌
     * @param token
     * @return 创建新的token令牌
     */
    public String refreshToken(String token){
        Claims claims=getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 判断令牌是否可以被刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }
}
