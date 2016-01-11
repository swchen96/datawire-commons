package io.datawire.app;


import com.fasterxml.jackson.databind.ObjectMapper;

public interface InitializerFactory<A extends ApplicationConfiguration, B extends ApplicationEnvironment> {
  Initializer<A, B> create(Application<A, B> application);
  ObjectMapper buildObjectMapper();
}
