package io.tpd.reactivebook.reactor.temperature.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.tpd.reactivebook.reactor.temperature.domain.Measurement;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoggerSubscriber implements TemperatureSubscriber {

  private static final Logger log =
    LoggerFactory.getLogger(LoggerSubscriber.class);

  private final String name;

  @Override
  public void accept(final Measurement measurement) {
    log.debug("[{}] received {}", name, measurement);
  }
}
