package io.tpd.reactivebook.reactor.temperature;

import java.util.function.Consumer;

public interface TemperatureSubscriber extends Consumer<TemperatureRead> {
}
