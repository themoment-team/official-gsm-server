package team.themoment.officialgsm.domain.auth.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.officialgsm.domain.auth.dto.UnapprovedListDto;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class UnapprovedListUseCaseTest {

    @InjectMocks
    private UnapprovedListUseCase unapprovedListUseCase;

    @Mock
    private UserRepository userRepository;

    @Test
    void execute() {
        // given
        String oauthId = "0";
        String userName = "신희성";
        Role userRole = Role.UNAPPROVED;

        List<User> userList = List.of(new User(oauthId, userName, "s23012@gsm.hs.kr", userRole, null, null, null));

        given(userRepository.findAllByRoleAndUserNameNotNull(userRole)).willReturn(userList);

        // when
        List<UnapprovedListDto> result = unapprovedListUseCase.execute();

        // then
        verify(userRepository, times(1)).findAllByRoleAndUserNameNotNull(userRole);

        assertThat(oauthId).isEqualTo(result.get(0).getUserSeq());
        assertThat(userName).isEqualTo(result.get(0).getUserName());
        assertThat(userRole).isEqualTo(result.get(0).getRole());

    }
}