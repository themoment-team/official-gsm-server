package team.themoment.officialgsm.persistence.user.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.persistence.user.entity.UserJpaEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-06T23:15:09+0900",
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

        User.UserBuilder user = User.builder();

        user.oauthId( userJpaEntity.getOauthId() );
        user.userName( userJpaEntity.getUserName() );
        user.userEmail( userJpaEntity.getUserEmail() );
        user.role( userJpaEntity.getRole() );
        user.grantor( userJpaEntityToUser( userJpaEntity.getGrantor() ) );
        user.approvedAt( userJpaEntity.getApprovedAt() );
        user.requestedAt( userJpaEntity.getRequestedAt() );

        return user.build();
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

        User.UserBuilder user = User.builder();

        user.oauthId( userJpaEntity.getOauthId() );
        user.userName( userJpaEntity.getUserName() );
        user.userEmail( userJpaEntity.getUserEmail() );
        user.role( userJpaEntity.getRole() );
        user.grantor( toDomain( userJpaEntity.getGrantor() ) );
        user.approvedAt( userJpaEntity.getApprovedAt() );
        user.requestedAt( userJpaEntity.getRequestedAt() );

        return user.build();
    }
}
