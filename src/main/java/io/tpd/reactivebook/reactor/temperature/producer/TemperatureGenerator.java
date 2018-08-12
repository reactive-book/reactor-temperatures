package io.tpd.reactivebook.reactor.temperature.producer;

import java.util.stream.Stream;

import io.tpd.reactivebook.reactor.temperature.domain.Measurement;

public interface TemperatureGenerator {

  Stream<Measurement> temperatureStream(final int items);

}
