package top.wanmudong.redis.annotation;

import java.lang.annotation.*;

/**
 *@Author wanmudong
 *@Date 11:34 2018/12/11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Lock {

    String lockPrefix() default "";

    String lockKey();

    int expireTime();

    int timeout() default -1;
}
