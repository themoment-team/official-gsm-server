package team.themoment.officialgsm.admin.controller.approve.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import team.themoment.officialgsm.admin.controller.approve.dto.response.UnapprovedListResponse;
import team.themoment.officialgsm.domain.approve.dto.UnapprovedListDto;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ApproveDataMapper {
    List<UnapprovedListResponse> toUnapprovedListResponse(List<UnapprovedListDto> unapprovedListDto);
}


