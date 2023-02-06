package com.TryingThingsOut.handlerservice.services.impl;

import com.TryingThingsOut.handlerservice.config.Properties;
import com.TryingThingsOut.handlerservice.services.api.MapperApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MapperImpl implements MapperApi {

    private static final Properties props = new Properties();

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public JSONObject map(JSONObject objectToMap, String contract) {
        String translatedObject = props.getContractToTranslator().get(contract).translate(objectToMap);
        JSONObject returnObject = new JSONObject();
        try {
            returnObject = mapper.readValue(translatedObject, JSONObject.class);
        } catch (Exception e) {
            log.error(
                    "Error mapping object: {} - For contract: {} - With translated object: {}",
                    objectToMap,
                    contract,
                    translatedObject
            );
        }
        return returnObject;
    }
}
