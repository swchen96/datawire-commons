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

import org.apache.commons.lang3.text.StrSubstitutor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

public class SubstitutingConfigurationSource implements ConfigurationSource {

  private final ConfigurationSource delegate;
  private final StrSubstitutor substitutor;

  public SubstitutingConfigurationSource(ConfigurationSource delegate, StrSubstitutor substitutor) {
    this.delegate = Objects.requireNonNull(delegate, "Delegated configuration source is null");
    this.substitutor = Objects.requireNonNull(substitutor, "Substitutor engine is null");
  }

  @Override
  public String getLocation() {
    return delegate.getLocation();
  }

  public InputStream open() throws IOException {
    final String config = read(delegate.open());
    final String substituted = substitutor.replace(config);
    return new ByteArrayInputStream(substituted.getBytes(StandardCharsets.UTF_8));
  }

  private static String read(InputStream input) throws IOException {
    try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
      return buffer.lines().collect(Collectors.joining(System.lineSeparator()));
    }
  }
}
