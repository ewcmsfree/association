package com.ewcms.security.user.web.bind.annotation;

import java.lang.annotation.*;

import com.ewcms.common.Constants;

/**
 * 绑定当前登录的用户,不同于@ModelAttribute
 *
 * @author wu_zhijun
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    /**
     * 当前用户在request中的名字 默认{@link Constants#CURRENT_USER}
     *
     * @return
     */
    String value() default Constants.CURRENT_USER;

}
