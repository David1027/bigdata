package com.shoestp.mains.config.shiro;

import com.shoestp.mains.config.shiro.pojo.UserInfo;
import com.shoestp.mains.config.shiro.utils.JWTUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
/** * Token权限校验类 */
@Component
public class TokenRealm extends AuthorizingRealm {

  private static final Logger logger = LogManager.getLogger(TokenRealm.class);

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JWTToken;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    UserInfo userInfo = JWTUtil.getUserInfo(principalCollection.toString());
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    simpleAuthorizationInfo.addRole(userInfo.getRole());
    Set<String> permission = new HashSet<>(Arrays.asList("url"));
    simpleAuthorizationInfo.addStringPermissions(permission);
    return simpleAuthorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    String token = (String) authenticationToken.getCredentials();

    return new SimpleAuthenticationInfo(token, token, "TokenRealm");
  }
}
