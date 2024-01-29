package team.themoment.officialgsm.domain.user;

import java.time.LocalDateTime;

public record User(
        String oauthId,
        String userName,
        String userEmail,
        Role role,
        User grantor,
        LocalDateTime approvedAt,
        LocalDateTime requestedAt
) {
}
