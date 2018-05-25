package io.tpd.reactivebook.reactor;

import io.tpd.reactivebook.reactor.temperature.TemperatureLogger;
import io.tpd.reactivebook.reactor.temperature.TemperatureProcessor;
import io.tpd.reactivebook.reactor.temperature.TemperatureSensor;

public class ReactorApp {

  public static void main(String[] args) {
    final TemperatureSensor temperatureSensor = new TemperatureSensor();
    final TemperatureLogger temperatureSubscriberAll = new TemperatureLogger("ALL");
    final TemperatureLogger temperatureSubscriberAvg = new TemperatureLogger("AVG");
    final TemperatureProcessor temperatureProcessor = new TemperatureProcessor(temperatureSensor.temperatureFlux());
    temperatureProcessor.process(temperatureSubscriberAll, temperatureSubscriberAvg);
  }

}
