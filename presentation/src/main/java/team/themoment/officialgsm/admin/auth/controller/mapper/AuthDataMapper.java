package team.themoment.officialgsm.admin.auth.controller.mapper;

import org.mapstruct.*;
import team.themoment.officialgsm.admin.auth.controller.dto.response.UserInfoResponse;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface AuthDataMapper {
    UserInfoResponse toInfoResponse(UserInfoDto userInfoDto);
}