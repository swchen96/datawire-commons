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

import org.apache.commons.lang3.text.StrLookup;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableLookup extends StrLookup<String> {

  private final static Pattern VARIABLE_PATTERN = Pattern.compile("^(\\w+)\\s+`(.*)`$");

  private final Map<String, LookupHandler> lookups;

  private final boolean strict;

  public VariableLookup() {
    this(true);
  }

  public VariableLookup(boolean strict) {
    this.strict = strict;

    Map<String, LookupHandler> handlers = new HashMap<>();
    handlers.put("env", new EnvironmentVariableLookupHandler());
    handlers.put("prop", new PropertyLookupHandler());
    handlers.put("file", new FileLookupHandler());

    this.lookups = handlers;
  }

  public VariableLookup registerLookup(String id, LookupHandler handler) {
    Objects.requireNonNull(id, "Lookup handler id is null");
    Objects.requireNonNull(handler, "Lookup handler is null");

    lookups.put(id.toLowerCase(), handler);
    return this;
  }

  public String lookup(String key) {
    Matcher matcher = VARIABLE_PATTERN.matcher(key);
    if (matcher.matches()) {
      String type = matcher.group(1);
      String source = matcher.group(2);

      LookupHandler handler = lookups.get(type.toLowerCase());
      String value = null;
      if (handler != null) {
        value = handler.lookup(source);
      }

      if (value == null && strict) {
        throw new UndefinedVariableException(type, source);
      }

      return value;
    } else {
      throw new InvalidVariableFormatException(key, VARIABLE_PATTERN);
    }
  }
}
