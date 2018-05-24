package io.tpd.reactivebook.reactor;

import io.tpd.reactivebook.reactor.temperature.TemperatureLogger;
import io.tpd.reactivebook.reactor.temperature.TemperatureProcessor;
import io.tpd.reactivebook.reactor.temperature.TemperatureSensor;
import lombok.val;

public class ReactorApp {

  public static void main(String[] args) {
    val temperatureSensor = new TemperatureSensor();
    val temperatureSubscriberAll = new TemperatureLogger("ALL");
    val temperatureSubscriberAvg = new TemperatureLogger("AVG");
    val temperatureProcessor = new TemperatureProcessor(temperatureSensor.temperatureFlux());
    temperatureProcessor.process(temperatureSubscriberAll, temperatureSubscriberAvg);
  }

}
