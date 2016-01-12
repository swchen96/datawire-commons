package io.datawire.app;


public class TestApplication extends Application<TestApplicationConfiguration> {
  TestApplication() {
    super("test-application", TestApplicationConfiguration.class);
  }
}
