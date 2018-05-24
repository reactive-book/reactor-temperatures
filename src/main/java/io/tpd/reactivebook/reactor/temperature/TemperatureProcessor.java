package io.tpd.reactivebook.reactor.temperature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class TemperatureProcessor {
  private static final Logger log =
    LoggerFactory.getLogger(TemperatureProcessor.class);

  private final Flux<TemperatureRead> temperatureFlux;

  public TemperatureProcessor(final Flux<TemperatureRead> temperatureFlux) {
    this.temperatureFlux = temperatureFlux;
  }

  public void process(final TemperatureSubscriber subscriberAll, final TemperatureSubscriber subscriberAvg) {
    temperatureFlux.subscribe(
      subscriberAll
    );

    temperatureFlux.window(10).flatMap(temperatureReadFlux ->
      temperatureReadFlux.doOnNext(
        item -> log.info("Calling onNext() for averages with {}", item)
      ).reduce(
        TemperatureProcessor::averageReducer
      )
    ).subscribe(
      subscriberAvg
    );
  }

  private static TemperatureRead averageReducer(final TemperatureRead t1, final TemperatureRead t2) {
    return new TemperatureRead((t1.getTemperature() + t2.getTemperature()) / 2, t1.getTimestamp());
  }
}
