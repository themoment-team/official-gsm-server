package team.themoment.officialgsm.domain.auth.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.officialgsm.common.annotation.UseCaseWithReadOnlyTransaction;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.user.User;

@Service
@UseCaseWithReadOnlyTransaction
@RequiredArgsConstructor
public class FindUserInfoUseCase {

    public UserInfoDto execute(User user) {
        return UserInfoDto.builder()
                .userName(user.userName())
                .userEmail(user.userEmail())
                .role(user.role())
                .build();
    }
}
