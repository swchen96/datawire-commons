package io.datawire.app;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TestApplicationConfiguration extends ApplicationConfiguration {

  private final String foo;
  private final String bar;

  @JsonCreator TestApplicationConfiguration(
      @JsonProperty("foo") String foo,
      @JsonProperty("bar") String bar
  ) {
    this.foo = foo;
    this.bar = bar;
  }

  public String getFoo() {
    return foo;
  }

  public String getBar() {
    return bar;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TestApplicationConfiguration that = (TestApplicationConfiguration) o;
    return Objects.equals(foo, that.foo) && Objects.equals(bar, that.bar);
  }

  @Override
  public int hashCode() {
    return Objects.hash(foo, bar);
  }
}
