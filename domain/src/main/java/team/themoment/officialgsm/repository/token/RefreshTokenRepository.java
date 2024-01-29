package team.themoment.officialgsm.repository.token;

import team.themoment.officialgsm.domain.token.RefreshToken;

public interface RefreshTokenRepository {
    void save(RefreshToken refreshToken);
}
