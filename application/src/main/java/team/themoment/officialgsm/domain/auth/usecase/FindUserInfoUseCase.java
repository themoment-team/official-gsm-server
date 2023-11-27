package team.themoment.officialgsm.domain.auth.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.officialgsm.common.annotation.UseCaseWithReadOnlyTransaction;
import team.themoment.officialgsm.common.util.UserUtil;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.user.User;

@Service
@UseCaseWithReadOnlyTransaction
@RequiredArgsConstructor
public class FindUserInfoUseCase {
    private final UserUtil userUtil;

    public UserInfoDto execute() {
        User user = userUtil.getCurrentUser();
        return UserInfoDto.builder()
                .userName(user.userName())
                .userEmail(user.userEmail())
                .role(user.role())
                .build();
    }
}
