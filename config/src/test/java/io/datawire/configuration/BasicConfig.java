package io.datawire.configuration;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class BasicConfig {

  private final String foo;
  private final String baz;
  private final Widget widget;

  @JsonCreator public BasicConfig(
      @JsonProperty("foo") String foo,
      @JsonProperty("baz") String baz,
      @JsonProperty("widget") Widget widget
  ) {
    this.foo = foo;
    this.baz = baz;
    this.widget = widget;
  }

  public String getFoo() {
    return foo;
  }

  public String getBaz() {
    return baz;
  }

  public Widget getWidget() {
    return widget;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BasicConfig that = (BasicConfig) o;
    return Objects.equals(foo, that.foo) &&
        Objects.equals(baz, that.baz) &&
        Objects.equals(widget, that.widget);
  }

  @Override
  public int hashCode() {
    return Objects.hash(foo, baz, widget);
  }

  public static class Widget {

    private final String name;
    private final Integer age;
    private final Boolean active;
    private final Widget subWidget;

    @JsonCreator public Widget(
        @JsonProperty("name") String name,
        @JsonProperty("age") Integer age,
        @JsonProperty("active") Boolean active,
        @JsonProperty("subWidget") Widget subWidget
    ) {
      this.name = name;
      this.age = age;
      this.active = active;
      this.subWidget = subWidget;
    }

    public String getName() {
      return name;
    }

    public Integer getAge() {
      return age;
    }

    public Boolean getActive() {
      return active;
    }

    public Widget getSubWidget() {
      return subWidget;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Widget widget = (Widget) o;
      return Objects.equals(name, widget.name) &&
          Objects.equals(age, widget.age) &&
          Objects.equals(active, widget.active) &&
          Objects.equals(subWidget, widget.subWidget);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, age, active, subWidget);
    }
  }
}
