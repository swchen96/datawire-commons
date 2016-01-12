package io.datawire.app.command;


import io.datawire.app.Application;
import io.datawire.app.ApplicationConfiguration;
import io.datawire.app.Context;
import io.datawire.app.Initializer;
import net.sourceforge.argparse4j.inf.Namespace;


public abstract class ContextCommand<A extends ApplicationConfiguration> extends ConfiguredCommand<A> {

  public ContextCommand(String name, String description, Application<A> application) {
    super(name, description, application.getConfigurationClass());
  }

  @Override public void run(Initializer<A> initializer, A configuration, Namespace namespace) throws Exception {
    Context context = new Context(initializer.getObjectMapper(), System.getenv());
    run(configuration, context, namespace);
  }

  public abstract void run(A configuration, Context context, Namespace namespace) throws Exception;
}
