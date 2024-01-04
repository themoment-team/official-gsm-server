package team.themoment.officialgsm.global.security.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team.themoment.officialgsm.common.util.ConstantsUtil;
import team.themoment.officialgsm.common.util.CookieUtil;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.global.security.jwt.JwtTokenProvider;
import team.themoment.officialgsm.global.security.manager.EmailManager;
import team.themoment.officialgsm.persistence.user.entity.UserJpaEntity;
import team.themoment.officialgsm.persistence.user.mapper.UserMapper;
import team.themoment.officialgsm.repository.token.RefreshTokenRepository;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static team.themoment.officialgsm.domain.user.Role.UNAPPROVED;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegateOauth2UserService = new DefaultOAuth2UserService();
    private final HttpServletResponse httpServletResponse;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailManager emailManager;
    private final CookieUtil cookieUtil;
    private final UserMapper userMapper;

    @Value("${domain}")
    private String schoolDomain;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegateOauth2UserService.loadUser(userRequest);

        emailCheckLogic(oAuth2User.getAttribute("email"));

        String providerId = oAuth2User.getName();
        String email = oAuth2User.getAttribute("email");

        User user = getUser(providerId, email);
        String nameAttribute = "oauthId";
        Role role = user.role();
        Map<String, Object> attributes = new HashMap<>(Map.of(
                "oauthId", providerId,
                "role", role,
                "userEmail", email,
                "requestedAt", LocalDateTime.now()
        ));
        Collection<GrantedAuthority> authorities = new ArrayList<>(oAuth2User.getAuthorities());
        authorities.add(new SimpleGrantedAuthority(role.name()));

        cookieLogic(userMapper.toEntity(user));

        return new UserInfo(authorities, attributes, nameAttribute);
    }

    private User getUser(String providerId, String email) {
        User savedUser = userRepository.findByOauthId(providerId)
                .orElse(null);

        if (savedUser == null) {
            UserJpaEntity user = UserJpaEntity.builder()
                    .oauthId(providerId)
                    .userEmail(email)
                    .userName(null)
                    .role(UNAPPROVED)
                    .requestedAt(LocalDateTime.now())
                    .build();
            return userRepository.save(userMapper.toDomain(user));
        }
        return savedUser;
    }

    private void emailCheckLogic(String email){
        String emailDomain;

        try {
            emailDomain = emailManager.getOauthEmailDomain(email);
        }catch (IllegalArgumentException e){
            throw new OAuth2AuthenticationException(e.getMessage());
        }

        if (!emailDomain.equals(schoolDomain)) {
            throw new OAuth2AuthenticationException("학교 이메일이 아닙니다.");
        }
    }

    private void cookieLogic(UserJpaEntity user){
        String accessToken = jwtTokenProvider.generatedAccessToken(user.getOauthId());
        String refreshToken = jwtTokenProvider.generatedRefreshToken(user.getOauthId());
        cookieUtil.addTokenCookie(httpServletResponse, ConstantsUtil.accessToken, accessToken, jwtTokenProvider.getACCESS_TOKEN_EXPIRE_TIME(), true);
        cookieUtil.addTokenCookie(httpServletResponse, ConstantsUtil.refreshToken, refreshToken, jwtTokenProvider.getREFRESH_TOKEN_EXPIRE_TIME(), true);
        RefreshToken entityToRedis = new RefreshToken(user.getOauthId(), refreshToken, jwtTokenProvider.getREFRESH_TOKEN_EXPIRE_TIME());
        refreshTokenRepository.save(entityToRedis);
    }
}
