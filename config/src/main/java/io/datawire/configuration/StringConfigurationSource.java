/*
 * Copyright 2015 Datawire. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.datawire.configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class StringConfigurationSource implements ConfigurationSource {

  private final String data;
  private final Charset charset;

  public StringConfigurationSource(String data) {
    this(data, StandardCharsets.UTF_8);
  }

  public StringConfigurationSource(String data, Charset charset) {
    this.data = Objects.requireNonNull(data, "Input data source is null");
    this.charset = Objects.requireNonNull(charset, "Input data charset is null");
  }

  @Override
  public String getLocation() {
    return "<string in memory>";
  }

  @Override
  public InputStream open() throws IOException {
    return new ByteArrayInputStream(data.getBytes(charset));
  }
}
