package team.themoment.officialgsm.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("ADMIN"),
    UNAPPROVED("UNAPPROVED");

    private final String key;
}
