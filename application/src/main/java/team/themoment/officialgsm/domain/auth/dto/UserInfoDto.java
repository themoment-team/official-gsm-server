package team.themoment.officialgsm.domain.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.officialgsm.domain.user.Role;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private String userName;
    private Role role;
    private String userEmail;
}
