package com.kawai.fdtp.common;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface HasRole {

    String[] value() default {};

    String path() default "";

}
