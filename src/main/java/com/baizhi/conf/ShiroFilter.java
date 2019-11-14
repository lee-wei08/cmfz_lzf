package com.baizhi.conf;

import com.baizhi.role.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration // 声明此类为一个配置类
public class ShiroFilter {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager SecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(SecurityManager);
        // 告知shiro 要拦截哪些资源 需要一个Map
        HashMap<String, String> map = new HashMap<>();
        // 匿名拦截器
        map.put("/login/login.jsp", "anon");
        // 认证拦截器
        map.put("/back/*.jsp", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        // 指定登录界面
        shiroFilterFactoryBean.setLoginUrl("/login/login.jsp");
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager getSecurityManager(ShiroRealm shiroRealm, CacheManager cacheManager) {
        // SecurityManager 是一个接口 所以使用他的接口创建起对象 DefaultWebSecurityManager
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    @Bean
    public ShiroRealm getShiroRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return shiroRealm;
    }

    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

    @Bean
    public CacheManager getCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        return ehCacheManager;
    }

}
