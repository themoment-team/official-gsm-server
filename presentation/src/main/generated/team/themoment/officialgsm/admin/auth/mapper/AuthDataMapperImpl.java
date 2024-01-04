package team.themoment.officialgsm.admin.auth.mapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.auth.dto.response.UnapprovedListResponse;
import team.themoment.officialgsm.admin.auth.dto.response.UserInfoResponse;
import team.themoment.officialgsm.domain.auth.dto.UnapprovedListDto;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.auth.dto.UserNameDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-04T18:01:38+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class AuthDataMapperImpl implements AuthDataMapper {

    @Override
    public UserNameDto toDto(UserNameModifyRequest userNameModifyRequest) {
        if ( userNameModifyRequest == null ) {
            return null;
        }

        UserNameDto userNameDto = new UserNameDto();

        userNameDto.setUserName( userNameModifyRequest.getUserName() );

        return userNameDto;
    }

    @Override
    public UserInfoResponse toInfoResponse(UserInfoDto userInfoDto) {
        if ( userInfoDto == null ) {
            return null;
        }

        UserInfoResponse.UserInfoResponseBuilder userInfoResponse = UserInfoResponse.builder();

        userInfoResponse.userName( userInfoDto.getUserName() );
        userInfoResponse.role( userInfoDto.getRole() );
        userInfoResponse.userEmail( userInfoDto.getUserEmail() );

        return userInfoResponse.build();
    }

    @Override
    public List<UnapprovedListResponse> toUnapprovedListResponse(List<UnapprovedListDto> unapprovedListDto) {
        if ( unapprovedListDto == null ) {
            return null;
        }

        List<UnapprovedListResponse> list = new ArrayList<UnapprovedListResponse>( unapprovedListDto.size() );
        for ( UnapprovedListDto unapprovedListDto1 : unapprovedListDto ) {
            list.add( unapprovedListDtoToUnapprovedListResponse( unapprovedListDto1 ) );
        }

        return list;
    }

    protected UnapprovedListResponse unapprovedListDtoToUnapprovedListResponse(UnapprovedListDto unapprovedListDto) {
        if ( unapprovedListDto == null ) {
            return null;
        }

        UnapprovedListResponse.UnapprovedListResponseBuilder unapprovedListResponse = UnapprovedListResponse.builder();

        unapprovedListResponse.userSeq( unapprovedListDto.getUserSeq() );
        unapprovedListResponse.userName( unapprovedListDto.getUserName() );
        unapprovedListResponse.role( unapprovedListDto.getRole() );
        if ( unapprovedListDto.getRequestedAt() != null ) {
            unapprovedListResponse.requestedAt( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( unapprovedListDto.getRequestedAt() ) );
        }

        return unapprovedListResponse.build();
    }
}
