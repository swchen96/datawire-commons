package io.datawire.app;


public abstract class Application<A extends ApplicationConfiguration> {

  private final String name;
  private final Class<A> configurationClass;
  private final InitializerFactory<A> initializerFactory;

  public Application(String name, Class<A> configurationClass) {
    this(name, configurationClass, new DefaultInitializerFactory<>());
  }

  public Application(String name, Class<A> configurationClass, InitializerFactory<A> initializerFactory) {
    this.name = name;
    this.configurationClass = configurationClass;
    this.initializerFactory = initializerFactory;
  }

  String getName() {
    return name;
  }

  public Class<A> getConfigurationClass() {
    return configurationClass;
  }

  public void initialize(Initializer<A> initializer) {}

  public void run(String... arguments) {
    Initializer<A> initializer = initializerFactory.create(this);
    initialize(initializer);

    Runner runner = new CliRunner(initializer, System.out, System.err);
    if (!runner.run(arguments)) {
      System.exit(1);
    }
  }
}
