package com.TryingThingsOut.handlerservice.components.impl;

import com.TryingThingsOut.handlerservice.components.api.TranslatorApi;
import com.TryingThingsOut.handlerservice.utils.AttributeUtil;
import com.TryingThingsOut.handlerservice.utils.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.Map;

@Slf4j
public class TranslatorImpl implements TranslatorApi{

    private final String templateLocation;
    private final String mapperLocation;
    private final AttributeUtil attributeUtil = new AttributeUtil();

    public TranslatorImpl(String templateLocation, String mapperLocation) {
        this.templateLocation = templateLocation;
        this.mapperLocation = mapperLocation;
    }

    public String translate(JSONObject objectToTranslate) {
        String template = ResourceUtil.readClasspathFile(templateLocation);
        Map<String, Object> mappedAttributes = attributeUtil.getMappedAttributes(objectToTranslate, mapperLocation);
        log.info("Mapping template for object: {} - with attributes: {}", objectToTranslate, mappedAttributes);
        String returnString = StrSubstitutor.replace(template, mappedAttributes);
        return returnString;
    }
}
