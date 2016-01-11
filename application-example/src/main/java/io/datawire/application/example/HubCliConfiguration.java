package io.datawire.application.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.datawire.app.ApplicationConfiguration;
import io.datawire.application.example.config.HubConfig;


public class HubCliConfiguration extends ApplicationConfiguration {

  private final HubConfig hubConfig;

  @JsonCreator public HubCliConfiguration(@JsonProperty("hub") HubConfig hubConfig) {
    this.hubConfig = hubConfig;
  }

  public HubConfig getHubConfig() {
    return hubConfig;
  }

  public Object buildHubClient() {
    return null;
  }
}
