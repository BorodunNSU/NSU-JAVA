package ru.nsu.ccfit.borodin.transportCompany;

import java.util.*;

public class Train extends Thread {
    private final String name;
    private final int speed;
    private final int amortizationTime;
    private final Config config;
    Map<String, List<Item>> items;
    RailwaySystem railwaySystem;
    Depot depot;

    public Train(String name, int speed, int amortizationTime, Config config, RailwaySystem railwaySystem, Depot depot) {
        this.name = name;
        this.speed = speed;
        this.amortizationTime = amortizationTime;
        this.config = config;
        items = new HashMap<>();
        this.depot = depot;
        this.railwaySystem = railwaySystem;
    }

    private void load(Station station) throws ConfigException, InterruptedException {
        station.acceptTrain(this);
        for (Map.Entry<String, Integer> entry : config.getCapacityMap(name).entrySet()) {
            String item = entry.getKey();
            Warehouse warehouse = station.getWarehouse(item);
            for (int i = 0; i < entry.getValue(); i++) {
                Thread.sleep(config.getTimeToLoad(item));
                items.computeIfAbsent(item, (x) -> new ArrayList<>()).add(warehouse.getItem());
            }
        }
        station.sendTrain(this);
    }

    private void unload(Station station) throws InterruptedException, ConfigException {
        station.acceptTrain(this);
        for (Map.Entry<String, Integer> entry : config.getCapacityMap(name).entrySet()) {
            String item = entry.getKey();
            Warehouse warehouse = station.getWarehouse(item);
            for (int i = 0; i < entry.getValue(); i++) {
                Thread.sleep(config.getTimeToUnload(item));
                warehouse.addItem(items.get(item).get(0));
            }
        }
        station.sendTrain(this);
    }

    @Override
    public void run() {
        long startTime = Calendar.getInstance().getTimeInMillis() / 1000;
        try {
            while ((Calendar.getInstance().getTimeInMillis() / 1000 - startTime) < amortizationTime) {
                load(railwaySystem.getDepart());
                railwaySystem.getRoadToDest().getRailway();
                Log.info("Train  '" + name + "' started moving");
                int dist = 0;
                while (dist < railwaySystem.getRoadToDest().getDistance()) {
                    dist += speed;
                    Thread.sleep(1000);
                }
                Log.info("Train " + name + "' arrive");
                railwaySystem.getRoadToDest().freeRailway();

                unload(railwaySystem.getArrive());
                railwaySystem.getRoadBack().getRailway();
                Log.info("Train '" + name + "' started moving back");
                dist = 0;
                while (dist < railwaySystem.getRoadBack().getDistance()) {
                    dist += speed;
                    Thread.sleep(1000);
                }
                Log.info("Train '" + name + "' arrived back");
                railwaySystem.getRoadBack().freeRailway();
            }
            depot.addNewOrder(name);
        } catch (InterruptedException e) {
            Log.info("Train '" + name + "' have been interrupted");
        } catch (ConfigException e) {
            Log.severe("Can`t get config data for train" + name, e);
        }
    }
}
