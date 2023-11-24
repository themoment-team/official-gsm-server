package team.themoment.officialgsm.global.security.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.common.util.EmailUtil;

@Component
public class EmailUtilImpl implements EmailUtil {

    @Value("${emailId-regex}")
    private String emailIdRegex;

    @Override
    public String getOauthEmailDomain(String email) {
        int index = email.indexOf("@");

        String emailId = email.substring(0, index);

        if (emailId.matches(emailIdRegex)){
            throw new IllegalArgumentException("학생은 로그인할 수 없습니다.");
        }

        return email.substring(index + 1);
    }
}
