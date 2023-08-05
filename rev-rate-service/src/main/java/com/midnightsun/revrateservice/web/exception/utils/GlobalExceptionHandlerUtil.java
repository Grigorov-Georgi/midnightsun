package com.midnightsun.revrateservice.web.exception.utils;

import com.midnightsun.revrateservice.web.exception.dto.ErrorDTO;
import com.midnightsun.revrateservice.web.exception.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.midnightsun.revrateservice.config.LogFilter.TRACKING_ID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalExceptionHandlerUtil {
    public static ErrorDTO buildApiErrorDTO(final String errorUUID,
                                            final ErrorCode errorCode,
                                            final String errorMsg,
                                            final Throwable throwable,
                                            final boolean isDebugMsgEnabled) {
        var errorDTOBuilder = ErrorDTO.builder()
                .uuid(errorUUID)
                .errorCode(errorCode.getErrorCode())
                .requestTrackingId(MDC.get(TRACKING_ID));

        if (isDebugMsgEnabled) {
            errorDTOBuilder
                    .debugMessage(errorMsg)
                    .stackTrace(getStackTrace(throwable));
        }

        return errorDTOBuilder.build();
    }

    private static String getStackTrace(final Throwable throwable) {
        final var sw = new StringWriter();
        final var pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}
