package io.tpd.reactivebook.reactor.temperature.subscriber;

import java.util.function.Consumer;

import io.tpd.reactivebook.reactor.temperature.domain.Measurement;

public interface TemperatureSubscriber extends Consumer<Measurement> {
}
