package team.themoment.officialgsm.domain.approve.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.util.Optional;

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

        given(userRepository.findByOauthIdAndUserNameNotNull(oauthId)).willReturn(Optional.of(user));

        // when
        refuseApprovedUseCase.execute(oauthId);

        // then
        verify(userRepository, times(1)).findByOauthIdAndUserNameNotNull(oauthId);
        verify(userRepository, times(1)).delete(user);
    }
}