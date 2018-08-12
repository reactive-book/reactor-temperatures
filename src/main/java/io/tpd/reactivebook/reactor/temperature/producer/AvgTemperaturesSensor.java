package io.tpd.reactivebook.reactor.temperature.producer;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.tpd.reactivebook.reactor.temperature.domain.Measurement;

import reactor.core.publisher.Flux;

public class AvgTemperaturesSensor implements TemperatureSensor {

  private static final Logger log =
    LoggerFactory.getLogger(AvgTemperaturesSensor.class);

  private final AtomicLong counter;
  private final Flux<Measurement> temperatureFlux;
  private final int averageWindow;
  private final TemperatureGenerator generator;

  public AvgTemperaturesSensor(final int averageWindow,
                               final TemperatureGenerator generator) {
    this.averageWindow = averageWindow;
    this.generator = generator;
    this.counter = new AtomicLong(0);
    this.temperatureFlux = createAverageTemperatureFlux();
  }

  @Override
  public Flux<Measurement> temperatures() {
    return temperatureFlux;
  }

  private Flux<Measurement> createAverageTemperatureFlux() {
    return createFlux()
      .window(averageWindow).flatMap(measurementFlux ->
        measurementFlux.doOnNext(
          item -> log.trace("Calling onNext() for averages with {}", item)
        ).reduce(
          AvgTemperaturesSensor::averageReducer
        )
      );
  }

  private Flux<Measurement> createFlux() {
    return Flux.fromStream(
      generator.temperatureStream(100)
    ).doOnComplete(
      () -> log.info(
        "Completed! Total {} passed the filter",
        counter.getAndSet(0)
      )
    ).doOnNext(
      ignore -> counter.set(counter.get() + 1)
    ).doOnNext(
      item -> log.trace("Calling onNext() with {}", item)
    );
  }

  private static Measurement averageReducer(final Measurement m1,
                                            final Measurement m2) {
    return new Measurement(
      (m1.getTemperature() + m2.getTemperature()) / 2,
      m1.getTimestamp()
    );
  }

}
