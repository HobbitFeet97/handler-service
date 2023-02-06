package com.TryingThingsOut.handlerservice.components.api;

import net.minidev.json.JSONObject;

public interface TranslatorApi {

    String translate(JSONObject question);
}
