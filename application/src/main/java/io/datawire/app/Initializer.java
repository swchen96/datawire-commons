package io.datawire.app;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.datawire.app.command.Command;
import io.datawire.configuration.ConfigurationFactory;

import java.util.*;

import static java.util.Objects.*;

public class Initializer<A extends ApplicationConfiguration, B extends ApplicationEnvironment> {

  private final Application<A, B> application;
  private final ApplicationEnvironmentFactory<A, B> environmentFactory;
  private final ObjectMapper mapper;

  private List<Command> commands = new LinkedList<>();

  Initializer(Application<A, B> application,
              ApplicationEnvironmentFactory<A, B> environmentFactory,
              ObjectMapper mapper) {

    this.application = requireNonNull(application, "Application is null");
    this.environmentFactory = requireNonNull(environmentFactory, "Application environment factory is null");
    this.mapper = requireNonNull(mapper, "JSON/YAML object mapper is null").copy();
  }

  public final Iterable<Command> getCommands() {
    return Collections.unmodifiableList(commands);
  }

  public Initializer addCommands(Collection<Command> commands) {
    this.commands.addAll(requireNonNull(commands, "Commands are null"));
    return this;
  }

  public Initializer addCommand(Command command) {
    this.commands.add(requireNonNull(command, "Command is null"));
    return this;
  }

  public ConfigurationFactory<A> buildConfigurationFactory() {
    return new ConfigurationFactory<>(application.getConfigurationClass(), mapper);
  }

  public B buildEnvironment(A configuration) {
    return environmentFactory.build(configuration);
  }
}
