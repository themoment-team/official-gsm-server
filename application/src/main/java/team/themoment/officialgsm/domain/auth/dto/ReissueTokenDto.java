package team.themoment.officialgsm.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReissueTokenDto {
    private String accessToken;
    private String refreshToken;
}
