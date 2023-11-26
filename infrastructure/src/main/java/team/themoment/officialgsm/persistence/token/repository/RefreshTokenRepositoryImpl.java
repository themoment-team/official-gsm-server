package team.themoment.officialgsm.persistence.token.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.persistence.token.entity.RefreshTokenRedisEntity;
import team.themoment.officialgsm.repository.token.RefreshTokenRepository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public void save(RefreshToken refreshToken) {
        // mapper 사용
        RefreshTokenRedisEntity refreshTokenRedisEntity = RefreshTokenRedisEntity.builder()
                .expiredAt(refreshToken.expiredAt())
                .refreshToken(refreshToken.refreshToken())
                .oauthId(refreshToken.oauthId())
        .build();
        refreshTokenJpaRepository.save(refreshTokenRedisEntity);
    }
}
