package team.themoment.officialgsm.persistence.user.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.persistence.user.entity.UserJpaEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-22T04:35:01+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserJpaEntity toEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserJpaEntity.UserJpaEntityBuilder userJpaEntity = UserJpaEntity.builder();

        userJpaEntity.oauthId( user.oauthId() );
        userJpaEntity.userName( user.userName() );
        userJpaEntity.userEmail( user.userEmail() );
        userJpaEntity.role( user.role() );
        userJpaEntity.grantor( userToUserJpaEntity( user.grantor() ) );
        userJpaEntity.approvedAt( user.approvedAt() );
        userJpaEntity.requestedAt( user.requestedAt() );

        return userJpaEntity.build();
    }

    @Override
    public User toDomain(UserJpaEntity userJpaEntity) {
        if ( userJpaEntity == null ) {
            return null;
        }

        String oauthId = null;
        String userName = null;
        String userEmail = null;
        Role role = null;
        User grantor = null;
        LocalDateTime approvedAt = null;
        LocalDateTime requestedAt = null;

        oauthId = userJpaEntity.getOauthId();
        userName = userJpaEntity.getUserName();
        userEmail = userJpaEntity.getUserEmail();
        role = userJpaEntity.getRole();
        grantor = userJpaEntityToUser( userJpaEntity.getGrantor() );
        approvedAt = userJpaEntity.getApprovedAt();
        requestedAt = userJpaEntity.getRequestedAt();

        User user = new User( oauthId, userName, userEmail, role, grantor, approvedAt, requestedAt );

        return user;
    }

    @Override
    public List<User> toDomainList(List<UserJpaEntity> userJpaEntity) {
        if ( userJpaEntity == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userJpaEntity.size() );
        for ( UserJpaEntity userJpaEntity1 : userJpaEntity ) {
            list.add( toDomain( userJpaEntity1 ) );
        }

        return list;
    }

    protected UserJpaEntity userToUserJpaEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserJpaEntity.UserJpaEntityBuilder userJpaEntity = UserJpaEntity.builder();

        userJpaEntity.oauthId( user.oauthId() );
        userJpaEntity.userName( user.userName() );
        userJpaEntity.userEmail( user.userEmail() );
        userJpaEntity.role( user.role() );
        userJpaEntity.grantor( toEntity( user.grantor() ) );
        userJpaEntity.approvedAt( user.approvedAt() );
        userJpaEntity.requestedAt( user.requestedAt() );

        return userJpaEntity.build();
    }

    protected User userJpaEntityToUser(UserJpaEntity userJpaEntity) {
        if ( userJpaEntity == null ) {
            return null;
        }

        String oauthId = null;
        String userName = null;
        String userEmail = null;
        Role role = null;
        User grantor = null;
        LocalDateTime approvedAt = null;
        LocalDateTime requestedAt = null;

        oauthId = userJpaEntity.getOauthId();
        userName = userJpaEntity.getUserName();
        userEmail = userJpaEntity.getUserEmail();
        role = userJpaEntity.getRole();
        grantor = toDomain( userJpaEntity.getGrantor() );
        approvedAt = userJpaEntity.getApprovedAt();
        requestedAt = userJpaEntity.getRequestedAt();

        User user = new User( oauthId, userName, userEmail, role, grantor, approvedAt, requestedAt );

        return user;
    }
}
