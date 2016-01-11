package io.datawire.app;


import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultInitializerFactory<A extends ApplicationConfiguration, B extends ApplicationEnvironment>
    implements InitializerFactory<A, B> {

  @Override public Initializer<A, B> create(Application<A, B> application) {
    return new Initializer<>(application, buildEnvironmentFactory(), buildObjectMapper());
  }

  public ObjectMapper buildObjectMapper() {
    return new ObjectMapper();
  }

  public ApplicationEnvironmentFactory<A, B> buildEnvironmentFactory() {
    return new DefaultEnvironmentFactory<>();
  }
}
