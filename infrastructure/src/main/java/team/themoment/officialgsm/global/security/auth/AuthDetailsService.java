package team.themoment.officialgsm.global.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.themoment.officialgsm.common.exception.CustomException;
import team.themoment.officialgsm.common.exception.CustomHttpStatus;
import team.themoment.officialgsm.persistence.user.repository.UserJpaRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthDetailsService implements UserDetailsService {
    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String oauthId) throws UsernameNotFoundException {
        return userJpaRepository.findByOauthId(oauthId)
                .map(AuthDetails::new)
                .orElseThrow(() -> new CustomException("사용자를 찾을 수 없습니다.", CustomHttpStatus.NOT_FOUND));
    }
}
