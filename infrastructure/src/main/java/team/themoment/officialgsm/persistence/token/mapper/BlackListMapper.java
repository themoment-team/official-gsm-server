package team.themoment.officialgsm.persistence.token.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import team.themoment.officialgsm.domain.token.BlackList;
import team.themoment.officialgsm.persistence.token.entity.BlackListRedisEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BlackListMapper {
    BlackListRedisEntity toEntity(BlackList blackList);

    BlackList toDomain(BlackListRedisEntity blackListRedisEntity);
}

