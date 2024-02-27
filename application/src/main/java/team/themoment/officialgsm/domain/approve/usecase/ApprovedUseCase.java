package team.themoment.officialgsm.domain.approve.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.officialgsm.common.annotation.UseCaseWithTransaction;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.common.exception.CustomHttpStatus;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.time.LocalDateTime;

@Service
@UseCaseWithTransaction
@RequiredArgsConstructor
public class ApprovedUseCase {
    private final UserRepository userRepository;

    public void execute(String oauthId, User grantor) {
        User user = userRepository.findByOauthIdAndUserNameNotNull(oauthId)
                .orElseThrow(() -> new CustomException("유저를 찾을 수 없습니다.", CustomHttpStatus.NOT_FOUND));
        LocalDateTime approvedAt = LocalDateTime.now();
       User approvedUser = user.approvedExecute(grantor, approvedAt);
       userRepository.save(approvedUser);
    }
}
