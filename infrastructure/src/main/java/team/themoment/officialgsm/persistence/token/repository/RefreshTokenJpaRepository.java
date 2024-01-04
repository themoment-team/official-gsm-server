package team.themoment.officialgsm.persistence.token.repository;

import org.springframework.data.repository.CrudRepository;
import team.themoment.officialgsm.persistence.token.entity.RefreshTokenRedisEntity;

public interface RefreshTokenJpaRepository extends CrudRepository<RefreshTokenRedisEntity, String> {
}
