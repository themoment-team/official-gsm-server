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
    public User modifyUserName(String newUserName) {
        return new User(
                this.oauthId,
                newUserName,
                this.userEmail,
                this.role,
                this.grantor,
                this.approvedAt,
                this.requestedAt
        );
    }
}
