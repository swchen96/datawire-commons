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

package io.datawire.configuration.aws;

import com.amazonaws.services.ec2.model.*;
import io.datawire.configuration.LookupHandler;

import java.util.Objects;

/**
 * Implementation of a {@link LookupHandler} that can query EC2 instance tags.
 *
 * @author plombardi@datawire.io
 */

public class EC2InstanceTagLookup implements LookupHandler {

  private final EC2InstanceResolver instanceResolver;

  public EC2InstanceTagLookup(EC2InstanceResolver instanceResolver) {
    this.instanceResolver = Objects.requireNonNull(instanceResolver, "Amazon EC2 instance resolver is null");
  }

  @Override public String lookup(String key) {
    Instance instance = instanceResolver.resolveInstance();
    if (instance != null) {
      for (Tag tag : instance.getTags()) {
        if (tag.getKey().equals(key)) {
          return tag.getValue();
        }
      }
    }

    return null;
  }
}
