package com.ecy.show.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(ShiroRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(realm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }
    /**
     * 添加自己的过滤器，自定义url规则
     * Shiro自带拦截器配置规则
     * rest：比如/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user：method] ,其中method为post，get，delete等
     * port：比如/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal：//serverName：8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数
     * perms：比如/admins/user/**=perms[user：add：*],perms参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，比如/admins/user/**=perms["user：add：*,user：modify：*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法
     * roles：比如/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，比如/admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。//要实现or的效果看http://zgzty.blog.163.com/blog/static/83831226201302983358670/
     * anon：比如/admins/**=anon 没有参数，表示可以匿名使用
     * authc：比如/admins/user/**=authc表示需要认证才能使用，没有参数
     * authcBasic：比如/admins/user/**=authcBasic没有参数表示httpBasic认证
     * ssl：比如/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
     * user：比如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
     * 详情见文档 http://shiro.apache.org/web.html#urls-
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put("jwt", new JwtFilter());
        filterMap.put("anyRole",new AnyRolesAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        // 未授权界面返回JSON
        shiroFilterFactoryBean.setLoginUrl("/public/notLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/public/unauthorized");

        Map<String, String> map = new LinkedHashMap<String, String>(32);
        //预约公共接口不拦截
        map.put("/open/**", "anon");

        map.put("/", "anon");
        map.put("/swagger-ui.html", "anon");
        map.put("/swagger**/**", "anon");
        map.put("/webjars/**", "anon");
        map.put("/csrf", "anon");

        map.put("/**/*.js", "anon");
        map.put("/**/*.css", "anon");
        map.put("/**/*.html", "anon");
        map.put("/**/*.svg", "anon");
        map.put("/**/*.pdf", "anon");
        map.put("/**/*.jpg", "anon");
        map.put("/**/*.png", "anon");
        map.put("/**/*.ico", "anon");

        map.put("/**/*.ttf", "anon");
        map.put("/**/*.woff", "anon");
        map.put("/**/*.woff2", "anon");
        map.put("/v2/**", "anon");
        map.put("/error/**", "anon");

        //anyRole任意一个角色属于此角色都可以访问
        //默认的roles过滤器，无角色时是重定向，RESTful前后端分离，不要使用roles[admin]
        //jwt,会先登录，anyRole过滤器才会拦截
        map.put("/admin/login", "anon");
        map.put("/department/list", "anon");
        map.put("/item/list", "anon");
        map.put("/wechat/**", "anon");
        map.put("/keytop/**", "anon");
        map.put("/user/logout", "anon");
        map.put("/verifyDate", "anon");

        map.put("/type/**", "jwt,anyRole[Admin]");
        map.put("/item/**", "jwt,anyRole[Admin]");
//        map.put("/department/**", "jwt,anyRole[Admin]");
//        map.put("/admin/**", "jwt");

 // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 所有请求通过我们自己的JWT Filter

        map.put("/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
            return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager  securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
