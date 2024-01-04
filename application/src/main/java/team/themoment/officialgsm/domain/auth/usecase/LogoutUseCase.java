package team.themoment.officialgsm.domain.auth.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.officialgsm.common.annotation.UseCaseWithTransaction;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.common.exception.CustomHttpStatus;
import team.themoment.officialgsm.domain.token.BlackList;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.token.BlackListRepository;
import team.themoment.officialgsm.repository.token.RefreshTokenRepository;
import org.springframework.data.redis.core.RedisTemplate;

@Service
@UseCaseWithTransaction
@RequiredArgsConstructor
public class LogoutUseCase {
    private final BlackListRepository blackListRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate redisTemplate;

    public void execute(String accessToken, User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByOauthId(user.oauthId())
                .orElseThrow(() -> new CustomException("리프레시 토큰을 찾을 수 없습니다.", CustomHttpStatus.NOT_FOUND));
        refreshTokenRepository.delete(refreshToken);
        saveBlackList(user.oauthId(), accessToken);
    }

    private void saveBlackList(String oauthId, String accessToken){
        if(redisTemplate.opsForValue().get(accessToken) != null){
            throw new CustomException("이미 블랙리스트에 존재합니다.", CustomHttpStatus.CONFLICT);
        }
        BlackList blackList = BlackList.builder()
                .oauthId(oauthId)
                .accessToken(accessToken)
                .timeToLive(7200L)
                .build();
        blackListRepository.save(blackList);
    }
}
