package team.themoment.officialgsm.persistence.user.entity;

import jakarta.persistence.*;
import lombok.*;
import team.themoment.officialgsm.domain.user.Role;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserJpaEntity {
    @Id
    @Column(name = "oauth_id", nullable = false)
    private String oauthId;

    @Column(name = "user_name", nullable = true)
    private String userName;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToOne
    @JoinColumn(name = "grantor_seq", referencedColumnName = "oauth_id", nullable = true)
    private UserJpaEntity grantor;

    @Column(name = "approved_at", nullable = true)
    private LocalDateTime approvedAt;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;
}