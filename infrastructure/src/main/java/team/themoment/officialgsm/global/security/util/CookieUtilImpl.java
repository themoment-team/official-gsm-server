package team.themoment.officialgsm.global.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.common.util.CookieUtil;

@Component
public class CookieUtilImpl implements CookieUtil {
    @Value("${cookie-domain}")
    private String cookieDomain;

    @Override
    public void addTokenCookie(HttpServletResponse response, String name, String value, Long maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(Math.toIntExact(maxAge)/1000);
        cookie.setHttpOnly(httpOnly);
        cookie.setDomain(cookieDomain);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public String getCookieValue(HttpServletRequest request, String name) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }
}
