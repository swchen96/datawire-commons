package io.datawire.app;


public interface InitializerFactory<A extends ApplicationConfiguration> {
  Initializer<A> create(Application<A> application);
}
