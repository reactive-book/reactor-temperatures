package io.tpd.reactivebook.reactor.temperature.producer;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.tpd.reactivebook.reactor.temperature.domain.Measurement;

import reactor.core.publisher.Flux;

public class FilteredTemperaturesSensor implements TemperatureSensor {

  private static final Logger log =
    LoggerFactory.getLogger(FilteredTemperaturesSensor.class);

  private final AtomicLong counter;
  private final Flux<Measurement> temperatureFlux;
  private final int threshold;
  private final TemperatureGenerator generator;

  public FilteredTemperaturesSensor(final int threshold,
                                    final TemperatureGenerator generator) {
    this.threshold = threshold;
    this.generator = generator;
    this.counter = new AtomicLong(0);
    this.temperatureFlux = createFlux();
  }

  @Override
  public Flux<Measurement> temperatures() {
    return temperatureFlux;
  }

  private Flux<Measurement> createFlux() {
    return Flux.fromStream(
      generator.temperatureStream(100)
    ).doOnComplete(
      () -> log.info(
        "Completed! Total {} passed the filter",
        counter.getAndSet(0)
      )
    ).filter(
      t -> t.getTemperature() > threshold
    ).doOnNext(
      ignore -> counter.set(counter.get() + 1)
    ).doOnNext(
      item -> log.trace("Calling onNext() with {}", item)
    );
  }

}
