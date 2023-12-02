package team.themoment.officialgsm.persistence.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.persistence.user.entity.UserJpaEntity;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, String> {
    List<UserJpaEntity> findAllByRoleAndUserNameNotNull(Role role);
}
