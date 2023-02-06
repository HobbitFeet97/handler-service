package com.TryingThingsOut.handlerservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppianQuestion {
    private String id;
    private String label;
    private String bdp;
    private String[] value;
    private String type;
    private Boolean visible;
    private String visibleExpression;
    private String visibleJSExpression;
}
