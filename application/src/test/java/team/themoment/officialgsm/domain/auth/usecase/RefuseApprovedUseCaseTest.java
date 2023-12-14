package team.themoment.officialgsm.domain.auth.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefuseApprovedUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RefuseApprovedUseCase refuseApprovedUseCase;

    @Test
    void execute() {
        // given
        String oauthId = "0";

        User user = new User(oauthId, "신희성", "s23012@gsm.hs.kr", Role.UNAPPROVED, null, null, null);

        given(userRepository.findByOauthId(oauthId)).willReturn(Optional.of(user));

        // when
        refuseApprovedUseCase.execute(oauthId);

        // then
        verify(userRepository, times(1)).findByOauthId(oauthId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void execute_userNotFound() {
        // given
        String oauthId = "0";

        when(userRepository.findByOauthId(oauthId)).thenReturn(Optional.empty());

        // when
        assertThrows(CustomException.class, () -> refuseApprovedUseCase.execute(oauthId));

        // then
        verify(userRepository, times(1)).findByOauthId(oauthId);
        verify(userRepository, never()).delete(any());
    }
}
