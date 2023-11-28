package team.themoment.officialgsm.persistence.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import team.themoment.officialgsm.persistence.user.entity.UserJpaEntity;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, String> {
}
