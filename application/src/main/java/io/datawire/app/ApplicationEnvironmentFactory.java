package io.datawire.app;


public interface ApplicationEnvironmentFactory<A extends ApplicationConfiguration, B extends ApplicationEnvironment> {
  B build(A configuration);
}
