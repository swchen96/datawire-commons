package io.datawire.application.example.command;


public class InteractiveCommandResult {

  private boolean exit;

  InteractiveCommandResult setExit(boolean exit) {
    this.exit = exit;
    return this;
  }
}
