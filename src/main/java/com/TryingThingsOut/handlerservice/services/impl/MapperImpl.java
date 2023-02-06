package com.TryingThingsOut.handlerservice.services.impl;

import com.TryingThingsOut.handlerservice.components.impl.TranslatorImpl;
import com.TryingThingsOut.handlerservice.config.Properties;
import com.TryingThingsOut.handlerservice.exceptions.NoSuchTranslatorException;
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
    public JSONObject map(JSONObject objectToMap, String contract) throws NoSuchTranslatorException {
        TranslatorImpl translator = props.getContractToTranslator().get(contract);
        if (translator == null) {
            throw new NoSuchTranslatorException(contract);
        }
        String translatedObject = translator.translate(objectToMap);
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
