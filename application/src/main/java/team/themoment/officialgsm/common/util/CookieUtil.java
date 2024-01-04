package team.themoment.officialgsm.common.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieUtil {
    void addTokenCookie(HttpServletResponse response, String name, String value, Long maxAge, boolean httpOnly);
    String getCookieValue(HttpServletRequest request, String name);
}
