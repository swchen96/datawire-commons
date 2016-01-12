package io.datawire.app;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;

public class Context {

  private final Map<String, String> variables;
  private final ObjectMapper objectMapper;

  public Context(ObjectMapper mapper, Map<String, String> variables) {
    this.objectMapper = Objects.requireNonNull(mapper, "Object mapper is null");
    this.variables = Objects.requireNonNull(variables, "Environment variables map is null");
  }

  public String getProperty(String name) {
    return getProperty(name, null);
  }

  public String getProperty(String name, String defaultValue) {
    return System.getProperty(name, defaultValue);
  }

  public String getVariable(String name) {
    return getVariable(name, null);
  }

  public String getVariable(String name, String defaultValue) {
    return variables.getOrDefault(name, defaultValue);
  }

  public Map<String, String> getVariables() {
    return variables;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }
}