package team.themoment.officialgsm.domain.auth.usecase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import team.themoment.officialgsm.domain.auth.dto.UserNameDto;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;

import java.time.LocalDateTime;

class ModifyNameUseCaseTest {

    private final UserNameDto userNameDto = new UserNameDto(
            "신희성"
    );

    private final User user = new User(
            "0", null, "s23012@gsm.hs.kr", Role.UNAPPROVED, null, null, LocalDateTime.now()
    );

    @Test
    void execute() {
        Assertions.assertThat(user.modifyUserName(userNameDto.getUserName()).userName()).isEqualTo(userNameDto.getUserName());
    }
}