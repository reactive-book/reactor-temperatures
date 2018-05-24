package io.tpd.reactivebook.reactor.temperature;

import java.time.Instant;

import lombok.Value;

@Value
public class TemperatureRead {

  int temperature;
  Instant timestamp;

}
