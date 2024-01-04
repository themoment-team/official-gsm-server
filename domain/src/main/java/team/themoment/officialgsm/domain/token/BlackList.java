package team.themoment.officialgsm.domain.token;

import lombok.Builder;

@Builder
public record BlackList(
        String oauthId,
        String accessToken,
        Long timeToLive
) {
}
