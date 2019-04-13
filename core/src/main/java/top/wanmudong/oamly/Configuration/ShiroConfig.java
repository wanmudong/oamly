package top.wanmudong.oamly.Configuration;


import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import top.wanmudong.oamly.modules.common.filter.AuthFilter;
import top.wanmudong.oamly.modules.common.utils.shiro.LoginRetryCredentialsMatcher;
import top.wanmudong.oamly.modules.common.utils.shiro.MyShiroRealm;
import top.wanmudong.oamly.modules.common.utils.shiro.ShiroUser;
import top.wanmudong.oamly.modules.common.utils.shiro.tool.CustomCredentialsMatcher;
import top.wanmudong.oamly.modules.common.utils.shiro.tool.ShiroSessionListener;
import top.wanmudong.oamly.modules.common.utils.shiro.tool.ShiroSessionRedisDAO;

import javax.servlet.Filter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /**
         * 无权限跳转的url
         */
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("loginoutfilter", new AuthFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        //拦截器.
        /**
         * anon:所有url都都可以匿名访问
         * authc: 需要认证才能进行访问
         */
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置不会被拦截的链接 顺序判断

        //解决登录成功后不跳转的问题
//        Map map= new LinkedHashMap();
//        map.put("loginoutfilter",new MyFormAuthenticationFilter());
//
//        shiroFilterFactoryBean.setFilters(map);
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/api/login", "anon");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//       filterChainDefinitionMap.put("/api/announce/**", "loginoutfilter");
//       filterChainDefinitionMap.put("/api/feedback/**", "loginoutfilter");
//       filterChainDefinitionMap.put("/api/recruit/**", "loginoutfilter");
//       filterChainDefinitionMap.put("/api/report/**", "loginoutfilter");
//       filterChainDefinitionMap.put("/api/getAllUser", "loginoutfilter");
////       filterChainDefinitionMap.put("/api/getUserInfoByStuid", "loginoutfilter");
//       filterChainDefinitionMap.put("/api/member/**", "loginoutfilter");
//       filterChainDefinitionMap.put("/api/me/**", "loginoutfilter");
        filterChainDefinitionMap.put("/**", "anon");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
       shiroFilterFactoryBean.setLoginUrl("/#/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

//        未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }



    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     * @return
     */
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher(){
//        System.out.println("hashedCredentialsMatcher!");
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
////        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(false);
//        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
//        return hashedCredentialsMatcher;
//    }

    /**
     * 缓存管理器 使用Ehcache实现
     */
    @Bean(name = "shiroCacheManager")
    public CacheManager cacheManager(/*EhCacheCacheManager cacheManager*/) {

        System.out.println("shiroCacheManager注入成功！");
        EhCacheManager ehCacheManager = new EhCacheManager();
        //设置 ehcache 缓存的配置文件；
        ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        ehCacheManager.init();
        return ehCacheManager;

    }

    /**
     * 凭证匹配器
     */
    @Bean
    public LoginRetryCredentialsMatcher credentialsMatcher() {
        LoginRetryCredentialsMatcher credentialsMatcher = new LoginRetryCredentialsMatcher();
        credentialsMatcher.setCacheManager(cacheManager());
        credentialsMatcher.setLoginRetryCacheName("loginRetry");
        credentialsMatcher.setMaxRetryCount(5);
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1);
        return credentialsMatcher;
    }

    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
//        myShiroRealm.setCredentialsMatcher(credentialsMatcher());
        myShiroRealm.setCredentialsMatcher(new CustomCredentialsMatcher());
        return myShiroRealm;
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }
    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

