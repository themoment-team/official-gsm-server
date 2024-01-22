package team.themoment.officialgsm.global.security.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import team.themoment.officialgsm.common.util.EmailUtil;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.global.security.jwt.JwtTokenProvider;
import team.themoment.officialgsm.persistence.user.entity.UserJpaEntity;
import team.themoment.officialgsm.persistence.user.repository.UserJpaRepository;
import team.themoment.officialgsm.repository.token.RefreshTokenRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static team.themoment.officialgsm.domain.user.Role.ADMIN;
import static team.themoment.officialgsm.domain.user.Role.UNAPPROVED;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegateOauth2UserService = new DefaultOAuth2UserService();
    private final HttpServletResponse httpServletResponse;
    private final UserJpaRepository userJpaRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailUtil emailUtil;
    private final CookieUtil cookieUtil;

    @Value("${domain}")
    private String schoolDomain;

    @Value("${site-address}")
    private String siteAddress;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegateOauth2UserService.loadUser(userRequest);

        emailCheckLogic(oAuth2User.getAttribute("email"));

        String providerId = oAuth2User.getName();
        String email = oAuth2User.getAttribute("email");

        UserJpaEntity user = getUser(providerId, email);
        String nameAttribute = "id";
        Long id = user.getUserSeq();
        Role role = user.getRole();
        Map<String, Object> attributes = new HashMap<>(Map.of(
                nameAttribute, id,
                "oauthId", providerId,
                "role", role,
                "userEmail", email,
                "requestedAt", LocalDateTime.now()
        ));
        Collection<GrantedAuthority> authorities = new ArrayList<>(oAuth2User.getAuthorities());
        authorities.add(new SimpleGrantedAuthority(role.name()));

        cookieLogic(user);

        redirectUser(user);

        return new UserInfo(authorities, attributes, nameAttribute);
    }

    private UserJpaEntity getUser(String providerId, String email) {
        UserJpaEntity savedUser = userJpaRepository.findByOauthId(providerId)
                .orElse(null);
        if (savedUser == null) {
            UserJpaEntity user = UserJpaEntity.builder()
                    .oauthId(providerId)
                    .userEmail(email)
                    .role(UNAPPROVED)
                    .requestedAt(LocalDateTime.now())
                    .build();
            return userJpaRepository.save(user);
        }
        return savedUser;
    }

    private void emailCheckLogic(String email){
        String emailDomain;

        try {
            emailDomain = emailUtil.getOauthEmailDomain(email);
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

    private void redirectUser(UserJpaEntity user){
        Role role = user.getRole();
        String userName = user.getUserName();

        if (role == UNAPPROVED && userName != null) {
            redirectPendingPage();
        } else if (role == ADMIN) {
            redirectHomePage();
        } else {
            redirectSignupPage();
        }
    }

    private void redirectPendingPage(){
        try {
            httpServletResponse.sendRedirect(siteAddress + "/auth/signup/pending");
        } catch (IOException e) {
            log.error(siteAddress + "/auth/signup/pending 페이지로 redirect 도중 에러가 발생했습니다.", e);
        }
    }

    private void redirectHomePage(){
        try {
            httpServletResponse.sendRedirect(siteAddress);
        } catch (IOException e) {
            log.error(siteAddress + "페이지로 redirect 도중 에러가 발생했습니다.", e);
        }
    }

    private void redirectSignupPage(){
        try {
            httpServletResponse.sendRedirect(siteAddress + "/auth/signup");
        } catch (IOException e) {
            log.error(siteAddress + "/auth/signup 페이지로 redirect 도중 에러가 발생했습니다.", e);
        }
    }
}
