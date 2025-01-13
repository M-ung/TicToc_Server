package org.tictoc.tictoc.global.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.tictoc.tictoc.global.auth.jwt.JwtProvider;
import org.tictoc.tictoc.global.common.entity.Constants;
import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.UnauthorizedException;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String accessToken = getAccessToken(request);
        final long userId = jwtProvider.getUserIdFromToken(accessToken);
        doAuthentication(accessToken, userId);
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(final HttpServletRequest request) {
        final String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(Constants.BEARER)) {
            return accessToken.substring(Constants.BEARER.length());
        }
        throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
    }

    private void doAuthentication(final String token, final long userId) {
        TokenAuthentication tokenAuthentication = TokenAuthentication.createTokenAuthentication(token, userId);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(tokenAuthentication);
    }
}
