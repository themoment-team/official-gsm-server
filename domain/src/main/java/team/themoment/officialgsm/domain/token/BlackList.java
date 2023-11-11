package team.themoment.officialgsm.domain.token;

public record BlackList(

        String oauthId,
        String accessToken,
        Long timeToLive
) {
}
