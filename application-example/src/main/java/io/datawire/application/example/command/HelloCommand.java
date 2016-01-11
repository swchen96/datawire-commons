package io.datawire.application.example.command;


import io.datawire.app.Application;
import io.datawire.app.ApplicationEnvironment;
import io.datawire.app.command.EnvironmentCommand;
import io.datawire.application.example.HubCliConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;

public class HelloCommand extends EnvironmentCommand<HubCliConfiguration, ApplicationEnvironment> {

  public HelloCommand(String name, String description, Application<HubCliConfiguration, ApplicationEnvironment> app) {
    super(name, description, app);
  }

  @Override
  public void run(HubCliConfiguration configuration, ApplicationEnvironment environment, Namespace namespace) {
    System.out.println("Hello, World!");
  }
}
