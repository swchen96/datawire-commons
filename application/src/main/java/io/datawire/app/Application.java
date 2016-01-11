package io.datawire.app;


public abstract class Application<A extends ApplicationConfiguration, B extends ApplicationEnvironment> {

  private final String name;
  private final Class<A> configurationClass;
  private final Class<B> environmentClass;
  private final InitializerFactory<A, B> initializerFactory;

  public Application(String name, Class<A> configurationClass, Class<B> environmentClass) {
    this(name, configurationClass, environmentClass, new DefaultInitializerFactory<>());
  }

  public Application(String name, Class<A> configurationClass, Class<B> environmentClass, InitializerFactory<A, B> initializerFactory) {
    this.name = name;
    this.configurationClass = configurationClass;
    this.environmentClass = environmentClass;
    this.initializerFactory = initializerFactory;
  }

  String getName() {
    return name;
  }

  public Class<A> getConfigurationClass() {
    return configurationClass;
  }

  public Class<B> getEnvironmentClass() {
    return environmentClass;
  }

  public void initialize(Initializer<A, B> initializer) {}

  public void run(String... arguments) {
    Initializer<A, B> initializer = initializerFactory.create(this);
    initialize(initializer);

    Runner runner = new CliRunner(initializer, System.out, System.err);
    if (!runner.run(arguments)) {
      System.exit(1);
    }
  }
}
