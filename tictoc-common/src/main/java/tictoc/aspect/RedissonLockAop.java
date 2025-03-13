package tictoc.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import tictoc.annotation.DistributedLock;
import tictoc.annotation.RedissonLock;
import tictoc.constants.RedisConstants;
import tictoc.error.ErrorCode;
import tictoc.error.exception.LockAcquisitionException;
import java.util.concurrent.TimeUnit;

@Aspect
@RedissonLock
@RequiredArgsConstructor
public class RedissonLockAop {
    private final RedissonClient redissonClient;

    @Around("@annotation(distributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        String lockKey = RedisConstants.LOCK_PREFIX + distributedLock.key();
        RLock lock = redissonClient.getLock(lockKey);
        boolean acquired = false;
        try {
            acquired = lock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), TimeUnit.SECONDS);
            if (!acquired) {
                throw new LockAcquisitionException(ErrorCode.BID_FAIL);
            }
            Object result = joinPoint.proceed();
            if (distributedLock.delayTime() > 0) {
                Thread.sleep(distributedLock.delayTime() * 1000);
            }
            return result;
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}