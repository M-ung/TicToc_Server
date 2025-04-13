package tictoc.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggerAspect {
    @AfterReturning(
            pointcut = "execution(* tictoc.user.application.UserCommandService.login(..))",
            returning = "result"
    )
    public void loginSuccess(JoinPoint joinPoint, Object result) {
        log.info("[LOGIN SUCCESS] method={}, args={}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(
            pointcut = "execution(* tictoc.bid.application.BidCommandService.bid(..))",
            returning = "result"
    )
    public void bidSuccess(JoinPoint joinPoint, Object result) {
        log.info("[BID SUCCESS] method={}, args={}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterThrowing(
            pointcut = "execution(* tictoc..*(..))",
            throwing = "ex"
    )
    public void exception(JoinPoint joinPoint, Throwable ex) {
        log.error("[EXCEPTION] method={}, args={}, message: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs(),
                ex.getMessage(), ex);
    }
}