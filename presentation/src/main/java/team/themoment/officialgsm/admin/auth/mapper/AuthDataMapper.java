package team.themoment.officialgsm.admin.auth.mapper;

import org.mapstruct.*;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.auth.dto.response.UnapprovedListResponse;
import team.themoment.officialgsm.admin.auth.dto.response.UserInfoResponse;
import team.themoment.officialgsm.domain.auth.dto.UnapprovedListDto;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.auth.dto.UserNameDto;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface AuthDataMapper {
    @Mapping(target = "userName", source = "userNameModifyRequest.userName")
    UserNameDto toDto(UserNameModifyRequest userNameModifyRequest);
    UserInfoResponse toInfoResponse(UserInfoDto userInfoDto);
    List<UnapprovedListResponse> toUnapprovedListResponse(List<UnapprovedListDto> unapprovedListDto);
}


