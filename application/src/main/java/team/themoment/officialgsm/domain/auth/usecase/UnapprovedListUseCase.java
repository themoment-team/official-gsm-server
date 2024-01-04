package team.themoment.officialgsm.domain.auth.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.officialgsm.common.annotation.UseCaseWithTransaction;
import team.themoment.officialgsm.domain.auth.dto.UnapprovedListDto;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.util.List;

@Service
@UseCaseWithTransaction
@RequiredArgsConstructor
public class UnapprovedListUseCase {
    private final UserRepository userRepository;

    public List<UnapprovedListDto> execute() {
        return userRepository.findAllByRoleAndUserNameNotNull(Role.UNAPPROVED).stream()
                .map(user -> UnapprovedListDto.builder()
                        .userSeq(user.oauthId())
                        .userName(user.userName())
                        .role(user.role())
                        .requestedAt(user.requestedAt())
                        .build()).toList();
    }
}
