package team.themoment.officialgsm.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.admin.controller.auth.manager.CookieManager;
import team.themoment.officialgsm.common.util.ConstantsUtil;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    private final CookieManager cookieManager;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String token = cookieManager.getCookieValue(request, ConstantsUtil.accessToken);

        log.error("AUTHENTICATION_ENTRY_POINT");
        log.info(token);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
