package team.themoment.officialgsm.domain.token;

public record RefreshToken(

        String oauthId,
        String refreshToken,
        Long expiredAt
) {
}
