package io.datawire.app;


public interface ApplicationEnvironmentFactory<A extends ApplicationConfiguration, B extends Context> {
  B build(A configuration);
}
