package com.TryingThingsOut.handlerservice.config;

import com.TryingThingsOut.handlerservice.components.impl.TranslatorImpl;
import com.TryingThingsOut.handlerservice.constants.ContractTypes;
import com.TryingThingsOut.handlerservice.services.impl.MapperImpl;
import com.TryingThingsOut.handlerservice.services.impl.SmartServeMapperImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Data
@Configuration
public class Properties {

    private static final String MCQ = "templates/MasterContract/MasterContractQuestion.json";

    private Map<String, TranslatorImpl> contractToTranslator = Map.of(
            ContractTypes.SMART_SERVE_QUESTION, new TranslatorImpl(
                    "templates/SmartServe/SmartServeQuestion.json",
                    "attributeMappers/SmartServe/SmartServeQuestionAttributeMapper.json"
            ),
            ContractTypes.SMART_SERVE_QUESTION_REV, new TranslatorImpl(
                    MCQ,
                    "attributeMappers/SmartServe/MasterContractQuestionAttributeMapper.json"
            ),
            ContractTypes.SMART_SERVE_SECTION, new TranslatorImpl(
                    "templates/SmartServe/SmartServeSection.json",
                    "attributeMappers/SmartServe/SmartServeSectionAttributeMapper.json"
            ),
            ContractTypes.SMART_SERVE_FORM, new TranslatorImpl(
                    "templates/SmartServe/SmartServeForm.json",
                    "attributeMappers/SmartServe/SmartServeFormAttributeMapper.json"
            ),
            ContractTypes.TRADE_SHIFT_QUESTION, new TranslatorImpl(
                    "templates/Tradeshift/TradeshiftQuestion.json",
                    "attributeMappers/Tradeshift/TradeshiftQuestionAttributeMapper.json"
            ),
            ContractTypes.TRADE_SHIFT_SECTION, new TranslatorImpl(
                    "templates/Tradeshift/TradeshiftSection.json",
                    "attributeMappers/Tradeshift/TradeshiftSectionAttributeMapper.json"
            )
    );

    private Map<String, MapperImpl> contractToMapper = Map.of(
            ContractTypes.SMART_SERVE_FORM, new SmartServeMapperImpl(),
            ContractTypes.SMART_SERVE_SECTION, new SmartServeMapperImpl()
    );

}
