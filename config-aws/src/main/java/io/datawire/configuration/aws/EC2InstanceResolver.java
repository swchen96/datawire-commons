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

import com.amazonaws.services.ec2.model.Instance;

/**
 * An instance resolver is responsible for retrieving an {@link Instance} record from AWS EC2.
 *
 * @author plombardi@datawire.io
 */

public interface EC2InstanceResolver {

  /**
   * Resolves information about an EC2 instance.
   *
   * @return an instance of {@link Instance} or null if no instance is found.
   */
  Instance resolveInstance();
}
