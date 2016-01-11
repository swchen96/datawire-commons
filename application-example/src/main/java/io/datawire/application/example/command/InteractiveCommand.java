package io.datawire.application.example.command;


import io.datawire.app.Application;
import io.datawire.app.ApplicationConfiguration;
import io.datawire.app.ApplicationEnvironment;
import io.datawire.app.command.EnvironmentCommand;
import jline.console.ConsoleReader;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.*;

public class InteractiveCommand<A extends ApplicationConfiguration, B extends ApplicationEnvironment> extends EnvironmentCommand<A, B> {

  private final Map<String, CommandHandler> commands;

  private Pattern linePattern = Pattern.compile("(\\w+)\\s+(\\w+)");

  public InteractiveCommand(String name, String description, Application<A, B> application) {
    super(name, description, application);
    this.commands = new HashMap<>();
  }

  public InteractiveCommand addHandler(CommandHandler handler) {
    commands.put(handler.getName().toLowerCase(), handler);
    return this;
  }

  public InteractiveCommand clearHandlers() {
    commands.clear();
    return this;
  }

  public InteractiveCommand withLinePattern(Pattern linePattern) {
    this.linePattern = requireNonNull(linePattern);
    return this;
  }

  protected String format(String message, Object... args) {
    return String.format(Locale.ROOT, message, args);
  }

  @Override public void run(A configuration, B environment, Namespace namespace) throws Exception {
    ConsoleReader reader = new ConsoleReader();
    reader.setPrompt(">>>");
    configureConsoleReader(reader);

    String line = null;
    PrintWriter output = new PrintWriter(reader.getOutput());
    while((line = reader.readLine()) != null) {
      Matcher m = linePattern.matcher(line);
      if (m.matches()) {
        String command = m.group(0);
        String payload = m.group(1);

        CommandHandler handler = commands.get(command);
        if (handler != null) {
          handler.handle(payload);
        } else {
          output.println(format("Unknown command: '%s'", command));
        }
      } else {
        output.println(format("Bad command format: '%s'", line));
      }
    }
  }

  public void configureConsoleReader(ConsoleReader reader) {

  }
}
