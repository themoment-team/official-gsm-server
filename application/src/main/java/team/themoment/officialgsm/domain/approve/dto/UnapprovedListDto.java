package team.themoment.officialgsm.domain.approve.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.themoment.officialgsm.domain.user.Role;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnapprovedListDto {
    private String userSeq;
    private String userName;
    private Role role;
    private LocalDateTime requestedAt;
}
