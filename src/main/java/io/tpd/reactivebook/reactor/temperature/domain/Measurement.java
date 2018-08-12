package io.tpd.reactivebook.reactor.temperature.domain;

import java.time.Instant;

import lombok.Value;

@Value
public class Measurement {

  int temperature;
  Instant timestamp;

}
