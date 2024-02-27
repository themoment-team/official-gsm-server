package team.themoment.officialgsm.repository.token;

import team.themoment.officialgsm.domain.token.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByOauthId(String oauthId);
    void delete(RefreshToken refreshToken);
}