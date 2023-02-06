package com.TryingThingsOut.handlerservice.utils;

import com.TryingThingsOut.handlerservice.components.impl.TranslatorImpl;
import com.TryingThingsOut.handlerservice.config.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AttributeUtil {

    private static final Properties props = new Properties();

    private static final String KEY = "mappedKey";
    private static final String DEFAULT = "default";
    private static final String NESTED = "isNestedMapping";
    private static final String NEST_CONTRACT = "nestedContract";
    private static final String VALUE_TRANSLATE = "valueTranslate";
    private static final ObjectMapper mapper = new ObjectMapper();


    /*
    Provided an object which needs translating and a file location for a mapper, will return a map
    Key = replacement identifier from corresponding template JSON
    Value = value to be placed into the corresponding template JSON
     */
    public Map<String, Object> getMappedAttributes(JSONObject objectToTranslate, String mapperLocation) {
        Map<String, Object> returnMap = new HashMap<>();
        for (Map.Entry<String, Map<String,Object>> entry : getRelevantAttributes(mapperLocation).entrySet()) {
            boolean dotNot = entry.getKey().contains(".");
            if (objectToTranslate.containsKey(entry.getKey()) || (dotNot && objectToTranslate.containsKey(entry.getKey().split("\\.")[0]))) {
                /*
                If the entry is nested, perform correct nested translation
                 */
                if (entry.getValue().get(NESTED) != null && (Boolean) entry.getValue().get(NESTED)) {
                    returnMap.put(
                            (String) entry.getValue().get(KEY),
                            handleNestedMapping(
                                    dotNot ? returnDotNotValue(objectToTranslate, entry) : objectToTranslate.get(entry.getKey()),
                                    props.getContractToTranslator().get(
                                            entry.getValue().get(NEST_CONTRACT)
                                    )
                            )
                    );
                }
                /*
                If the entry has a value that needs to be translated (question types from x->y) this is defined
                in the mapper file
                 */
                else if (entry.getValue().containsKey(VALUE_TRANSLATE) && entry.getValue().get(VALUE_TRANSLATE) instanceof Map) {
                    returnMap.put(
                            (String) entry.getValue().get(KEY),
                            ((Map<?, ?>) entry.getValue()
                                    .get(VALUE_TRANSLATE))
                                    .get(
                                            dotNot ? returnDotNotValue(objectToTranslate, entry) : objectToTranslate.get(entry.getKey())
                                    )
                    );
                } else {
                    returnMap.put((String) entry.getValue().get(KEY), dotNot ? returnDotNotValue(objectToTranslate, entry) : objectToTranslate.get(entry.getKey()));
                }
            } else {
                returnMap.put((String) entry.getValue().get(KEY), entry.getValue().get(DEFAULT));
            }
        }
        return returnMap;
    }

    private static Map<String, Map<String, Object>> getRelevantAttributes(String mapperLocation) {
        String attributeMapper = ResourceUtil.readClasspathFile(mapperLocation);
        Map<String, Map<String, Object>> returnMap = new HashMap<>();
        try {
            returnMap = mapper.readValue(attributeMapper, Map.class);
        } catch (Exception e) {
            log.error("There was an error reading file: {} - Exception: {}", mapperLocation, e);
        }
        return returnMap;
    }

    private static Object handleNestedMapping(Object nestedObject, TranslatorImpl translator) {
        if (nestedObject instanceof List) {
            List<Object> returnList = new ArrayList<>();
            for (Object nested : (List) nestedObject) {
                if (nested instanceof Map){
                    JSONObject objectToTranslate = new JSONObject((Map<String, ?>) nested);
                    returnList.add(translator.translate(objectToTranslate));
                }
            }
            return returnList;
        } else {
            return translator.translate((JSONObject) nestedObject);
        }
    }

    private static Object returnDotNotValue(JSONObject objectToTranslate, Map.Entry<String, Map<String,Object>> entry) {
        String[] keyList = entry.getKey().split("\\.");
        int currentIndex = 0;
        Object newObject = new HashMap<>(objectToTranslate);
        while (currentIndex < keyList.length) {
            newObject = ((Map)newObject).get(keyList[currentIndex]);
            currentIndex += 1;
            if (newObject instanceof Map) {
                continue;
            } else {
                return newObject;
            }
        }
        return entry.getValue().get(DEFAULT);
    }
}
