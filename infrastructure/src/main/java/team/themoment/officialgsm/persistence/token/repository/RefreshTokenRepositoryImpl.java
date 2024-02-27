package team.themoment.officialgsm.persistence.token.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.common.exception.CustomHttpStatus;
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
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenMapper.toDomain(refreshTokenJpaRepository.save(refreshTokenMapper.toEntity(refreshToken)));
    }

    @Override
    public Optional<RefreshToken> findByOauthId(String oauthId) {
        RefreshTokenRedisEntity refreshToken = refreshTokenJpaRepository.findById(oauthId)
                .orElseThrow(() -> new CustomException("유저를 찾을 수 없습니다.", CustomHttpStatus.NOT_FOUND));
        return Optional.ofNullable(refreshTokenMapper.toDomain(refreshToken));
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenJpaRepository.delete(refreshTokenMapper.toEntity(refreshToken));
    }
}
