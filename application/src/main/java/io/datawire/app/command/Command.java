package io.datawire.app.command;


import io.datawire.app.Initializer;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

public abstract class Command {

  private final String name;
  private final String description;

  public Command(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public abstract void configure(Subparser subparser);
  public abstract void run(Initializer<?, ?> initializer, Namespace namespace) throws Exception;
}
