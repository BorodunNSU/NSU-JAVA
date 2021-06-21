package ru.nsu.ccfit.borodin.transportCompany;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransportCompany {
    private Depot depot;
    private final List<Thread> consumersAndFactories;

    public TransportCompany() {
        consumersAndFactories = new ArrayList<>();
    }

    public void startCompany(String configName) throws ConfigException {
        Config config = new Config();
        config.parseConfig(configName);
        Map<String, Warehouse> startWarehouseMap = new HashMap<>();
        Map<String, Warehouse> endWarehouseMap = new HashMap<>();

        Log.info("Starting up consumers and factories");

        for (String item : config.getListOfItems()) {
            Warehouse warehouse = new Warehouse(config.getStartCapacity(item));
            Warehouse endWarehouse = new Warehouse(config.getDistCapacity(item));
            startWarehouseMap.put(item, warehouse);
            endWarehouseMap.put(item, endWarehouse);
            for (int i = 0; i < config.getFactoryCount(item); i++) {
                Factory factory = new Factory(item, warehouse, config.getTimeToCreateItem(item));
                consumersAndFactories.add(factory);
                factory.start();
            }
            for (int i = 0; i < config.getConsumerCount(item); i++) {
                Consumer consumer = new Consumer(item, endWarehouse, config.getTimeToConsume(item));
                consumersAndFactories.add(consumer);
                consumer.start();
            }
        }

        Log.info("Setting up railway system");

        Station start = new Station(config.getStartLoadTrack(), startWarehouseMap);
        Station end = new Station(config.getDistLoadTrack(), endWarehouseMap);
        Railway fromStartToEnd = new Railway(config.getTrackFromStartToDist(), config.getDistance());
        Railway fromEndToStart = new Railway(config.getTrackFromDistToStart(), config.getDistance());
        RailwaySystem system = new RailwaySystem(start, end, fromStartToEnd, fromEndToStart);

        Log.info("Starting up transport company");

        depot = new Depot(config, system);
        for (String trainName : config.getListOfTrains()) {
            depot.addNewOrder(trainName);
        }
    }

    public void stopCompany() throws InterruptedException {
        Log.info("Stopping down transport company");

        depot.stop();
        consumersAndFactories.forEach(Thread::interrupt);
    }
}
