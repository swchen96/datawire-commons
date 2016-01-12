package io.datawire.app;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.datawire.app.command.Command;
import io.datawire.configuration.ConfigurationFactory;

import java.util.*;

import static java.util.Objects.*;

public class Initializer<A extends ApplicationConfiguration> {

  private final Application<A> application;
  private final ObjectMapper objectMapper;

  private List<Command> commands = new LinkedList<>();

  Initializer(Application<A> application, ObjectMapper objectMapper) {
    this.application = requireNonNull(application, "Application is null");
    this.objectMapper = requireNonNull(objectMapper, "JSON/YAML object objectMapper is null");
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
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
    return new ConfigurationFactory<>(application.getConfigurationClass(), objectMapper);
  }
}
