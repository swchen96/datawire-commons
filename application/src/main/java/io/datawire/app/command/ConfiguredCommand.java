package io.datawire.app.command;


import io.datawire.app.ApplicationConfiguration;
import io.datawire.app.Initializer;
import io.datawire.configuration.ConfigurationFactory;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import java.io.File;

import static java.util.Objects.*;

public abstract class ConfiguredCommand<A extends ApplicationConfiguration> extends Command {

  protected final Class<A> configurationClass;

  public ConfiguredCommand(String name, String description, Class<A> configurationClass) {
    super(name, description);
    this.configurationClass = requireNonNull(configurationClass, "Configuration class is null");
  }

  @Override public void configure(Subparser subparser) {
    subparser.addArgument("config_url").nargs("?").help("configuration url");
  }

  public abstract void run(Initializer<A ,?> initializer, A configuration, Namespace namespace) throws Exception;

  @SuppressWarnings("unchecked")
  @Override public void run(Initializer<?, ?> initializer, Namespace namespace) throws Exception {
    ConfigurationFactory<A> factory = (ConfigurationFactory<A>) initializer.buildConfigurationFactory();
    A config = factory.build(new File(namespace.getString("config_url")));
    run((Initializer<A, ?>) initializer, config, namespace);
  }
}
