package team.themoment.officialgsm.persistence.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.persistence.user.entity.UserJpaEntity;
import team.themoment.officialgsm.persistence.user.mapper.UserMapper;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        return userMapper.toDomain(userJpaRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByOauthId(String oauthId) {
        Optional<UserJpaEntity> user = userJpaRepository.findById(oauthId);
        return user.map(userMapper::toDomain);
    }

    @Override
    public void deleteAll() {
        userJpaRepository.deleteAll();
    }

    @Override
    public List<User> findAllByRoleAndUserNameNotNull(Role role) {
        return userMapper.toDomainList(userJpaRepository.findAllByRoleAndUserNameNotNull(role));
    }
}
