package team.themoment.officialgsm.domain.user;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
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

    public User approvedExecute(User grantor, LocalDateTime approvedAt) {
        return new User(
                this.oauthId,
                this.userName,
                this.userEmail,
                Role.ADMIN,
                grantor,
                approvedAt,
                this.requestedAt
        );
    }
}
