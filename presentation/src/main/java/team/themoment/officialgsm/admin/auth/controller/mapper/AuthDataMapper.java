package team.themoment.officialgsm.admin.auth.controller.mapper;

import org.mapstruct.*;
import team.themoment.officialgsm.admin.auth.controller.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.auth.controller.dto.response.UserInfoResponse;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.auth.dto.UserNameDto;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface AuthDataMapper {
    @Mapping(target = "userName", source = "userNameModifyRequest.userName")
    UserNameDto toDto(UserNameModifyRequest userNameModifyRequest);
    UserInfoResponse toInfoResponse(UserInfoDto userInfoDto);
}