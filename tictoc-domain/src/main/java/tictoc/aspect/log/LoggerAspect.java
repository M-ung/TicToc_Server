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
            pointcut = "execution(* tictoc.auction.event.CloseAuctionEventListener(..))",
            returning = "result"
    )
    public void closeAuctionSuccess(JoinPoint joinPoint, Object result) {
        log.info("[CLOSE_AUCTION SUCCESS] method={}, args={}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterThrowing(
            pointcut = "execution(* tictoc..*(..))",
            throwing = "ex"
    )
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("[ERROR {}] 예외 발생: {}, message: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs(),
                ex.getMessage(), ex);
    }
}