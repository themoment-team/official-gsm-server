package team.themoment.officialgsm.domain.token;

import lombok.Builder;

@Builder
public record RefreshToken(

        String oauthId,
        String refreshToken,
        Long expiredAt
) {
}
