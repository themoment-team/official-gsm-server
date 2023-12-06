package team.themoment.officialgsm.persistence.token.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.persistence.token.entity.RefreshTokenRedisEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-06T23:15:09+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class RefreshTokenMapperImpl implements RefreshTokenMapper {

    @Override
    public RefreshTokenRedisEntity toEntity(RefreshToken refreshToken) {
        if ( refreshToken == null ) {
            return null;
        }

        RefreshTokenRedisEntity.RefreshTokenRedisEntityBuilder refreshTokenRedisEntity = RefreshTokenRedisEntity.builder();

        refreshTokenRedisEntity.oauthId( refreshToken.oauthId() );
        refreshTokenRedisEntity.refreshToken( refreshToken.refreshToken() );
        refreshTokenRedisEntity.expiredAt( refreshToken.expiredAt() );

        return refreshTokenRedisEntity.build();
    }

    @Override
    public RefreshToken toDomain(RefreshTokenRedisEntity refreshTokenRedisEntity) {
        if ( refreshTokenRedisEntity == null ) {
            return null;
        }

        RefreshToken.RefreshTokenBuilder refreshToken = RefreshToken.builder();

        refreshToken.oauthId( refreshTokenRedisEntity.getOauthId() );
        refreshToken.refreshToken( refreshTokenRedisEntity.getRefreshToken() );
        refreshToken.expiredAt( refreshTokenRedisEntity.getExpiredAt() );

        return refreshToken.build();
    }
}
