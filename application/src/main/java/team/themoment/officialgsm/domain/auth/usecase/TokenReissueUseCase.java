package team.themoment.officialgsm.domain.auth.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.themoment.officialgsm.common.annotation.UseCaseWithTransaction;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.common.exception.CustomHttpStatus;
import team.themoment.officialgsm.domain.auth.dto.ReissueTokenDto;
import team.themoment.officialgsm.domain.auth.spi.TokenProvider;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.token.RefreshTokenRepository;
import team.themoment.officialgsm.repository.user.UserRepository;

@Service
@UseCaseWithTransaction
@RequiredArgsConstructor
public class TokenReissueUseCase {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public ReissueTokenDto execute(String token) {
        if (token == null)
            throw new CustomException("쿠키에 리프레시 토큰이 없습니다.", CustomHttpStatus.BADREQUEST);

        String secret = tokenProvider.getRefreshSecert();
        String oauthId = tokenProvider.getRefreshTokenOauthId(token, secret);
        User user = userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new CustomException("유저를 찾을 수 없습니다.", CustomHttpStatus.BADREQUEST));
        RefreshToken refreshToken = refreshTokenRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new CustomException("리프레시 토큰이 유효하지 않습니다.", CustomHttpStatus.BADREQUEST));

        String newAccessToken = tokenProvider.generatedAccessToken(oauthId);
        String newRefreshToken = tokenProvider.generatedRefreshToken(oauthId);

        if (!user.oauthId().equals(refreshToken.oauthId()) || !refreshToken.refreshToken().equals(token) && !tokenProvider.isValidToken(token, secret)) {
            throw new CustomException("리프레시 토큰이 유효하지 않습니다.", CustomHttpStatus.BADREQUEST);
        }

        RefreshToken updatedRefreshToken = refreshToken.updateRefreshToken(newRefreshToken);
        refreshTokenRepository.save(updatedRefreshToken);

        return new ReissueTokenDto(newAccessToken, newRefreshToken);
    }
}
