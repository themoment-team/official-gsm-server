package team.themoment.officialgsm.global.security.util;

import org.springframework.security.core.context.SecurityContextHolder;
import team.themoment.officialgsm.common.util.UserUtil;

public class UserUtilImpl implements UserUtil {


    @Override
    public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
