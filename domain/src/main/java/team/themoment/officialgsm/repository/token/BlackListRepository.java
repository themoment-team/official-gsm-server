package team.themoment.officialgsm.repository.token;


import team.themoment.officialgsm.domain.token.BlackList;

import java.util.Optional;

public interface BlackListRepository {
    BlackList save(BlackList blackList);
    Optional<BlackList> findByAccessToken(String accessToken);
}
