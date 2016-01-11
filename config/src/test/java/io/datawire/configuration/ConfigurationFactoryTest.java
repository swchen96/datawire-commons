package io.datawire.configuration;


import io.datawire.util.test.Fixtures;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationFactoryTest {

  private final Fixtures fixtures = new Fixtures();

  @Test
  public void build_emptyConfig_throwsConfigurationParsingException() throws IOException {
    ConfigurationFactory<BasicConfig> configFactory = ConfigurationFactory.getInstance(BasicConfig.class);
    String raw = fixtures.loadFixtureAsString(getClass().getSimpleName() + "_EmptyConfig.yml", StandardCharsets.UTF_8);

    try {
      configFactory.build(new StringConfigurationSource(raw, StandardCharsets.UTF_8));
      failBecauseExceptionWasNotThrown(ConfigurationParsingException.class);
    } catch (ConfigurationException ex) {
      assertThat(ex).hasMessage("<string in memory> has one or more errors:\n" +
          "  * Configuration is empty\n");
    }
  }

  @Test
  public void build_basicConfig_ReturnsBasicConfig() throws Exception {
    ConfigurationFactory<BasicConfig> configFactory = ConfigurationFactory.getInstance(BasicConfig.class);
    String raw = fixtures.loadFixtureAsString(getClass().getSimpleName() + "_BasicConfig.yml", StandardCharsets.UTF_8);

    BasicConfig expected = new BasicConfig(
        "foobar",
        "bazbot",
        new BasicConfig.Widget("widget0", 3, true, new BasicConfig.Widget("widget1", 3, false, null)));

    BasicConfig config = configFactory.build(new StringConfigurationSource(raw, StandardCharsets.UTF_8));
    assertThat(config).isEqualTo(expected);
  }

  @Test
  public void build_substitutionConfig_ReturnsSubstitutedConfig() throws Exception {
    ConfigurationFactory<BasicConfig> configFactory = ConfigurationFactory.getInstance(BasicConfig.class);
    String raw = fixtures.loadFixtureAsString(getClass().getSimpleName() + "_SubstitutionConfig.yml", StandardCharsets.UTF_8);

    System.setProperty("FOO", "foobar");
    System.setProperty("BAZ", "bazbot");

    BasicConfig expected = new BasicConfig("foobar", "bazbot", null);

    StrSubstitutor substitutor = new StrSubstitutor(new VariableLookup());
    ConfigurationSource source = new SubstitutingConfigurationSource(new StringConfigurationSource(raw, StandardCharsets.UTF_8), substitutor);
    BasicConfig config = configFactory.build(source);
    assertThat(config).isEqualTo(expected);
  }
}
