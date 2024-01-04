package team.themoment.officialgsm.domain.auth.spi;

public interface TokenProvider {

    String generatedAccessToken(String oauthId);

    String generatedRefreshToken(String oauthId);

    String getTokenOauthId(String token, String secret);

    String getRefreshTokenOauthId(String token, String secret);

    long getExpiredAtAccessTokenToLong();

    boolean isExpiredToken(String token, String secret);

    boolean isValidToken(String token, String secret);

    String getRefreshSecert();

    long getACCESS_TOKEN_EXPIRE_TIME();

    long getREFRESH_TOKEN_EXPIRE_TIME();
}