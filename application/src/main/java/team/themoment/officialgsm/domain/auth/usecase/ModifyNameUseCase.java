package team.themoment.officialgsm.domain.auth.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.officialgsm.common.annotation.UseCaseWithTransaction;
import team.themoment.officialgsm.domain.auth.dto.UserNameDto;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

@Service
@UseCaseWithTransaction
@RequiredArgsConstructor
public class ModifyNameUseCase {
    private final UserRepository userRepository;

    public void execute(UserNameDto dto, User user) {
        User modifiedUser = user.modifyUserName(dto.getUserName());
        userRepository.save(modifiedUser);
    }
}
