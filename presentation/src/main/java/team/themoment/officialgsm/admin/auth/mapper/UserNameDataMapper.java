package team.themoment.officialgsm.admin.auth.mapper;

import org.mapstruct.*;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.domain.auth.dto.UserNameDto;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserNameDataMapper {
    UserNameDto toDto(UserNameModifyRequest userNameModifyRequest);
}