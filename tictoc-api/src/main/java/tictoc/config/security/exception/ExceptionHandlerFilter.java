package tictoc.config.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tictoc.constants.AuthConstants;
import tictoc.error.ErrorCode;
import tictoc.error.ErrorResponse;
import tictoc.error.exception.UnauthorizedException;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            handleUnauthorizedException(response, e);
        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void handleUnauthorizedException(HttpServletResponse response, UnauthorizedException e) throws IOException {
        ErrorCode errorMessage = e.getErrorCode();
        HttpStatus httpStatus = errorMessage.getStatus();
        setResponse(response, httpStatus, errorMessage);
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        setResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private void setResponse(HttpServletResponse response, HttpStatus httpStatus, ErrorCode errorCode) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(AuthConstants.CHARACTER_TYPE);
        response.setStatus(httpStatus.value());
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(ErrorResponse.of(errorCode)));
    }
}