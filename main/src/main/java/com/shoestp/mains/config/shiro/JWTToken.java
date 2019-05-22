package com.shoestp.mains.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/21 Time: 15:38 */
public class JWTToken implements AuthenticationToken {

  // 密钥
  private String token;

  public JWTToken(String token) {
    this.token = token;
  }

  @Override
  public Object getPrincipal() {
    return token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }
}
