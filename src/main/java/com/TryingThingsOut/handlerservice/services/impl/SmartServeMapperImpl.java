package com.TryingThingsOut.handlerservice.services.impl;

import com.TryingThingsOut.handlerservice.constants.ContractTypes;
import com.TryingThingsOut.handlerservice.exceptions.NoSuchTranslatorException;
import com.TryingThingsOut.handlerservice.utils.ResourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.List;
import java.util.Map;

@Slf4j
public class SmartServeMapperImpl extends MapperImpl {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public JSONObject map(JSONObject objectToMap, String contract) throws NoSuchTranslatorException {
        JSONObject mapped = super.map(objectToMap, contract);
        switch (contract) {
            case ContractTypes
                    .SMART_SERVE_SECTION:
                handleSection(mapped);
                break;
            case ContractTypes.SMART_SERVE_FORM:
                handleForm(mapped);
                break;
        }

        return mapped;
    }

    private void handleSection(Map objectToMap) {
        if (objectToMap.containsKey("items") && objectToMap.get("items") instanceof List) {
            try {
                String leftNav = ResourceUtil.readClasspathFile("templates/SmartServe/SmartServeLeftNav.json");
                String actions = ResourceUtil.readClasspathFile("templates/SmartServe/SmartServeActions.json");
                JSONObject leftNavObject = mapper.readValue(leftNav, JSONObject.class);
                JSONObject actionsObject = mapper.readValue(actions, JSONObject.class);
                List<Object> viewItems = (List) objectToMap.get("items");
                viewItems.add(0, actionsObject);
                viewItems.add(0, leftNavObject);
                objectToMap.put("items", viewItems);
            } catch (Exception e) {
                log.error("Error handling SmartServe section adding static items - Error: {}", e);
            }
        }
    }

    private void handleForm(JSONObject objectToMap) {
        if (objectToMap.containsKey("views") && objectToMap.get("views") != null) {
            if (objectToMap.get("views") instanceof List) {
                for (Map view : (List<Map>) objectToMap.get("views")) {
                    handleSection(view);
                }
            } else {
                JSONArray viewArray = new JSONArray();
                JSONObject view = (JSONObject) objectToMap.get("views");
                handleSection(view);
                viewArray.add(view);
                objectToMap.put("views", viewArray);
            }
        }
    }
}
