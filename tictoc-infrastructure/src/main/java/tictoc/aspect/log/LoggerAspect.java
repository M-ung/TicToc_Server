package tictoc.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LoggerAspect {
    @AfterThrowing(
            pointcut = "execution(* tictoc.redis.*(..))",
            throwing = "ex"
    )
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("[EXCEPTION] method={}, args={}, message: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()),
                ex.getMessage(), ex);
    }
}