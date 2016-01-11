package io.datawire.application.example.command;


public abstract class CommandHandler {

  protected final String name;
  protected final String description;

  public CommandHandler(String name, String description) {
    this.name = name;
    this.description = description;
  }

  protected String getName() {
    return name;
  }

  protected String getDescription() {
    return description;
  }

  public abstract void handle(String payload);
}
