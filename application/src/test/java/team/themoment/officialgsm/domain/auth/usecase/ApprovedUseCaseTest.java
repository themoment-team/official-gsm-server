package team.themoment.officialgsm.domain.auth.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.common.util.UserUtil;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ApprovedUseCaseTest {

    @InjectMocks
    private ApprovedUseCase approvedUseCase;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserUtil userUtil;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    void execute() {
        // given
        String oauthId = "0";
        User user = new User(oauthId, "신희성", "s23012@gsm.hs.kr", Role.UNAPPROVED, null, null, null);
        User grantor = new User("1", "최장우", "s22000@gsm.hs.kr", Role.ADMIN, null, null, null);

        given(userUtil.getCurrentUser()).willReturn(grantor);
        given(userRepository.findByOauthId(oauthId)).willReturn(Optional.of(user));

        // when
        approvedUseCase.execute(oauthId);

        // then
        verify(userRepository, times(1)).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertThat(Role.ADMIN).isEqualTo(capturedUser.role());
        assertThat(user.userName()).isEqualTo(capturedUser.userName());
        assertThat(grantor).isEqualTo(capturedUser.grantor());
    }

    @Test
    void execute_userNotFound() {
        // given
        String oauthId = "0";
        User grantor = new User("1", "최장우", "s22000@gsm.hs.kr", Role.ADMIN, null, null, null);

        given(userUtil.getCurrentUser()).willReturn(grantor);
        given(userRepository.findByOauthId(oauthId)).willReturn(Optional.empty());

        // when
        assertThrows(CustomException.class, () -> approvedUseCase.execute(oauthId));

        // then
        verify(userRepository, times(1)).findByOauthId(oauthId);
        verify(userRepository, never()).save(any());
    }
}