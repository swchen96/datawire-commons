package io.datawire.app.jackson;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

public enum JacksonFactory {

  INSTANCE;

  private final ObjectMapper objectMapper;

  JacksonFactory() {
    objectMapper = configure(newObjectMapper());
  }

  public ObjectMapper newObjectMapper() {
    return new ObjectMapper();
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  private ObjectMapper configure(ObjectMapper objectMapper) {
    Objects.requireNonNull("Object mapper is null");
    return objectMapper;
  }
}
