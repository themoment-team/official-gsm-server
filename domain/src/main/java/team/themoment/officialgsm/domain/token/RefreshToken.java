package team.themoment.officialgsm.domain.token;

import lombok.Builder;

@Builder
public record RefreshToken(

        String oauthId,
        String refreshToken,
        Long expiredAt
) {
    public RefreshToken updateRefreshToken(String refreshToken) {
        return new RefreshToken(
                this.oauthId,
                refreshToken,
                this.expiredAt
        );
    }
}
