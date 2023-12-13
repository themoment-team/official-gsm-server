package team.themoment.officialgsm.domain.auth.usecase;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.officialgsm.common.util.UserUtil;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindUserInfoUseCaseTest {

    @Mock
    private UserUtil userUtil;

    @InjectMocks
    private FindUserInfoUseCase findUserInfoUseCase;

    @Test
    void execute() {
        // given
        User user = new User("0", "신희성", "s23012@gsm.hs.kr", Role.ADMIN, null, null, null);

        given(userUtil.getCurrentUser()).willReturn(user);

        // when
        UserInfoDto result = findUserInfoUseCase.execute();

        // then
        verify(userUtil, times(1)).getCurrentUser();

        assertThat("신희성").isEqualTo(result.getUserName());
        assertThat("s23012@gsm.hs.kr").isEqualTo(result.getUserEmail());
        assertThat(Role.ADMIN).isEqualTo(result.getRole());
    }
}