//    /**
//     * 在Shiro中我们可以通过
//     * org.apache.shiro.session.mgt.eis.SessionDAO对象的
//     *
//     * getActiveSessions()方法方便的获取到当前所有有效的Session对象。
//     * @return
//     */
//    @Bean
//    public SessionDAO sessionDAO() {
//        //如DefaultSessionManager在创建完session后会调用该方法；如保存到关系数据库/文件系统/NoSQL数据库；即可以实现会话的持久化；返回会话ID；主要此处返回的ID.equals(session.getId())；
//        //Serializable create(Session session);
//        //根据会话ID获取会话
//        //Session readSession(Serializable sessionId) throws UnknownSessionException;
//        //更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
//        //void update(Session session) throws UnknownSessionException;
//        //删除会话；当会话过期/会话停止（如用户退出时）会调用
//        //void delete(Session session);
//        //获取当前所有活跃用户，如果用户量多此方法影响性能
//        //Collection<Session> getActiveSessions();&nbsp;
//
//        /**
//         * AbstractSessionDAO 提供了 SessionDAO 的基础实现，如生成会话 ID 等；
//         * CachingSessionDAO 提供了对开发者透明的会话缓存的功能，只需要设置相应的 CacheManager 即可；
//         * MemorySessionDAO 直接在内存中进行会话维护；
//         * 而 EnterpriseCacheSessionDAO 提供了缓存功能的会话维护，默认情况下使用 MapCache 实现，内部使用 ConcurrentHashMap 保存缓存的会话。
//         */
////        MemorySessionDAO sessionDAO = new MemorySessionDAO();
//        ShiroSessionRedisDAO sessionDAO = new ShiroSessionRedisDAO();
//        return sessionDAO;
//    }

    @Bean
    public RedisConfig redisConfig(){
        return new RedisConfig();
    }


    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();     // crazycake 实现
        redisManager.setHost(redisConfig().getHost());
        redisManager.setTimeout(redisConfig().getTimeout());
        return redisManager;
    }
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }
    @Bean
    public RedisSessionDAO sessionDAO(){
        RedisSessionDAO sessionDAO = new RedisSessionDAO(); // crazycake 实现
        sessionDAO.setRedisManager(redisManager());
        sessionDAO.setSessionIdGenerator(sessionIdGenerator()); //  Session ID 生成器
        return sessionDAO;
    }
    @Bean
    public SimpleCookie cookie(){
        SimpleCookie cookie = new SimpleCookie("SHAREJSESSIONID"); //  cookie的name,对应的默认是 JSESSIONID
        cookie.setHttpOnly(true);
        cookie.setPath("/");        //  path为 / 用于多个系统共享JSESSIONID
        return cookie;
    }
    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(redisConfig().getTimeout());    // 设置session超时
        sessionManager.setDeleteInvalidSessions(true);      // 删除无效session
        sessionManager.setSessionIdCookie(cookie());            // 设置JSESSIONID
        sessionManager.setSessionDAO(sessionDAO());         // 设置sessionDAO
        return sessionManager;
    }
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();   // crazycake 实现
        cacheManager.setRedisManager(redisManager());
        return cacheManager;
    }

//    /**
//     * 在Shiro中，SessionDao通过org.apache.shiro.session.mgt.SessionManager进行管理，
//     * 所以继续在ShiroConfig中配置SessionManager：
//     * @return
//     */
//    @Bean
//    public SessionManager sessionManager() {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
//        listeners.add(new ShiroSessionListener());
//        sessionManager.setSessionListeners(listeners);
////        sessionManager.setSessionDAO(sessionDAO());
//        sessionManager.setSessionDAO(redisSessionDAO());
//        //设置会话的全局过期时间（毫秒为单位），默认 一天：
//        sessionManager.setGlobalSessionTimeout(2*60*60*1000);
////        sessionManager.setGlobalSessionTimeout(10*1000);
//
//        sessionManager.setSessionIdCookie(simpleCookie());
//        //是否启用 / 禁用 Session Id Cookie，默认是启用的；如果禁用后将不会设置 Session Id Cookie，即默认使用了 Servlet 容器的 JSESSIONID，且通过 URL 重写（URL 中的 “;JSESSIONID=id” 部分）保存 Session Id。
//        sessionManager.setSessionIdCookieEnabled(true);
//        return sessionManager;
//    }

    @Bean
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie();
        //设置 Cookie 名字，默认为 JSESSIONID
        simpleCookie.setName("JSESSIONID");
        //设置 Cookie 的域名，默认空，即当前访问的域名；
        //simpleCookie.setDomain();
        //设置 Cookie 的路径，默认空，即存储在域名根下；
        //simpleCookie.setPath();
        //设置 Cookie 的过期时间，秒为单位，默认 - 1 表示关闭浏览器时过期 Cookie
        simpleCookie.setMaxAge(-1);
        //如果设置为 true，则客户端不会暴露给客户端脚本代码，使用 HttpOnly cookie 有助于减少某些类型的跨站点脚本攻击；此特性需要实现了 Servlet 2.5 MR6 及以上版本的规范的 Servlet 容器支持；
        simpleCookie.setHttpOnly(false);

        return simpleCookie;
    }

    /**
     * cookie对象
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(24*60*60);
        return cookie;
    }

    /**
     * cookie管理对象
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }


//
//    @Bean
//    public DefaultWebSecurityManager securityManager(){
//        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
//        securityManager.setRealm(myShiroRealm());
//        System.out.println("securityManager");
//        return securityManager;
//    }
//
//
//    /**
//     * 凭证匹配器
//     */
//    @Bean
//    public LoginRetryCredentialsMatcher credentialsMatcher() {
//        LoginRetryCredentialsMatcher credentialsMatcher = new LoginRetryCredentialsMatcher();
//        credentialsMatcher.setCacheManager(cacheManager());
//        credentialsMatcher.setLoginRetryCacheName("loginRetry");
//        credentialsMatcher.setMaxRetryCount(5);
//        credentialsMatcher.setHashAlgorithmName("MD5");
//        credentialsMatcher.setHashIterations(1);
//        System.out.println("credentialsMatcher！");
//        return credentialsMatcher;
//    }
//

}