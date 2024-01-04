package team.themoment.officialgsm.admin.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.officialgsm.domain.user.Role;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {
    private String userName;
    private Role role;
    private String userEmail;
}
