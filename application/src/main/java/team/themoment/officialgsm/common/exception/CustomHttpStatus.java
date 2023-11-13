package team.themoment.officialgsm.common.exception;

public enum CustomHttpStatus {

    OK(200, "OK"),
    CREATED(201, "Created"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int value;
    private final String reasonPhrase;

    CustomHttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return this.value;
    }
}
