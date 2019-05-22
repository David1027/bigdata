package com.shoestp.mains.config.shiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.shoestp.mains.config.shiro.pojo.UserInfo;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;

public class JWTUtil {
  private static final Logger logger = LogManager.getLogger(JWTUtil.class);
  // 过期时间5分钟
  private static final long EXPIRE_TIME = 5 * 1000 * 60;

  /**
   * 校验token是否正确
   *
   * @param token 密钥
   * @param secret 用户的密码
   * @return 是否正确
   */
  public static boolean verify(String token, String key) {
    try {
      Algorithm algorithm = Algorithm.HMAC256("1234");
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      return true;
    } catch (Exception exception) {
      logger.info(exception.getMessage());
      return false;
    }
  }

  /**
   * 获得token中的信息无需secret解密也能获得
   *
   * @return token中包含的用户名
   */
  public static String getUsername(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim("username").asString();
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  public static UserInfo getUserInfo(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      UserInfo userInfo = new UserInfo();
      userInfo.setId(jwt.getClaim("id").asInt());
      userInfo.setRole(jwt.getClaim("role").asString());
      userInfo.setEmail(jwt.getClaim("email").asString());
      userInfo.setName(jwt.getClaim("username").asString());
      return userInfo;
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  public static UserInfo getUserInfo() {
    try {
      System.out.println(
          verify(
              String.valueOf(SecurityUtils.getSubject().getPrincipal()),
              "12131231231231231231231231313"));
      DecodedJWT jwt = JWT.decode(String.valueOf(SecurityUtils.getSubject().getPrincipal()));
      UserInfo userInfo = new UserInfo();
      userInfo.setId(jwt.getClaim("id").asInt());
      userInfo.setRole(jwt.getClaim("role").asString());
      userInfo.setEmail(jwt.getClaim("email").asString());
      userInfo.setName(jwt.getClaim("username").asString());
      return userInfo;
    } catch (JWTDecodeException e) {
      return null;
    }
  }
  /**
   * 生成签名,5min后过期
   *
   * @param username 用户名
   * @param secret 用户的密码
   * @return 加密的token
   */
  public static String sign(String username, String secret) {
    Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
    Algorithm algorithm = Algorithm.HMAC256(secret);
    // 附带username信息
    return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
  }

  public static void main(String[] args) {
    System.out.println(sign("hello", "1234"));
  }
}
