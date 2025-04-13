package tictoc.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LoggerAspect {
    @AfterReturning(
            pointcut = "execution(* tictoc.userLoginHistory.archive.LoginHistoryCsvArchiver(..))",
            returning = "result"
    )
    public void csvArchiverSuccess(JoinPoint joinPoint, Object result) {
        log.info("[CSV_ARCHIVER SUCCESS] method={}, args={}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(
            pointcut = "execution(* tictoc.userLoginHistory.config.UserLoginHistoryBatchConfig(..))",
            returning = "result"
    )
    public void deleteUserLoginHistorySuccess(JoinPoint joinPoint, Object result) {
        log.info("[DELETE_USER_LOGIN_HISTORY SUCCESS] method={}, args={}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterThrowing(
            pointcut = "execution(* tictoc.userLoginHistory.*(..))",
            throwing = "ex"
    )
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("[EXCEPTION] method={}, args={}, message: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()),
                ex.getMessage(), ex);
    }
}