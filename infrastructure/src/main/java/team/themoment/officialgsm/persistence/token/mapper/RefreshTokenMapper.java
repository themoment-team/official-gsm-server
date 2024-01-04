package team.themoment.officialgsm.persistence.token.mapper;

import org.apache.logging.log4j.message.MessageCollectionMessage;
import org.mapstruct.*;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.persistence.token.entity.RefreshTokenRedisEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RefreshTokenMapper {
    RefreshTokenRedisEntity toEntity(RefreshToken refreshToken);
}
