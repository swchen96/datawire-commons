package io.datawire.app.command;


import io.datawire.app.Application;
import io.datawire.app.ApplicationConfiguration;
import io.datawire.app.ApplicationEnvironment;
import io.datawire.app.Initializer;
import net.sourceforge.argparse4j.inf.Namespace;


public abstract class EnvironmentCommand<A extends ApplicationConfiguration, B extends ApplicationEnvironment>
    extends ConfiguredCommand<A> {

  private final Application<A, B> application;

  public EnvironmentCommand(String name, String description, Application<A, B> application) {
    super(name, description, application.getConfigurationClass());
    this.application = application;
  }

  @Override public void run(Initializer<A, ?> initializer, A configuration, Namespace namespace) throws Exception {
    @SuppressWarnings("unchecked") B environment = (B) initializer.buildEnvironment(configuration);
    run(configuration, environment, namespace);
  }

  public abstract void run(A configuration, B environment, Namespace namespace) throws Exception;
}
