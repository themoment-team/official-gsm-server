package team.themoment.officialgsm.domain.auth.usecase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;

import java.time.LocalDateTime;

class ApprovedUseCaseTest {

    private final User grantor = new User(
            "0", "신희성", "s23012@gsm.hs.kr", Role.UNAPPROVED, null, null, LocalDateTime.now()
    );

    private final User user = new User(
            "1", "최장우", "s22012@gsm.hs.kr", Role.ADMIN, null, null, LocalDateTime.now()
    );

    private final LocalDateTime approvedAt = LocalDateTime.now();

    @Test
    void execute() {
        User approvedUser = user.approvedExecute(grantor, approvedAt);

        Assertions.assertThat(approvedUser.role()).isEqualTo(Role.ADMIN);
        Assertions.assertThat(approvedUser.grantor().oauthId()).isEqualTo(grantor.oauthId());
    }
}