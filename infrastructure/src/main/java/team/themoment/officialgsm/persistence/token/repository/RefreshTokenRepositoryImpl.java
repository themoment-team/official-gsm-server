package team.themoment.officialgsm.persistence.token.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.persistence.token.entity.RefreshTokenRedisEntity;
import team.themoment.officialgsm.persistence.token.mapper.RefreshTokenMapper;
import team.themoment.officialgsm.repository.token.RefreshTokenRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;
    private final RefreshTokenMapper refreshTokenMapper;

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

    @Override
    public Optional<RefreshToken> findByOauthId(String oauthId) {
        // mapper 사용
        Optional<RefreshTokenRedisEntity> refreshToken = refreshTokenJpaRepository.findById(oauthId);
        return Optional.ofNullable(RefreshToken.builder()
                .refreshToken(refreshToken.get().getRefreshToken())
                .oauthId(refreshToken.get().getOauthId())
                .expiredAt(refreshToken.get().getExpiredAt())
                .build());
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        // mapper 사용
        refreshTokenJpaRepository.delete(RefreshTokenRedisEntity.builder()
                        .oauthId(refreshToken.oauthId())
                        .refreshToken(refreshToken.refreshToken())
                        .expiredAt(refreshToken.expiredAt())
                .build());
    }
}
