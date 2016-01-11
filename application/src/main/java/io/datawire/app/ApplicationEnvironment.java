package io.datawire.app;


import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ApplicationEnvironment {

  private final Map<String, String> variables;

  public ApplicationEnvironment() {
    this(System.getenv());
  }

  public ApplicationEnvironment(Map<String, String> variables) {
    Objects.requireNonNull(variables, "Environment variables map is null");
    this.variables = Collections.unmodifiableMap(variables);
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
}