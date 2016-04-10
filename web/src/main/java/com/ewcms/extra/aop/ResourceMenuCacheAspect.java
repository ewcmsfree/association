package com.ewcms.extra.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ewcms.common.cache.BaseCacheAspect;
import com.ewcms.security.user.entity.User;

/**
 * 缓存及清理菜单缓存
 * 
 * @author wu_zhijun
 */
@Component
@Aspect
public class ResourceMenuCacheAspect extends BaseCacheAspect {

    public ResourceMenuCacheAspect() {
        setCacheName("ewcms-menuCache");
    }

    private String menusKeyPrefix = "menus-";

    /**
     * 切入点对象，被代理{@link com.ewcms.security.resource.service.ResourceService}的对象
     */
    @Pointcut(value = "target(com.ewcms.security.resource.service.ResourceService)")
    private void resourceServicePointcut() {
    }

    /**
     * 切入点方法
     */
    @Pointcut(value = "execution(* save(..)) || execution(* update(..)) || execution(* delete*(..)) || execution(* append*(..)) || execution(* move(..)) )")
    private void resourceCacheEvictAllPointcut() {
    }

    /**
     * 切入点方法
     */
    @Pointcut(value = "execution(* findMenus(*)) && args(arg)", argNames = "arg")
    private void resourceCacheablePointcut(User arg) {
    }

    @Before(value = "resourceServicePointcut() && resourceCacheEvictAllPointcut()")
    public void cacheClearAllAdvice() throws Throwable {
        clear();
    }

    @Around(value = "resourceServicePointcut() && resourceCacheablePointcut(arg)", argNames = "pjp,arg")
    public Object findResourceCacheableAdvice(ProceedingJoinPoint pjp, User arg) throws Throwable {
        User user = arg;

        String key = menusKey(user.getId());
        Object retVal = get(key);

        if (retVal != null) {
            log.debug("cacheName:{}, method:findResourceCacheableAdvice, hit key:{}", cacheName, key);
            return retVal;
        }
        log.debug("cacheName:{}, method:method:findResourceCacheableAdvice, miss key:{}", cacheName, key);

        retVal = pjp.proceed();

        put(key, retVal);

        return retVal;
    }


    public void evict(Long userId) {
        evict(menusKey(userId));
    }


    private String menusKey(Long userId) {
        return this.menusKeyPrefix + userId;
    }
}
