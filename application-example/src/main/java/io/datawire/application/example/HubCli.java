package io.datawire.application.example;


import io.datawire.app.Application;
import io.datawire.app.ApplicationEnvironment;
import io.datawire.app.Initializer;
import io.datawire.application.example.command.CommandHandler;
import io.datawire.application.example.command.InteractiveCommand;
import io.datawire.application.example.command.ConnectCommand;
import io.datawire.application.example.command.PublishCommand;
import io.datawire.application.example.command.SubscribeCommand;

public class HubCli extends Application<HubCliConfiguration, ApplicationEnvironment> {

  public HubCli() {
    super("hub-cli", HubCliConfiguration.class, ApplicationEnvironment.class);
  }

  @Override
  public void initialize(Initializer<HubCliConfiguration, ApplicationEnvironment> initializer) {
    initializer.addCommand(new ConnectCommand(this));
    initializer.addCommand(new SubscribeCommand(this));
    initializer.addCommand(new PublishCommand(this));
    initializer.addCommand(new InteractiveCommand<>("ping", "", this).addHandler(new CommandHandler("ping", "") {
      @Override
      public void handle(String payload) {
        System.out.println("pong!");
      }
    }));
  }

  public static void main(String... args) {
    new HubCli().run(args);
  }
}
