package com.hanumant.config;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum SingleObjMapper {
    INSTANCE;

    private final ObjectMapper objectMapper;

    SingleObjMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
