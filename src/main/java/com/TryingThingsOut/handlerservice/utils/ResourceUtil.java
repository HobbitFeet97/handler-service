package com.TryingThingsOut.handlerservice.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ResourceUtil {

    @SneakyThrows
    public static String readClasspathFile(String path) {
        InputStream inputStream = ResourceUtil.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            log.error("No file found at path: {}", path);
            return "";
        } else {
            StringBuilder builder = new StringBuilder();
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name()))
                );
                int character;
                while ((character = reader.read()) != -1) {
                    builder.append((char) character);
                }
                String result = builder.toString();
                log.info("Successfully read file at path: {} - Result: {}", path, result);
                return result;
            } catch (Exception e) {
                log.error("Error reading file at path: {}", path);
            }
        }
        return "";
    }
}
