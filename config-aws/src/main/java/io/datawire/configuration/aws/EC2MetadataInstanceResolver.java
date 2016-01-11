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

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.util.EC2MetadataUtils;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link EC2InstanceResolver} that inspects the local EC2 AWS metadata server in order to discover
 * the ID of the instance and then issue a subsequent EC2 API request for the full Instance information.
 *
 * @author plombardi@datawire.io
 */

public class EC2MetadataInstanceResolver implements EC2InstanceResolver {

  private final AmazonEC2 ec2;

  public EC2MetadataInstanceResolver(AmazonEC2 ec2) {
    this.ec2 = Objects.requireNonNull(ec2, "Amazon EC2 client is null");
  }

  @Override public Instance resolveInstance() {
    String instanceId = EC2MetadataUtils.getInstanceId();
    DescribeInstancesRequest describeRequest = new DescribeInstancesRequest().withInstanceIds(instanceId);
    DescribeInstancesResult describeResult = ec2.describeInstances(describeRequest);
    return extractInstance(describeResult);
  }

  private static Instance extractInstance(DescribeInstancesResult describeResult) {
    Instance result = null;

    List<Reservation> reservations = describeResult.getReservations();
    if (!(reservations == null || reservations.isEmpty())) {
      List<Instance> instances = reservations.get(0).getInstances();
      if (!(instances == null || instances.isEmpty())) {
        result = instances.get(0);
      }
    }

    return result;
  }
}
