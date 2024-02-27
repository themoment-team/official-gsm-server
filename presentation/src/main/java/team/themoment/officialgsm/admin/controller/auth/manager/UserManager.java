package team.themoment.officialgsm.admin.controller.auth.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.common.exception.CustomHttpStatus;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String oauthId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new CustomException("요청하신 oauthId는 존재하지 않습니다.", CustomHttpStatus.INTERNAL_SERVER_ERROR));
    }
}