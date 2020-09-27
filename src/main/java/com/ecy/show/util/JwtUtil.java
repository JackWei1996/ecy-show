package com.ecy.show.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecy.show.dto.sys.CurrentUser;
import com.ecy.show.exception.BusinessException;
import com.ecy.show.global.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JwtUtil {

    // 过期时间10小时
    private static final long EXPIRE_TIME = 10 * 60 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param userId 用户Id
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String userId, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(Constant.KEY_ID, userId)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static Long getUserId(Boolean throwEx) throws BusinessException {
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getPrincipal();
        if (currentUser.getId() == null && throwEx) {
            throw new BusinessException("请先登录");
        }
        return currentUser.getId();
    }
    public static Long getUserId(){
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getPrincipal();
        if (currentUser == null) {
            return null;
        }
        return currentUser.getId();
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String userId = jwt.getClaim(Constant.KEY_ID).asString();
            if (userId == null) {
                return null;
            }
            return Long.parseLong(userId);
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 此方法用于未拦截的请求方法中获取用户id
     * @param request
     * @return 未登录得到null，登录，返回用户id
     */
    public static Long getUserId(HttpServletRequest request) {
        String token=JwtUtil.getToken(request);
        if (!StringUtils.isEmpty(token)) {
            return JwtUtil.getUserId(token);
        }
        return null;
    }

    public static String getToken(HttpServletRequest request) {
        String authorization = request.getHeader(Constant.AUTHORIZATION);
        if (authorization != null && authorization.length() > Constant.BEARER.length()) {
            return authorization.substring(Constant.BEARER.length() + 1);
        }
        return null;
    }

    public static String getToken(HttpServletRequest request, boolean throwEx){
       String token= JwtUtil.getToken(request);

       if(token==null&&throwEx==true){
           throw new AuthorizationException("请先登录!");
       }
        return token;
    }

    /**
     * 生成签名,5min后过期
     *
     * @param userId 用户Id
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(String userId, String secret) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带KEY_ID信息
            return JWT.create()
                    .withClaim(Constant.KEY_ID, userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}