package io.tpd.reactivebook.reactor;

import io.tpd.reactivebook.reactor.temperature.TemperatureLogger;
import io.tpd.reactivebook.reactor.temperature.TemperatureSensor;

public class ReactorApp {

  public static void main(String[] args) {
    TemperatureSensor sensor = new TemperatureSensor();
    TemperatureLogger logger = new TemperatureLogger("T-LOG");

    sensor.temperatureFlux().subscribe(logger);
    sensor.averageTemperatureFlux().subscribe(logger);
  }

}
