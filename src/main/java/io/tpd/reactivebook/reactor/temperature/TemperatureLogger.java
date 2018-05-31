package io.tpd.reactivebook.reactor.temperature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TemperatureLogger implements TemperatureSubscriber {

  private static final Logger log =
    LoggerFactory.getLogger(TemperatureLogger.class);

  private final String name;

  @Override
  public void accept(final TemperatureRead temperatureRead) {
    log.debug("[{}] received {}", name, temperatureRead);
  }
}
