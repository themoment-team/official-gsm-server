package team.themoment.officialgsm.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import team.themoment.officialgsm.admin.controller.auth.manager.CookieManager;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.common.exception.CustomHttpStatus;
import team.themoment.officialgsm.common.util.ConstantsUtil;
import team.themoment.officialgsm.domain.token.BlackList;
import team.themoment.officialgsm.global.security.jwt.JwtTokenProvider;
import team.themoment.officialgsm.persistence.token.repository.BlackListRepositoryImpl;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final BlackListRepositoryImpl blackListRepository;
    private final JwtTokenProvider jwtProvider;
    private final CookieManager cookieManager;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/api/auth/token/refresh");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, CustomException {
        String token = cookieManager.getCookieValue(request, ConstantsUtil.accessToken);

        if (token != null) {
            Optional<BlackList> blackList = blackListRepository.findByAccessToken(token);
            if (blackList.isPresent() && token.equals(blackList.get().accessToken())) {
                throw new CustomException("Invalid Token", CustomHttpStatus.UNAUTHORIZED);
            }
            UsernamePasswordAuthenticationToken auth = jwtProvider.authentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}