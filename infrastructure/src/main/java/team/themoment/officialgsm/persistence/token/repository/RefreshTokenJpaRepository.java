package team.themoment.officialgsm.persistence.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import team.themoment.officialgsm.persistence.token.entity.RefreshTokenRedisEntity;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenRedisEntity, String> {
}
