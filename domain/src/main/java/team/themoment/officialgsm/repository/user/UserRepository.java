package team.themoment.officialgsm.repository.user;

import team.themoment.officialgsm.domain.user.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findByOauthId(String oauthId);
}
