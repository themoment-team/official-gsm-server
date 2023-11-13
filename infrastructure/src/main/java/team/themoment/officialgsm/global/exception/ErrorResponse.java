package team.themoment.officialgsm.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import team.themoment.officialgsm.common.exception.CustomHttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    private final String formatNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    private final String detailMessage;

    public static ResponseEntity<ErrorResponse> toResponseEntity(String detailMessage, CustomHttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus.getValue())
                .body(ErrorResponse.builder()
                        .detailMessage(detailMessage)
                        .build()
                );
    }
}
