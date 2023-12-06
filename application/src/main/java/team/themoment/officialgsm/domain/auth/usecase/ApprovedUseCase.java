package team.themoment.officialgsm.domain.auth.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.officialgsm.common.annotation.UseCaseWithTransaction;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.common.exception.CustomHttpStatus;
import team.themoment.officialgsm.common.util.UserUtil;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.time.LocalDateTime;

@Service
@UseCaseWithTransaction
@RequiredArgsConstructor
public class ApprovedUseCase {
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    public void execute(String oauthId) {
        User grantor = userUtil.getCurrentUser();
        User user = userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new CustomException("유저를 찾을 수 없습니다.", CustomHttpStatus.NOT_FOUND));
        LocalDateTime approvedAt = LocalDateTime.now();
       User approvedUser = user.approvedExecute(grantor, approvedAt);
       userRepository.save(approvedUser);
    }
}