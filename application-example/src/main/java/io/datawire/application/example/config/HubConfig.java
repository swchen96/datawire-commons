package io.datawire.application.example.config;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HubConfig {

  private final String gateway;

  @JsonCreator public HubConfig(@JsonProperty("gateway") String gateway) {
    this.gateway = gateway;
  }

  public String getGatewayAddress() {
    return gateway;
  }
}
