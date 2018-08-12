package io.tpd.reactivebook.reactor.temperature.producer;

import io.tpd.reactivebook.reactor.temperature.domain.Measurement;

import reactor.core.publisher.Flux;

public class AllTemperaturesSensor implements TemperatureSensor {

  private final Flux<Measurement> temperatureFlux;
  private final TemperatureGenerator generator;

  public AllTemperaturesSensor(final TemperatureGenerator generator) {
    this.generator = generator;
    this.temperatureFlux = createFlux();
  }

  @Override
  public Flux<Measurement> temperatures() {
    return temperatureFlux;
  }

  private Flux<Measurement> createFlux() {
    return Flux.fromStream(
      generator.temperatureStream(100)
    );
  }

}
