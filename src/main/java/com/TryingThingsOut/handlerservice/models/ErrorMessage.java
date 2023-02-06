package com.TryingThingsOut.handlerservice.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorMessage {
    private String timestamp;
    private String errorCode;
    private String errorMessage;
}
