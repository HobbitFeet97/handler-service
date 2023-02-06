package com.TryingThingsOut.handlerservice.services.api;

import net.minidev.json.JSONObject;

public interface MapperApi {

    JSONObject map(JSONObject objectToMap, String contract);
}
