package team.themoment.officialgsm.global.security.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import static team.themoment.officialgsm.domain.user.Role.*;

import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.persistence.user.entity.UserJpaEntity;
import team.themoment.officialgsm.persistence.user.repository.UserJpaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegateOauth2UserService;
    private final UserJpaRepository userJpaRepository;

    @Autowired
    public OAuthService(UserJpaRepository userJpaRepository) {
        this.delegateOauth2UserService = new DefaultOAuth2UserService();
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegateOauth2UserService.loadUser(userRequest);

        String providerId = oAuth2User.getName();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        UserJpaEntity user = getUser(providerId, email, name);
        String nameAttribute = "id";
        Long id = user.getUserSeq();
        Role role = user.getRole();
        Map<String, Object> attributes = new HashMap<>(Map.of(
                nameAttribute, id,
                "oauthId", providerId,
                "role", role,
                "userEmail", email,
                "userName", name,
                "requestedAt", LocalDateTime.now()
        ));
        Collection<GrantedAuthority> authorities = new ArrayList<>(oAuth2User.getAuthorities());
        authorities.add(new SimpleGrantedAuthority(role.name()));

        return new UserInfo(authorities, attributes, nameAttribute);
    }

    private UserJpaEntity getUser(String providerId, String email, String name) {
        UserJpaEntity savedUser = userJpaRepository.findByOauthId(providerId)
                .orElse(null);
        if (savedUser == null) {
            UserJpaEntity user = UserJpaEntity.builder()
                    .oauthId(providerId)
                    .userEmail(email)
                    .userName(name)
                    .role(UNAPPROVED)
                    .requestedAt(LocalDateTime.now())
                    .build();
            return userJpaRepository.save(user);
        }
        return savedUser;
    }
}
