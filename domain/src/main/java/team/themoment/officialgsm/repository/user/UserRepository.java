package team.themoment.officialgsm.repository.user;

import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByOauthId(String oauthId);
    Optional<User> findByOauthIdAndUserNameNotNull(String oauthId);
    void deleteAll();
    void delete(User user);
    List<User> findAllByRoleAndUserNameNotNull(Role role);
}