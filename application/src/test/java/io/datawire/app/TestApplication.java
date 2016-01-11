package io.datawire.app;


public class TestApplication extends Application<TestApplicationConfiguration, TestApplicationEnvironment> {

  TestApplication() {
    super("test", TestApplicationConfiguration.class, TestApplicationEnvironment.class);
  }
}
