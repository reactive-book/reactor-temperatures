package io.tpd.reactivebook.reactor.temperature.producer;

import io.tpd.reactivebook.reactor.temperature.domain.Measurement;

import reactor.core.publisher.Flux;

public interface TemperatureSensor {

  Flux<Measurement> temperatures();

}
