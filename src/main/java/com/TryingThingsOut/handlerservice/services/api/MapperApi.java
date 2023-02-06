package com.TryingThingsOut.handlerservice.services.api;

import com.TryingThingsOut.handlerservice.exceptions.NoSuchTranslatorException;
import net.minidev.json.JSONObject;

public interface MapperApi {

    JSONObject map(JSONObject objectToMap, String contract) throws NoSuchTranslatorException;
}
