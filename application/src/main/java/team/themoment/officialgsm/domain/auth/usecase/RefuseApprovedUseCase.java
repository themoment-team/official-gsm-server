package team.themoment.officialgsm.domain.auth.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.officialgsm.common.annotation.UseCaseWithTransaction;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.common.exception.CustomHttpStatus;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

@Service
@UseCaseWithTransaction
@RequiredArgsConstructor
public class RefuseApprovedUseCase {
    private final UserRepository userRepository;

    public void execute(String useSeq) {
        User user = userRepository.findByOauthId(useSeq)
                .orElseThrow(() -> new CustomException("유저를 찾을 수 없습니다.", CustomHttpStatus.NOT_FOUND));
        userRepository.delete(user);
    }
}
