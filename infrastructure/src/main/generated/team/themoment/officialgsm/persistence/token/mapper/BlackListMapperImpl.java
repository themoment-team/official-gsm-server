package team.themoment.officialgsm.persistence.token.mapper;

import java.util.Optional;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.domain.token.BlackList;
import team.themoment.officialgsm.persistence.token.entity.BlackListRedisEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-01T23:54:17+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class BlackListMapperImpl implements BlackListMapper {

    @Override
    public BlackListRedisEntity toEntity(BlackList blackList) {
        if ( blackList == null ) {
            return null;
        }

        BlackListRedisEntity.BlackListRedisEntityBuilder blackListRedisEntity = BlackListRedisEntity.builder();

        blackListRedisEntity.oauthId( blackList.oauthId() );
        blackListRedisEntity.accessToken( blackList.accessToken() );
        blackListRedisEntity.timeToLive( blackList.timeToLive() );

        return blackListRedisEntity.build();
    }

    @Override
    public BlackList toDomain(Optional<BlackListRedisEntity> blackListRedisEntity) {
        if ( blackListRedisEntity == null ) {
            return null;
        }

        BlackList.BlackListBuilder blackList = BlackList.builder();

        return blackList.build();
    }
}
