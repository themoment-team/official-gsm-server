package team.themoment.officialgsm.persistence.token.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.themoment.officialgsm.domain.token.BlackList;
import team.themoment.officialgsm.persistence.token.entity.BlackListRedisEntity;
import team.themoment.officialgsm.persistence.token.mapper.BlackListMapper;
import team.themoment.officialgsm.repository.token.BlackListRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BlackListRepositoryImpl implements BlackListRepository {
    private final BlackListJpaRepository blackListJpaRepository;
    private final BlackListMapper blackListMapper;

    @Override
    public BlackList save(BlackList blackList) {
        return blackListMapper.toDomain(blackListJpaRepository.save(blackListMapper.toEntity(blackList)));
    }

    @Override
    public Optional<BlackList> findByAccessToken(String accessToken) {
        Optional<BlackListRedisEntity> blackList = blackListJpaRepository.findByAccessToken(accessToken);
        return blackList.map(blackListMapper::toDomain);
    }
}
