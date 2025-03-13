package tictoc.annotation;

import tictoc.constants.RedisConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {
    String key();
    long waitTime() default RedisConstants.LOCK_WAIT_TIME;
    long leaseTime() default RedisConstants.LOCK_LEASE_TIME;
    long delayTime() default 0;
}