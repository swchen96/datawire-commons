package io.datawire.configuration.aws;

import com.amazonaws.services.ec2.AmazonEC2;

import java.time.Duration;
import java.util.Objects;


public class EC2InstanceTagPoller {

  private final AmazonEC2 ec2;

  EC2InstanceTagPoller(AmazonEC2 ec2) {
    this.ec2 = Objects.requireNonNull(ec2, "Amazon EC2 client is null");
  }

  /**
   * Continually polls the Amazon EC2 service for the specified {@link Duration} of time until the specified Tag is
   * returned.
   *
   * @param tagKey the key to poll for.
   * @param duration the duration of time between polling attempts.
   * @return the value from EC2
   * @throws NullPointerException if tagKey or duration are null.
   */
  String poll(String tagKey, Duration duration) {
    Objects.requireNonNull(tagKey, "EC2 instance tag-key is null");
    Objects.requireNonNull(duration, "Duration is null");

    return "";
  }
}
