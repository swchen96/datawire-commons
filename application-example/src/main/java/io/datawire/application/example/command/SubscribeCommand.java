package io.datawire.application.example.command;


import io.datawire.app.Application;
import io.datawire.app.ApplicationEnvironment;
import io.datawire.app.command.EnvironmentCommand;
import io.datawire.application.example.HubCliConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;

public class SubscribeCommand extends EnvironmentCommand<HubCliConfiguration, ApplicationEnvironment> {

  public SubscribeCommand(Application<HubCliConfiguration, ApplicationEnvironment> app) {
    super("subscribe", "connect and subscribe to a Hub", app);
  }

  @Override
  public void run(HubCliConfiguration configuration, ApplicationEnvironment environment, Namespace namespace) {
    Object client = configuration.buildHubClient();
  }
}
