package com.ecy.show.annotation;

import java.lang.annotation.*;

/**
 * @author luhong
 * @version 1.0
 * @Description
 * @date 2019/5/13
 * @updateby 自定义注解类，此注解不包装返回参数
 * @updatedate
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseAnnotation {
}