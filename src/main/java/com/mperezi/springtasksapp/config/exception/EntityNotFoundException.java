package com.mperezi.springtasksapp.config.exception;

import java.util.Map;
import java.util.stream.Collectors;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> clazz, Map<String, String> parameters) {
        super(buildMessage(clazz, parameters));
    }

    private static String buildMessage(Class<?> clazz, Map<String, String> parameters) {
        return clazz.getSimpleName() +
                " not found for query parameters " +
                parameters.entrySet()
                        .stream()
                        .map(param -> param.getKey() + "=" + param.getValue())
                        .collect(Collectors.joining(",", "{", "}"));
    }
}
