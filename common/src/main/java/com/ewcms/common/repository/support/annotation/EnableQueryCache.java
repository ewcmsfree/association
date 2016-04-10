package com.ewcms.common.repository.support.annotation;

import java.lang.annotation.*;

/**
 * 开启查询缓存
 * 
 * @author wu_zhijun
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableQueryCache {

    boolean value() default true;

}
