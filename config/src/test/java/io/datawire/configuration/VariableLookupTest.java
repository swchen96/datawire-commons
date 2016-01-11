package io.datawire.configuration;


import org.apache.commons.lang3.text.StrLookup;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class VariableLookupTest {

  @Test public void lookupInvalidFormat_throwsInvalidVariableFormatException() {
    StrLookup lookup = new VariableLookup(true);

    try {
      lookup.lookup("env foo");
      failBecauseExceptionWasNotThrown(InvalidVariableFormatException.class);
    } catch (InvalidVariableFormatException ex) {

      assertThat(ex).hasMessage("Invalid variable format (was: env foo)");
    }
  }

  @Test
  public void strictModeAndMapNotContainingVariable_lookupMapValue_throwsUndefinedVariableException() {
    StrLookup lookup = new VariableLookup(true);

    try {
      lookup.lookup("env `NOT_IN_THE_MAP`");
      failBecauseExceptionWasNotThrown(UndefinedVariableException.class);
    } catch (UndefinedVariableException ex) {
      assertThat(ex).hasMessage("Variable not defined. Unable to substitute expression. " +
          "(type: env, source: NOT_IN_THE_MAP, expression: ${env `NOT_IN_THE_MAP`})");
    }
  }

  @Test
  public void strictModeAndMapContainingVariable_lookupFileValue_ReturnsValue() {
    StrLookup lookup = new VariableLookup(true);

    assertThat(lookup.lookup(String.format("file `%s`", getResourceFilePath("fixtures/FileLookupHandler.txt"))))
        .isEqualTo("I am the walrus!");
  }

  @Test
  public void strictModeAndFileContainingVariable_lookupFileValue_returnsValue() throws IOException {
    File temp = File.createTempFile("test-" + UUID.randomUUID(), ".tmp");

    BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
    bw.write("I am the walrus!");
    bw.close();

    StrLookup lookup = new VariableLookup();

    assertThat(lookup.lookup("file `" + temp.getAbsolutePath() + "`")).isEqualTo("I am the walrus!");
  }

  @Test
  public void strictModeAndFileDoesNotExist_lookupMapValue_throwsUndefinedVariableException() throws IOException {
    StrLookup lookup = new VariableLookup();
    String badPath = "/tmp/temp-" + UUID.randomUUID() + ".tmp";

    try {
      lookup.lookup("file `"+ badPath + "`");
      failBecauseExceptionWasNotThrown(UndefinedVariableException.class);
    } catch (UndefinedVariableException ex) {
      assertThat(ex).hasMessage("Variable not defined. Unable to substitute expression. " +
          "(type: file, source: " + badPath + ", expression: ${file `"+ badPath + "`})");
    }
  }

  private static String getResourceFilePath(String resource) {
    try {
      URL resourceURL = VariableLookupTest.class.getClassLoader().getResource(resource);
      return new File(resourceURL.toURI()).getAbsolutePath();
    } catch (URISyntaxException ex) {
      return null;
    }
  }
}
