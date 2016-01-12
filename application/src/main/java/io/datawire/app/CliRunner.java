package io.datawire.app;


import io.datawire.app.command.Command;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.Objects.*;

public class CliRunner implements Runner {

  private final static String COMMAND_KEY = "command";

  private final static Set<String> HELP_FLAGS = new HashSet<String>() {{ add("-h"); add("--help"); }};
  private final static Set<String> VERSION_FLAGS = new HashSet<String>() {{ add("-v"); add("--version"); }};

  private final Initializer initializer;
  private final Map<String, Command> commands;
  private final ArgumentParser parser;
  private final PrintWriter standardOut;
  private final PrintWriter standardError;

  public CliRunner(Initializer<?> initializer, OutputStream standardOut, OutputStream standardError) {
    this.parser = buildParser();
    this.initializer = requireNonNull(initializer, "Bootstrap is null");
    this.commands = configureCommands(initializer);
    this.standardOut = new PrintWriter(
        new OutputStreamWriter(requireNonNull(standardOut, "Standard output stream is null"), StandardCharsets.UTF_8),
        true);

    this.standardError = new PrintWriter(
        new OutputStreamWriter(requireNonNull(standardError, "Standard error stream is null"), StandardCharsets.UTF_8),
        true);
  }

  public boolean run(String[] arguments) {
    List<String> args = arguments != null ? Arrays.asList(arguments) : Collections.emptyList();

    try {
      if (args.isEmpty() || containsFlag(HELP_FLAGS, args)) {
        parser.printHelp(standardOut);
      } else if (containsFlag(VERSION_FLAGS, args)) {
        parser.printVersion(standardOut);
      } else {
        Namespace namespace = parser.parseArgs(arguments);
        if (namespace.getBoolean("is-help") == null) {
          Command command = commands.get(namespace.getString(COMMAND_KEY));
          command.run(initializer, namespace);
        }
      }
      return true;
    } catch (ArgumentParserException ex) {
      ex.getParser().printHelp(standardError);
      return false;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  private boolean containsFlag(Set<String> flags, Collection<String> arguments) {
    Set<String> intersection = new HashSet<>(flags);
    intersection.retainAll(arguments);
    return !intersection.isEmpty();
  }

  private Map<String, Command> configureCommands(Initializer<?> initializer) {
    final Map<String, Command> result = new TreeMap<>();
    for (Command command : initializer.getCommands()) {
      result.put(command.getName(), command);
      parser.addSubparsers().help("available commands");
      Subparser subparser = parser.addSubparsers().addParser(command.getName(), false);
      command.configure(subparser);
      addHelp(subparser);
      subparser.description(command.getDescription()).setDefault(COMMAND_KEY, command.getName()).defaultHelp(true);
    }

    return result;
  }

  private void addHelp(ArgumentParser parser) {
    parser.addArgument("-h", "--help")
        .action(new SafeHelpAction(standardOut))
        .help("show this message and exit")
        .setDefault(Arguments.SUPPRESS);
  }

  private static ArgumentParser buildParser() {
    ArgumentParser result = ArgumentParsers.newArgumentParser("java -jar ", false);

    result.version("TODO");
    result.addArgument("-v", "--version").action(Arguments.version()).help("print application version and exit");

    return result;
  }

  private class SafeHelpAction implements ArgumentAction {

    private final PrintWriter output;

    public SafeHelpAction(PrintWriter output) {
      this.output = output;
    }

    @Override
    public void run(ArgumentParser parser, Argument arg, Map<String, Object> attributes, String flag, Object value) {
      parser.printHelp(output);
      attributes.put("is-help", true);
    }

    @Override public boolean consumeArgument() {
      return false;
    }

    @Override public void onAttach(Argument argument) {

    }
  }
}
