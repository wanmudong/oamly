package top.wanmudong.redis.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.wanmudong.redis.RedisLock;
import top.wanmudong.redis.annotation.Lock;
import top.wanmudong.redis.exception.RedisLockException;

import java.lang.reflect.Method;

/**
 *@Author wanmudong
 *@Date 11:38 2018/12/11
 */
@Aspect
@Component
@Slf4j
public class RedisLockAspect {

    private static final String DEFAULT_LOCK_PREFIX = "lock";

    @Pointcut("@annotation(top.wanmudong.redis.annotation.Lock)")
    public void pointcut(){

    }

//    @Before("pointcut()")
//    public void before(JoinPoint joinPoint)throws  Throwable{
//        System.out.println(joinPoint.getArgs());
//        System.out.println("wanmudong");
//    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Lock lockAnnotation = method.getAnnotation(Lock.class);

        String lockKey = lockAnnotation.lockKey();
        if (StringUtils.isEmpty(lockKey)) {
            log.error("lockKey can not be empty!");
            throw new RedisLockException("lockKey can not be empty!");
        }

        int expireTime = lockAnnotation.expireTime();
        if (expireTime <= 0) {
            log.error("expireTime must greater than zero!");
            throw new RedisLockException("expireTime must greater than zero!");
        }

        String lockPrefix = StringUtils.isEmpty(lockAnnotation.lockPrefix()) ? DEFAULT_LOCK_PREFIX : lockAnnotation.lockPrefix();
        String key = lockPrefix + "_" + lockKey;

        int timeout = lockAnnotation.timeout();

        RedisLock lock = RedisLock.getInstance();

        boolean process = timeout > 0 ? lock.lock(key, expireTime, timeout) : lock.tryLock(key, expireTime);
        if (process) {
            try {
                return joinPoint.proceed();
            } finally {
                lock.unLock(key);
            }
        }else {
            log.warn("获取锁失败");
            throw new RedisLockException("fail to get lock!");
        }

    }





}
