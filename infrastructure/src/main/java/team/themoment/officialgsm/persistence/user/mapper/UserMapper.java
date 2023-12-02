package team.themoment.officialgsm.persistence.user.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.persistence.user.entity.UserJpaEntity;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    UserJpaEntity toEntity(User user);
    User toDomain(UserJpaEntity userJpaEntity);
    List<User> toDomainList(List<UserJpaEntity> userJpaEntity);
}
