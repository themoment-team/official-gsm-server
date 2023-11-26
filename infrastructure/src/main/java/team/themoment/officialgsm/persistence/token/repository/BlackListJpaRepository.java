package team.themoment.officialgsm.persistence.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import team.themoment.officialgsm.domain.token.BlackList;
import team.themoment.officialgsm.persistence.token.entity.BlackListRedisEntity;

import java.util.Optional;

public interface BlackListJpaRepository extends CrudRepository<BlackListRedisEntity, String> {
    Optional<BlackList> findByAccessToken(String accessToken);
}
