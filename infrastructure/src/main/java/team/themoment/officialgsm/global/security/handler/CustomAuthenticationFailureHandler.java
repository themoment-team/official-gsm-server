package team.themoment.officialgsm.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final String siteAddress;

    public CustomAuthenticationFailureHandler(@Value("${site-address}") String siteAddress) {
        this.siteAddress = siteAddress;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof OAuth2AuthenticationException){
            oAuth2AuthenticationExceptionHandling(request, response, (OAuth2AuthenticationException) exception);
        }
    }

    private void oAuth2AuthenticationExceptionHandling(HttpServletRequest request, HttpServletResponse response, OAuth2AuthenticationException exception) {
        log.error("OAuth2AuthenticationException error code = '{}'", exception.getError().getErrorCode());
        try {
            response.sendRedirect(siteAddress + "/auth/signin/warning");
        } catch (IOException e) {
            log.error(siteAddress + "/auth/signin/warning로 리다이렉트 도중 에러가 발생했습니다.", e);
        }
    }
}
