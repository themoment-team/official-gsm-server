package team.themoment.officialgsm.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final String detailMessage;
    private final CustomHttpStatus httpStatus;

    public CustomException(String detailMessage, CustomHttpStatus httpStatus) {
        this.detailMessage = detailMessage;
        this.httpStatus = httpStatus;
    }
}
