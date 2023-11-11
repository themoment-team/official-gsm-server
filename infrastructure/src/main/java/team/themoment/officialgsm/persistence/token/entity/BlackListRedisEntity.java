package team.themoment.officialgsm.persistence.token.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("blackList")
public class BlackListRedisEntity {

    @Id
    private String oauthId;

    @Indexed
    private String accessToken;

    @TimeToLive
    private Long timeToLive;
}
