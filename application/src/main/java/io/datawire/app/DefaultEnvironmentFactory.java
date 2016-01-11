package io.datawire.app;


public class DefaultEnvironmentFactory<A extends ApplicationConfiguration, B extends ApplicationEnvironment> implements ApplicationEnvironmentFactory<A, B> {
  @Override public B build(A configuration) {
    return (B) new ApplicationEnvironment();
  }
}
