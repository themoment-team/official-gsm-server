package team.themoment.officialgsm.admin.auth.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.domain.auth.dto.UserNameDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-27T17:02:57+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class UserNameDataMapperImpl implements UserNameDataMapper {

    @Override
    public UserNameDto toDto(UserNameModifyRequest userNameModifyRequest) {
        if ( userNameModifyRequest == null ) {
            return null;
        }

        UserNameDto userNameDto = new UserNameDto();

        return userNameDto;
    }
}
