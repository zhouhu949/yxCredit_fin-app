package com.zw.rule.web.aop.annotaion;

import java.lang.annotation.*;

/**
 * Created by zh-pc on 2018/1/3.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NotProcess {
}
