package team.themoment.officialgsm.persistence.token.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.themoment.officialgsm.domain.token.BlackList;
import team.themoment.officialgsm.persistence.token.entity.BlackListRedisEntity;
import team.themoment.officialgsm.repository.token.BlackListRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BlackListRepositoryImpl implements BlackListRepository {
    private final BlackListJpaRepository blackListJpaRepository;

    @Override
    public void save(BlackList blackList) {
        blackListJpaRepository.save(BlackListRedisEntity.builder()
                        .accessToken(blackList.accessToken())
                        .oauthId(blackList.oauthId())
                        .timeToLive(blackList.timeToLive())
                .build());
    }

    @Override
    public Optional<BlackList> findByAccessToken(String accessToken) {
        return blackListJpaRepository.findByAccessToken(accessToken);
    }
}
