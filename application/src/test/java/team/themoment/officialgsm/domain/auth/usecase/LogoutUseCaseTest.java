package team.themoment.officialgsm.domain.auth.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.domain.token.BlackList;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.token.BlackListRepository;
import team.themoment.officialgsm.repository.token.RefreshTokenRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutUseCaseTest {

    @InjectMocks
    private LogoutUseCase logoutUseCase;

    @Mock
    private BlackListRepository blackListRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private RedisTemplate redisTemplate;

    @Test
    void execute() {
        // given
        String accessTokenValue = "0";
        String refreshTokenValue = "0";
        String oauthId = "0";

        User user = new User(oauthId, "신희성", "s23012@gsm.hs.kr", Role.UNAPPROVED, null, null, null);
        RefreshToken refreshToken = new RefreshToken(oauthId, refreshTokenValue, 7200L);

        given(refreshTokenRepository.findByOauthId(oauthId)).willReturn(Optional.of(refreshToken));

        given(redisTemplate.opsForValue()).willReturn(mock(ValueOperations.class));

        // when
        logoutUseCase.execute(accessTokenValue, user);

        // then
        verify(refreshTokenRepository, times(1)).delete(refreshToken);
        verify(blackListRepository, times(1)).save(new BlackList(oauthId, accessTokenValue, 7200L));
    }

    @Test
    void execute_refreshTokenNotFound() {
        // given
        String accessTokenValue = "0";
        String oauthId = "0";

        User user = new User(oauthId, "신희성", "s23012@gsm.hs.kr", Role.UNAPPROVED, null, null, null);

        given(refreshTokenRepository.findByOauthId(oauthId)).willReturn(Optional.empty());

        // when
        assertThrows(CustomException.class, () -> logoutUseCase.execute(accessTokenValue, user));

        // then
        verify(refreshTokenRepository, never()).delete(any());
        verify(blackListRepository, never()).save(any());
    }
}