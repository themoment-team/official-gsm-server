package team.themoment.officialgsm.client.auth.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserNameModifyRequest {
    @Pattern(regexp = "^.{2,5}$")
    private String userName;
}
