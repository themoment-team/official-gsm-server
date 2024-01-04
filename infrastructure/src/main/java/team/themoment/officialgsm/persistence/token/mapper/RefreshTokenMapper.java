package team.themoment.officialgsm.persistence.token.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.persistence.token.entity.RefreshTokenRedisEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RefreshTokenMapper {
    RefreshTokenRedisEntity toEntity(RefreshToken refreshToken);
    RefreshToken toDomain(RefreshTokenRedisEntity refreshTokenRedisEntity);
}