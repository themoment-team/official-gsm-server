package team.themoment.officialgsm.persistence.token.mapper;

import org.mapstruct.*;
import team.themoment.officialgsm.domain.token.RefreshToken;
import team.themoment.officialgsm.persistence.token.entity.RefreshTokenRedisEntity;

import javax.swing.text.html.Option;
import java.util.Optional;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RefreshTokenMapper {
    RefreshTokenRedisEntity toEntity(RefreshToken refreshToken);
    RefreshToken toDomain(RefreshTokenRedisEntity refreshTokenRedisEntity);
}
