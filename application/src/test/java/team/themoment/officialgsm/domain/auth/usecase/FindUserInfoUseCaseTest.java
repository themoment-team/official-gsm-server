package team.themoment.officialgsm.domain.auth.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FindUserInfoUseCaseTest {

    @InjectMocks
    private FindUserInfoUseCase findUserInfoUseCase;

    @Test
    void execute() {
        // given
        User user = new User("0", "신희성", "s23012@gsm.hs.kr", Role.ADMIN, null, null, null);

        // when
        UserInfoDto result = findUserInfoUseCase.execute(user);

        // then
        assertThat("신희성").isEqualTo(result.getUserName());
        assertThat("s23012@gsm.hs.kr").isEqualTo(result.getUserEmail());
        assertThat(Role.ADMIN).isEqualTo(result.getRole());
    }
}