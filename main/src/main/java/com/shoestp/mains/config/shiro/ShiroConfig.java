package com.shoestp.mains.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/** * Shiro权限控制 */
@Configuration
public class ShiroConfig {

  @Bean
  @DependsOn("lifecycleBeanPostProcessor")
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator =
        new DefaultAdvisorAutoProxyCreator();
    defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
    return defaultAdvisorAutoProxyCreator;
  }

  @Bean(name = "customRealm")
  public TokenRealm customRealm() {
    return new TokenRealm();
  }

  @Bean(name = "securityManager")
  public DefaultWebSecurityManager defaultWebSecurityManager(TokenRealm customRealm) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    DefaultSessionManager defaultSessionManager = new DefaultSessionManager();
    defaultSessionManager.setSessionValidationSchedulerEnabled(false);
    DefaultSessionStorageEvaluator defaultSessionStorageEvaluator =
        new DefaultSessionStorageEvaluator();
    DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
    defaultSubjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
    //
    securityManager.setSessionManager(defaultSessionManager);
    securityManager.setRealm(customRealm);
    securityManager.setSubjectDAO(defaultSubjectDAO);
    return securityManager;
  }

  @Bean(name = "lifecycleBeanPostProcessor")
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  @Bean(name = "shiroFilter")
  public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    // 必须设置 SecurityManager
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    // 验证码过滤器
    Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
    filtersMap.put("jwt", new JWTFilter());
    shiroFilterFactoryBean.setFilters(filtersMap);
    // 拦截器
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    // 访问401和404页面不通过我们的Filter
    filterChainDefinitionMap.put("/401", "anon");
    filterChainDefinitionMap.put("/404", "anon");
    // 其他的
    filterChainDefinitionMap.put("/**", "jwt");
    shiroFilterFactoryBean.setUnauthorizedUrl("/401");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    return shiroFilterFactoryBean;
  }
}
