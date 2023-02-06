package com.TryingThingsOut.handlerservice.exceptions;

public class NoSuchTranslatorException extends Exception {
    public NoSuchTranslatorException() {
        super("No Translator Exists for this contract.");
    }
    public NoSuchTranslatorException(String contract) {
        super(String.format("No Translator Exists for this contract: %s", contract));
    }
}
