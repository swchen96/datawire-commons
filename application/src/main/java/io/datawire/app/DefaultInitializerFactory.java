package io.datawire.app;


import io.datawire.app.jackson.JacksonFactory;

public class DefaultInitializerFactory<A extends ApplicationConfiguration> implements InitializerFactory<A> {

  @Override
  public Initializer<A> create(Application<A> application) {
    return new Initializer<>(application, JacksonFactory.INSTANCE.getObjectMapper());
  }
}
