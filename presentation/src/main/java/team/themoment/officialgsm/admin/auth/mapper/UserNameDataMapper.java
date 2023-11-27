package team.themoment.officialgsm.admin.auth.mapper;

import org.mapstruct.*;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.auth.dto.response.UserInfoResponse;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.auth.dto.UserNameDto;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserNameDataMapper {
    UserNameDto toDto(UserNameModifyRequest userNameModifyRequest);
    UserInfoResponse toInfoResponse(UserInfoDto userInfoDto);
}