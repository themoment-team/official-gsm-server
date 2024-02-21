package team.themoment.officialgsm.admin.auth.controller.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.admin.auth.controller.dto.response.UserInfoResponse;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-22T04:47:20+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class AuthDataMapperImpl implements AuthDataMapper {

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
}
