package team.themoment.officialgsm.domain.auth.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.domain.auth.dto.ReissueTokenDto;
import team.themoment.officialgsm.domain.auth.spi.TokenProvider;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.token.RefreshTokenRepository;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;


@ExtendWith(MockitoExtension.class)
class TokenReissueUseCaseTest {

    @InjectMocks
    private TokenReissueUseCase tokenReissueUseCase;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenProvider tokenProvider;

    @Test
    void execute() {
        // given
        String refreshTokenValue = "0";
        String oauthId = "0";
        String refreshSecret = "0";

        User user = new User(oauthId, "신희성", "s23012@gsm.hs.kr", Role.UNAPPROVED, null, null, null);
        RefreshToken refreshToken = new RefreshToken(oauthId, refreshTokenValue, 0L);

        given(tokenProvider.getRefreshSecert()).willReturn(refreshSecret);
        given(tokenProvider.getRefreshTokenOauthId(refreshTokenValue, refreshSecret)).willReturn(oauthId);

        given(userRepository.findByOauthId(oauthId)).willReturn(Optional.of(user));
        given(refreshTokenRepository.findByOauthId(oauthId)).willReturn(Optional.of(refreshToken));

        given(tokenProvider.generatedAccessToken(oauthId)).willReturn("1");
        given(tokenProvider.generatedRefreshToken(oauthId)).willReturn("1");

        // when
        ReissueTokenDto reissueTokenDto = tokenReissueUseCase.execute(refreshTokenValue);

        // then
        verify(refreshTokenRepository, times(1)).save(refreshToken.updateRefreshToken("1"));

        assertThat(reissueTokenDto.getAccessToken()).isEqualTo("1");
        assertThat(reissueTokenDto.getRefreshToken()).isEqualTo("1");
    }

    @Test
    void execute_refreshTokenIsNull() {
        // given &  when
        assertThrows(CustomException.class, () -> tokenReissueUseCase.execute(null));

        // then
        verify(userRepository, never()).findByOauthId(any());
        verify(refreshTokenRepository, never()).save(any());
    }

    @Test
    void execute_userNotFound() {
        // given
        String refreshTokenValue = "0";
        String oauthId = "0";
        String refreshSecret = "0";

        given(tokenProvider.getRefreshSecert()).willReturn(refreshSecret);
        given(tokenProvider.getRefreshTokenOauthId(refreshTokenValue, refreshSecret)).willReturn(oauthId);

        given(userRepository.findByOauthId(oauthId)).willReturn(Optional.empty());

        // when
        assertThrows(CustomException.class, () -> tokenReissueUseCase.execute(refreshTokenValue));

        // then
        verify(refreshTokenRepository, never()).findByOauthId(any());

        verify(refreshTokenRepository, never()).save(any());
    }

    @Test
    void execute_refreshTokenIsNotValid() {
        // given
        String refreshTokenValue = "0";
        String oauthId = "0";
        String refreshSecret = "0";

        User user = new User(oauthId, "신희성", "s23012@gsm.hs.kr", Role.UNAPPROVED, null, null, null);
        RefreshToken refreshToken = new RefreshToken(oauthId, refreshTokenValue + 1, 0L);

        given(tokenProvider.getRefreshSecert()).willReturn(refreshSecret);
        given(tokenProvider.getRefreshTokenOauthId(refreshTokenValue, refreshSecret)).willReturn(oauthId);

        given(userRepository.findByOauthId(oauthId)).willReturn(Optional.of(user));
        given(refreshTokenRepository.findByOauthId(oauthId)).willReturn(Optional.of(refreshToken));

        // when
        assertThrows(CustomException.class, () -> tokenReissueUseCase.execute(refreshTokenValue));

        // then
        verify(refreshTokenRepository, never()).save(any());
    }
}