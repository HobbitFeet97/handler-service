package com.TryingThingsOut.handlerservice.controllers;

import com.TryingThingsOut.handlerservice.config.Properties;
import com.TryingThingsOut.handlerservice.services.api.MapperApi;
import com.TryingThingsOut.handlerservice.utils.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/v1")
@RestController
public class HandlerController {

    @Autowired
    private MapperApi mapper;

    @Autowired
    private Properties props;

    private ExceptionHandler exceptionHandler = new ExceptionHandler();

    @GetMapping("/health-check")
    public ResponseEntity<String> getHealthCheck() {
        log.info("Health check end-point called");
        return ResponseEntity.ok("Service is running");
    }

    @PatchMapping("/question")
    public ResponseEntity<Object> translateQuestion(
            @RequestBody JSONObject question,
            @RequestHeader(name = "contract") String contract
    ) {
        log.info("PATCH Translate question called - Question: {}", question.toString());
        try {
            JSONObject translatedQuestion = mapper.map(question, contract);
            log.info("PATCH Translate question called successfully.");
            return ResponseEntity.ok(translatedQuestion);
        } catch (Exception e) {
            log.info("PATCH Translate question called unsuccessfully.");
            return exceptionHandler.handleException(e);
        }
    }

    @PatchMapping("/section")
    public ResponseEntity<Object> translateSection(
            @RequestBody JSONObject section,
            @RequestHeader(name = "contract") String contract
    ) {
        log.info("PATCH Translate question called - Question: {}", section.toString());
        try {
            JSONObject translatedSection = mapper.map(section, contract);
            log.info("PATCH Translate section called successfully.");
            return ResponseEntity.ok(translatedSection);
        } catch (Exception e) {
            log.info("PATCH Translate section called unsuccessfully.");
            return exceptionHandler.handleException(e);
        }
    }

    @PatchMapping("/translate")
    public ResponseEntity<Object> translate(
            @RequestBody JSONObject objectToTranslate,
            @RequestHeader(name = "contract") String contract
    ) {
        log.info("PATCH Translate called - Object: {}", objectToTranslate.toString());
        try {
            JSONObject translatedObject = props.getContractToMapper().get(contract) == null ?
                    mapper.map(objectToTranslate, contract) : props.getContractToMapper().get(contract).map(objectToTranslate, contract);
            log.info("PATCH Translate section called successfully.");
            return ResponseEntity.ok(translatedObject);
        } catch (Exception e) {
            log.info("PATCH Translate section called unsuccessfully.");
            return exceptionHandler.handleException(e);
        }
    }

}
