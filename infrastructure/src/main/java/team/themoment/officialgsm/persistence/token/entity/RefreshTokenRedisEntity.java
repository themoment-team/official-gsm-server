package team.themoment.officialgsm.persistence.token.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@RedisHash("refreshToken")
public class RefreshTokenRedisEntity {

    @Id
    private String oauthId;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private Long expiredAt;
}
